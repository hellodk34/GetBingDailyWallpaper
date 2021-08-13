package com.hellodk.getbingdailywallpaper.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: hellodk
 * @description main service class
 * @date: 8/11/2021 2:55 PM
 */

@Configuration
public class MainService {

    String apiUrl = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    String baseUrl = "http://bing.com";
    String finalImageUrl = "";
    String copyright = "";
    String copyrightlink = "";

    public void getBingDailyWallpaperInfo() {
        HttpURLConnection con;
        BufferedReader bufferedReader;
        try {
            URL url = new URL(apiUrl);
            // 得到连接对象
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((finalImageUrl = bufferedReader.readLine()) != null) {
                sb.append(finalImageUrl);
            }
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            if (jsonObject != null) {
                JSONArray images = jsonObject.getJSONArray("images");
                if (images != null && images.size() > 0) {
                    JSONObject theImage = images.getJSONObject(0);
                    String imageUrl = theImage.getString("url");
                    finalImageUrl = baseUrl.concat(imageUrl);
                    copyright = theImage.getString("copyright");
                    copyrightlink = theImage.getString("copyrightlink");
                }
            }

            bufferedReader.close();
            con.disconnect();
            //System.out.println(sb);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getInfo() {
        HttpURLConnection con;
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            // 得到连接对象
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((finalImageUrl = bufferedReader.readLine()) != null) {
                sb.append(finalImageUrl);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(sb.toString());
    }

    public String getFinalImageUrl() {
        return finalImageUrl;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getCopyrightlink() {
        return copyrightlink;
    }

    public void getImage() {
        try {
            String filePath = "bing.jpg";
            URL url = new URL(finalImageUrl);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            // 构造 1KB 的数据缓冲字节数组
            byte[] bs = new byte[1024];
            int len;
            File file = new File(filePath);
            // 如果已经存在文件则将其删除
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream os = new FileOutputStream(file, true);
            // 不断读取输入流
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 关闭连接
            is.close();
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
