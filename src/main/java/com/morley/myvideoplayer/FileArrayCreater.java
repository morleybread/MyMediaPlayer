package com.morley.myvideoplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


//排序 实现compare接口
public class FileArrayCreater {

    public ArrayList<File> createFileObjectArray(){
        ObservableList<MediaPlayer> observableList = null;
        ArrayList<File> files=new ArrayList<>();
        for(File m: Objects.requireNonNull(new File("video_test").listFiles())){
//            System.out.println(m);//打印一级目录文件对象
            for (File f: Objects.requireNonNull(m.listFiles())){
                if(f.isFile()&&f.getName().lastIndexOf(4)==-1){  //判断是否为文件 并且为mp4结尾
                    files.add(f);
                }
            }
        }
        return files;
    }




    public static void main(String[] args) {
        FileArrayCreater fileArrayCreater =new FileArrayCreater();
        System.out.println(fileArrayCreater.createFileObjectArray());

    }

}
