package com.morley.myvideoplayer;

import java.io.File;
import java.io.PrintWriter;

public class MediaConfiguration {

public File start(){
    String config=this.getClass().getClassLoader().getResource("MediaConfiguration.txt").getFile();
    File f=new File(config);
    return f;

}

    public static void main(String[] args) {
        MediaConfiguration mediaConfiguration=new MediaConfiguration();
        File file=mediaConfiguration.start();
    }

}
