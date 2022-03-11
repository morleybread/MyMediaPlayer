package com.morley.myvideoplayer;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class HelloApplication extends Application {
     private   MediaView mediaView;
     private final ObservableList<MediaPlayer> mediaPlayers=FXCollections.observableArrayList();
     private int poistion;
     private MediaConfiguration mediaConfiguration;
     private File file;
     private Duration allDuration;

   public HelloApplication() throws IOException {

           this.mediaConfiguration=new MediaConfiguration();
           this.file=mediaConfiguration.start();
           this.poistion=mediaConfiguration.read(file)-48;
   }

    protected String formatTime(Duration elapsed,Duration duration){
        //将两个Duartion参数转化为 hh：mm：ss的形式后输出
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        int elapsedMinutes = (intElapsed - elapsedHours *60 *60)/ 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
        if(duration.greaterThan(Duration.ZERO)){
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            int durationMinutes = (intDuration - durationHours *60 * 60) / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if(durationHours > 0){
                return String.format("%02d:%02d:%02d / %02d:%02d:%02d",elapsedHours,elapsedMinutes,elapsedSeconds,durationHours,durationMinutes,durationSeconds);
            }else{
                return String.format("%02d:%02d / %02d:%02d",elapsedMinutes,elapsedSeconds,durationMinutes,durationSeconds);
            }
        }else{
            if(elapsedHours > 0){
                return String.format("%02d:%02d:%02d / %02d:%02d:%02d",elapsedHours,elapsedMinutes,elapsedSeconds);
            }else{
                return String.format("%02d:%02d / %02d:%02d",elapsedMinutes,elapsedSeconds);
            }
        }
    }


    @Override
    public void start(Stage stage) throws IOException {

        FileArrayCreater fileArrayCreater=new FileArrayCreater();
        System.out.println(fileArrayCreater.createFileObjectArray());
        int i=0;
          for(File f:fileArrayCreater.createFileObjectArray()){
              System.out.println(i++);
            mediaPlayers.add( new MediaPlayer(new Media(f.toURI().toString())));
          }
        mediaView=new MediaView(mediaPlayers.get(this.poistion));
        Slider slhorizon=new Slider();
        slhorizon.setMax(100);
        slhorizon.setShowTickLabels(true);
        slhorizon.setShowTickMarks(true);
        Label timelabel=new Label();
        mediaPlayers.get(poistion).setOnReady(new Runnable() {
            @Override
            public void run() {
                allDuration=mediaPlayers.get(poistion).getStopTime();

            }
        });
     mediaPlayers.get(poistion).currentTimeProperty().addListener(ov->{
            timelabel.setText(formatTime(mediaPlayers.get(poistion).getCurrentTime(),allDuration));
        });
//        allDuration=this.mediaPlayers.get(poistion).getStopTime();
//        System.out.println("================================");
//        System.out.println(allDuration);
//       slhorizon.valueProperty().addListener(ov->{
//
//           this.mediaPlayers.get(poistion).seek();
//       });
//
//        Label label=new Label("");
//        Slider slider=new Slider();
        Button back =new Button("<<");
        Button forward=new Button(">>");
        Button nextButton=new Button("next");
        Button pauseButton=new Button("pause");
        BorderPane borderPane=new BorderPane();
        HBox hBox=new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox=new VBox();
        vBox.getChildren().addAll(slhorizon,hBox);
        hBox.getChildren().addAll(timelabel,nextButton,pauseButton,back,forward);
        borderPane.setCenter(this.mediaView);
        borderPane.setBottom(vBox);




        nextButton.setOnAction(e->{   //点击事件 一旦点击 立即发生
          mediaPlayers.get(poistion).seek(Duration.INDEFINITE);
          mediaPlayers.get(poistion).setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    poistion++;
                    System.out.println(poistion);
                    mediaView.setMediaPlayer(mediaPlayers.get(poistion));
                    mediaPlayers.get(poistion).setAutoPlay(true);
                }
            });
        });

        pauseButton.setOnAction(e->{
            mediaPlayers.get(poistion).pause();
        });

        back.setOnAction(e->{
            mediaPlayers.get(poistion).seek(mediaPlayers.get(poistion).getCurrentTime().subtract(mediaPlayers.get(poistion).getCurrentTime().divide(5)));
        });

        forward.setOnAction(e->{

            allDuration=mediaPlayers.get(poistion).getStopTime();
            System.out.println(allDuration.toMillis());
            mediaPlayers.get(poistion).seek(mediaPlayers.get(poistion).getCurrentTime().add(mediaPlayers.get(poistion).getCurrentTime().divide(5)));
        });

        Scene scene=new Scene(borderPane,650,500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                String str="last poistion: "+poistion;
                try {
                    mediaConfiguration.write(file,str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

       mediaPlayers.get(poistion).currentTimeProperty().addListener(ov->{
            Duration currentTime = mediaPlayers.get(poistion).getCurrentTime();
            slhorizon.setValue(currentTime.toMillis()/allDuration.toMillis() * 100);
        });

        slhorizon.valueProperty().addListener(ov->{
            if(slhorizon.isValueChanging()) {     //加入Slider正在改变的判定，否则由于update线程的存在，mediaPlayer会不停地回绕
                mediaPlayers.get(poistion).seek(allDuration.multiply(slhorizon.getValue() / 100.0));
            }
        });



//mediaPlayer.setAutoPlay(true);
        mediaPlayers.get(poistion).setOnEndOfMedia(new Runnable() {    //无点击 自动发生
           @Override
                 public void run() {
                 poistion++;
                 System.out.println(poistion);
                 mediaView.setMediaPlayer(mediaPlayers.get(poistion));
                 mediaPlayers.get(poistion).setAutoPlay(true);
         }
    });

        mediaPlayers.get(poistion).setAutoPlay(true);
    }




    public static void main(String[] args) throws IOException {
         launch(args);
//        MediaConfiguration mediaConfiguration=new MediaConfiguration();
//        File file=mediaConfiguration.start();
//        System.out.println( mediaConfiguration.read(file)+"");
//        System.out.println(Character.isDigit(mediaConfiguration.read(file)));
////         mediaConfiguration.write(file,"last position: 1");


    }
}