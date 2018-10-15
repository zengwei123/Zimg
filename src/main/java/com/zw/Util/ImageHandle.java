package com.zw.Util;

import com.sun.deploy.net.HttpResponse;
import com.zw.Model.Page;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImageHandle {
    public String wh(String s, String name, int w, int h){
        String pathname=getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"a.jpg";
        File file=new File(pathname);
        String urlstr=s;//此处为指定指定图片的url,以百度logo为例
        downloadCompressedPicture(file, urlstr);
        try {
            BufferedImage srcImage = srcImage = ImageIO.read(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"a.jpg"));
            int srcImageHeight = srcImage.getHeight();
            int srcImageWidth = srcImage.getWidth();
            BufferedImage srcImagez=cropImage(srcImage,0,0,srcImageWidth-w,srcImageHeight-h);
            File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+name+".jpg");
            ImageIO.write(srcImagez, "png", f);
            return name+".jpg'/></div>";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 裁剪图片方法
     * @param bufferedImage 图像源
     * @param startX 裁剪开始x坐标
     * @param startY 裁剪开始y坐标
     * @param endX 裁剪结束x坐标
     * @param endY 裁剪结束y坐标
     * @return
     */
    public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * url下载压缩图
     * jdk1.8可以执行
     * jdk1.4无法执行
     * @param file
     * @param urlstr
     * @return
     */
    public static boolean downloadCompressedPicture(File file,String urlstr){
        try{
            URL url=new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //设置用户代理
            // 设置通用的请求属性

            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("Referer", " https://f12.baidu.com/");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124");
            //1.获取url的输入流 dataInputStream
            DataInputStream dataInputStream=new DataInputStream(con.getInputStream());
            //2.加一层BufferedInputStream
            BufferedInputStream bufferedInputStream=new BufferedInputStream(dataInputStream);
            //3.构造原始图片流 preImage
            BufferedImage preImage=ImageIO.read(bufferedInputStream);
            //4.获得原始图片的长宽 width/height
            int width=preImage.getWidth();
            int height=preImage.getHeight();
            //5.构造压缩后的图片流 image 长宽各为原来的1/2
            BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //6.给image创建Graphic ,在Graphic上绘制压缩后的图片
            Graphics graphic=image.createGraphics();
            graphic.drawImage(preImage, 0, 0, width, height, null);
            //7.为file生成对应的文件输出流
            //将image传给输出流
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            //8.将image写入到file中
            ImageIO.write(image, "bmp", bufferedOutputStream);
            //9.关闭输入输出流
            bufferedInputStream.close();
            bufferedOutputStream.close();

            return true;
        }catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 网络请求
     * @param urlStr
     * @return
     */
    public static String doget(String urlStr) {
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(urlStr);
            org.apache.http.HttpResponse response = null;
            response = client.execute(request);
            response.setHeader("accept", "*/*");
            response.setHeader("connection", "Keep-Alive");
            response.setHeader("Referer", " https://f12.baidu.com/");
            response.setHeader("content-type", "text/html;charset=utf-8");
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(),"utf-8");
                return strResult;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Page getNode(String s){
        List<String> sz=new ArrayList<>();
        List<String> img=new ArrayList<>();
        if(s!=null){
            Document  doc = Jsoup.parse(s);
            Elements s1=doc.select("#page_wrapper #content_wrapper div:nth-child(1) .contentText.contentSize.contentPadding");
            for(int i=0;i<s1.size();i++){
                sz.add(s1.get(i).text());
            }
            s1=doc.select("#page_wrapper #content_wrapper div:nth-child(1) .contentMedia.contentPadding div img");
            for(int i=0;i<s1.size();i++){
                img.add(s1.get(i).attr("src"));
            }
            String title=doc.select("#content_wrapper .landingHead .titleSize").text();
            String titlename=doc.select("#content_wrapper .landingHead .layout.columnCenter.rowBetween .extraInfo a").text();
            return new Page(sz,img,title,titlename);
        }else{
            return null;
        }
    }



    public static void main(String[] args) {
        String s=doget("https://mbd.baidu.com/newspage/data/landingshare?context=%7B%22nid%22%3A%22news_8523156324263067527%22%2C%22sourceFrom%22%3A%22bjh%22%7D&type=gallery&qq-pf-to=pcqq.c2c");
        Page page=getNode(s);
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        List<String> strings=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<page.getImg().size();i++){
            stringBuilder.append(page.getS().get(i)+new ImageHandle().wh(page.getImg().get(i),formatter.format(date)+"_"+i,0,40));
        }
        page.setZwss(stringBuilder.toString());
        System.out.println(page.getZwss());
//      wh("https://f11.baidu.com/it/u=2924843809,1114142286&fm=175&app=25&f=JPEG?w=547&h=827&s=958B26F10E1674DE6B34217A0300C054&access=215967316","123");
    }
}
