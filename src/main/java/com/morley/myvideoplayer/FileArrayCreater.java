package com.morley.myvideoplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


//排序 实现compare接口
public class FileArrayCreater {
    //返回 dot之前的数字
    public int getseq(String str){
        return  Integer.parseInt(str.substring(0,str.indexOf('.')));
    }



    //文件夹排序 文件排序
    public ArrayList<File> createFileObjectArray(){
        ObservableList<MediaPlayer> observableList = null;
        ArrayList<File> files=new ArrayList<>();
        ArrayList<File> tempfiles=new ArrayList<>();
        ArrayList<File>  result=new ArrayList<>();

        for(File m: Objects.requireNonNull(new File("video_test").listFiles())){
//            System.out.println(m);//打印一级目录文件对象
            files.add(m);


//
//            for (File f: Objects.requireNonNull(m.listFiles())){
//                if(f.isFile()&&f.getName().lastIndexOf(4)==-1){  //判断是否为文件 并且为mp4结尾
//                    files.add(f);
//                }
//            }
//

        }
        System.out.println(files);

        files.sort(new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                if ((char)file.getName().charAt(0)>(char) t1.getName().charAt(0)){
                    return 1;
                }else {
                    return -1;
                }

            }
        });

        System.out.println(files);


        for (File s:files){
            for (File f: Objects.requireNonNull(s.listFiles())){
                if(f.isFile()&&f.getName().lastIndexOf(4)==-1){  //判断是否为文件 并且为mp4结尾
                    tempfiles.add(f);
                }
            }
            tempfiles.sort(new Comparator<File>() {
                @Override
                public int compare(File file, File t1) {
                    if ((char)file.getName().charAt(0)>(char) t1.getName().charAt(0)){
                        return 1;
                    }else {
                        return -1;
                    }
                }
            });
            for (File r:tempfiles){
                result.add(r);
            }
            tempfiles.clear();
        }
        return result;
    }


    public static void main(String[] args) {
        FileArrayCreater fileArrayCreater =new FileArrayCreater();
        System.out.println(fileArrayCreater.createFileObjectArray());


    }

}
