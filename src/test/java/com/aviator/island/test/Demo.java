package com.aviator.island.test;

import java.io.*;
import java.net.URL;

/**
 * Created by 18057046 on 2018/7/26.
 */
public class Demo {
    public static void main(String[] args){
//        try {
//            download("https://cdn.bootcss.com/unslider/2.0.3/js/unslider-min.js", "E:/download");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(2/5);
    }

    public static void download(String urlPath, String filePath) throws IOException {
        BufferedReader reader = null;
        OutputStream out = null;
        try{
            URL url = new URL(urlPath);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")); // 实例化输入流，并获取网页代码
            out = new FileOutputStream(filePath, true);
            String s; // 依次循环，至到读的值为空
            while ((s = reader.readLine()) != null) {
                out.write(s.getBytes());
            }
        }finally {
            if(reader != null){
                reader.close();
            }
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }
}
