package com.morley.myvideoplayer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HelloApplication extends Application {
     private   MediaView mediaView;
     private final ObservableList<MediaPlayer> mediaPlayers=FXCollections.observableArrayList();
     private int poistion;

//
//     public void init() throws IOException {
//
//         MediaConfiguration mediaConfiguration=new MediaConfiguration();
//         File file=mediaConfiguration.start();
//         this.poistion=mediaConfiguration.read(file);
//
//         mediaConfiguration.write(file,"last position: 5");
//         
//     }
//


    @Override
    public void start(Stage stage) throws IOException {

        MediaConfiguration mediaConfiguration=new MediaConfiguration();//把它变为全局变量
        File file=mediaConfiguration.start();
        this.poistion=mediaConfiguration.read(file);
        mediaConfiguration.write(file,"last position: 5");


        FileArrayCreater fileArrayCreater=new FileArrayCreater();
        System.out.println(fileArrayCreater.createFileObjectArray());
        int i=0;
          for(File f:fileArrayCreater.createFileObjectArray()){
              System.out.println(i++);
            this.mediaPlayers.add( new MediaPlayer(new Media(f.toURI().toString())));
          }
        System.out.println(this.mediaPlayers);


this.mediaView=new MediaView(this.mediaPlayers.get(this.poistion));

        Button button=new Button("<<");
        Button button1=new Button(">>");
        Button nextButton=new Button("next");
        Button pauseButton=new Button("pause");
        BorderPane borderPane=new BorderPane();
        HBox hBox=new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(nextButton,pauseButton);
        borderPane.setCenter(this.mediaView);
        borderPane.setBottom(hBox);


        nextButton.setOnAction(e->{
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

          //记忆功能



        });

        Scene scene=new Scene(borderPane,650,500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

//mediaPlayer.setAutoPlay(true);
        this.mediaPlayers.get(poistion).setOnEndOfMedia(new Runnable() {

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




    public static void main(String[] args) {
         launch(args);

    }
}