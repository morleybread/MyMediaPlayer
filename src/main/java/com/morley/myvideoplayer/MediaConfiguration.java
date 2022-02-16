package com.morley.myvideoplayer;

import java.io.*;

public class MediaConfiguration {

public File start(){
    String config=this.getClass().getClassLoader().getResource("MediaConfiguration.txt").getFile();
    File f=new File(config);
    return f;

}

public char read(File file) throws IOException { //返回位置
    FileReader fr=new FileReader(file);
    char[]a =new char[200];
    int len =0;
     StringBuilder result= new StringBuilder();
    while ((len=fr.read(a))!=-1){

        result.append(new String(a, 0, len));
    }
    fr.close();
    return result.toString().charAt(result.toString().length()-2);
}



public void write(File file,String str) throws IOException {
    FileWriter fw=new FileWriter(file,true);
    fw.write("\n"+str+"\n");
     fw.flush();
     fw.close();
}

    public static void main(String[] args) throws IOException {
        MediaConfiguration mediaConfiguration=new MediaConfiguration();
        File file=mediaConfiguration.start();
        System.out.println( mediaConfiguration.read(file)+"");
        System.out.println(Character.isDigit(mediaConfiguration.read(file)));
//         mediaConfiguration.write(file,"last position: 1");

    }

}
