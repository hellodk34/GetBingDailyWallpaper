package com.hellodk.getbingdailywallpaper.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: hellodk
 * @description main service class
 * @date: 8/11/2021 2:55 PM
 */

@Configuration
public class MainService {

    static String apiUrl = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
    static String baseUrl = "http://bing.com";
    static String finalImageUrl = "";
    static String copyright = "";
    static String copyrightlink = "";

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
            //System.out.println(sb);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getInfo() {
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
            return JSONObject.parseObject(sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
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
            InputStream in = new URL(finalImageUrl).openStream();

            Files.deleteIfExists(Paths.get(filePath));
            Files.copy(in, Paths.get(filePath));
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
