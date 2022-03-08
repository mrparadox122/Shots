package com.paradox.projectsp3.HomeComment;

public class HomeCommentModel {

    String username;
    String massege;
    String img_url;

    public HomeCommentModel() {
    }

    public HomeCommentModel(String username, String massege, String img_url) {
        this.username = username;
        this.massege = massege;
        this.img_url = img_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

}
