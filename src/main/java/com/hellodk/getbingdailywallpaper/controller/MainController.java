package com.hellodk.getbingdailywallpaper.controller;

import com.alibaba.fastjson.JSONObject;
import com.hellodk.getbingdailywallpaper.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: hellodk
 * @description main controller
 * @date: 8/11/2021 3:42 PM
 */

@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(method = RequestMethod.GET, value = "url")
    public String getUrl() {
        mainService.getBingDailyWallpaperInfo();
        return mainService.getFinalImageUrl();
    }

    @RequestMapping(method = RequestMethod.GET, value = "copyright")
    public String getCopyright() {
        mainService.getBingDailyWallpaperInfo();
        return mainService.getCopyright();
    }

    @RequestMapping(method = RequestMethod.GET, value = "copyrightlink")
    public String getCopyrightLink() {
        mainService.getBingDailyWallpaperInfo();
        return mainService.getCopyrightlink();
    }

    @RequestMapping(method = RequestMethod.GET, value = "info")
    public JSONObject getInfo() {
        return mainService.getInfo();
    }

    @RequestMapping(value = "image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageAsResource() throws IOException {
        mainService.getBingDailyWallpaperInfo();
        mainService.getImage();

        File file = new File("bing.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }

}
