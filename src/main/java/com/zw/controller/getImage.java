package com.zw.controller;

import com.google.gson.Gson;
import com.zw.Model.Page;
import com.zw.Util.ImageHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class getImage {
    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;

    @ResponseBody
    @GetMapping("/Image")
    public void img(@RequestParam("name") String name){
        try {
            File file=new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+name);
            FileInputStream inputStream=new FileInputStream(file);
            byte buffer[]=new byte[1024];//设置缓冲区
            int len=0;
            OutputStream out=response.getOutputStream();
            while((len=inputStream.read(buffer))>0){
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/caijianImage")
    public String getImage(@RequestParam("urls") String urls){
        try {
            String s=ImageHandle.doget(urls);
            Page page=ImageHandle.getNode(s);
            Date date=new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0;i<page.getImg().size();i++){
                stringBuilder.append("<div class='jsonpaiban'>"+page.getS().get(i)+"</div>"+" <div  class='jsonpaiban'><img src='http://zengwei123.top:40872/Image?name="+new ImageHandle().wh(page.getImg().get(i),formatter.format(date)+"_"+i,0,40));
            }
            page.setZwss(stringBuilder.toString());
            Gson gson=new Gson();
            return "{\"message\":\"ok\",\"mez\":"+gson.toJson(page,Page.class)+"}";
        }catch (Exception e){
            return "{\"message\":\"error\"}";
        }
    }

    @GetMapping("/index")
    public String Login(){
        return "index";
    }
}
