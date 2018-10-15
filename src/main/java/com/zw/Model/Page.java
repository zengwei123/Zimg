package com.zw.Model;

import java.util.List;

public class Page {
    private List<String> s;
    private List<String> img;
    private String title;
    private String titlename;
    private String zwss;
    public Page(List<String> s, List<String> img, String title, String titlename) {
        this.s = s;
        this.img = img;
        this.title = title;
        this.titlename = titlename;
    }

    @Override
    public String toString() {
        return "Page{" +
                "s=" + s +
                ", img=" + img +
                ", title='" + title + '\'' +
                ", titlename='" + titlename + '\'' +
                '}';
    }

    public List<String> getS() {
        return s;
    }

    public void setS(List<String> s) {
        this.s = s;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getZwss() {
        return zwss;
    }

    public void setZwss(String zwss) {
        this.zwss = zwss;
    }
}
