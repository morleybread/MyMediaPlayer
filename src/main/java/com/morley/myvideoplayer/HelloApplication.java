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
     private   ObservableList<MediaPlayer> observableList; //needed

   public HelloApplication(ObservableList<MediaPlayer> observableList){
       this.observableList=observableList;
   }

    @Override
    public void start(Stage stage) throws IOException {
        File file=new File("video_test/Best air shows in the world.mp4");
        Media media=new Media(file.toURI().toString());
   MediaPlayer mediaPlayer=new MediaPlayer(media);
this.mediaView=new MediaView(mediaPlayer);

//        Button button=new Button("<<");
//        Button button1=new Button(">>");
        Button nextButton=new Button("next");
        Button pauseButton=new Button("pause");

        BorderPane borderPane=new BorderPane();

        HBox hBox=new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(nextButton,pauseButton);
        borderPane.setCenter(this.mediaView);
        borderPane.setBottom(hBox);


        nextButton.setOnAction(e->{
          mediaPlayer.seek(Duration.INDEFINITE);
        });


        Scene scene=new Scene(borderPane,650,500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

mediaPlayer.setAutoPlay(true);
mediaPlayer.setOnEndOfMedia(new Runnable() {
    @Override
    public void run() {

                    File file1=new File("video_test/The Course.mp4");
            Media media1=new Media(file1.toURI().toString());
            MediaPlayer mediaPlayer1=new MediaPlayer(media1);
           mediaView.setMediaPlayer(mediaPlayer1);
           mediaPlayer1.setAutoPlay(true);

    }
});
    }




    public static void main(String[] args) {

//         launch(args);
        File file=new File("video_test");
        System.out.println(Arrays.toString(file.list()));

//        Application.launch(args);
    }
}