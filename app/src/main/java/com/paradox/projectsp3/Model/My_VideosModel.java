package com.paradox.projectsp3.Model;

public class My_VideosModel {

    String img_url;
    String viewtext;
    String videoid;

    public My_VideosModel() {

    }

    public My_VideosModel(String img_url, String viewtext,String videoid) {
        this.img_url = img_url;
        this.viewtext = viewtext;
        this.videoid = videoid;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getViewtext() {
        return viewtext;
    }

    public void setViewtext(String viewtext) {
        this.viewtext = viewtext;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }
}
