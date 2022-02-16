package com.morley.myvideoplayer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

   public HelloApplication() throws IOException {

           mediaConfiguration=new MediaConfiguration();
           file=mediaConfiguration.start();
           poistion=mediaConfiguration.read(file)-48;


   }





    @Override
    public void start(Stage stage) throws IOException {

        FileArrayCreater fileArrayCreater=new FileArrayCreater();
        System.out.println(fileArrayCreater.createFileObjectArray());
        int i=0;
          for(File f:fileArrayCreater.createFileObjectArray()){
              System.out.println(i++);
            this.mediaPlayers.add( new MediaPlayer(new Media(f.toURI().toString())));
          }
        System.out.println(this.mediaPlayers);

        System.out.println(this.poistion);
        System.out.println(this.poistion+"jgdirthgi");
        this.mediaView=new MediaView(this.mediaPlayers.get(this.poistion));

        Slider slhorizon=new Slider();
        slhorizon.setShowTickLabels(true);
        slhorizon.setShowTickMarks(true);
//       slhorizon.valueProperty().addListener(ov->{
//
//           this.mediaPlayers.get(poistion).seek();
//       });



        Button back =new Button("<<");
        Button forward=new Button(">>");
        Button nextButton=new Button("next");
        Button pauseButton=new Button("pause");
        BorderPane borderPane=new BorderPane();
        HBox hBox=new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(nextButton,pauseButton,slhorizon,back,forward);
        borderPane.setCenter(this.mediaView);
        borderPane.setBottom(hBox);




        nextButton.setOnAction(e->{   //点击事件 一旦点击 立即发生
          this.mediaPlayers.get(poistion).seek(Duration.INDEFINITE);
          this.mediaPlayers.get(poistion).setOnEndOfMedia(new Runnable() {
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
            this.mediaPlayers.get(poistion).pause();
        });
        back.setOnAction(e->{
            this.mediaPlayers.get(poistion).seek(this.mediaPlayers.get(poistion).getCurrentTime().divide(100));
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



//mediaPlayer.setAutoPlay(true);
        this.mediaPlayers.get(poistion).setOnEndOfMedia(new Runnable() {    //无点击 自动发生
           @Override
                 public void run() {
                 poistion++;
                 System.out.println(poistion);
                 mediaView.setMediaPlayer(mediaPlayers.get(poistion));
                 mediaPlayers.get(poistion).setAutoPlay(true);


         }
    });
        this.mediaPlayers.get(poistion).setAutoPlay(true);

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