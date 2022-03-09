package com.paradox.projectsp3.Profile;

public class Comments_Model {


    String username;
    String massege;
    String img_url;



    String us_id;




    String comment_id;
    String video_id;



    String post_id;



    public Comments_Model() {
    }

    public Comments_Model(String username, String massege, String img_url,String us_id,String comment_id,String video_id,String post_id) {
        this.username = username;
        this.massege = massege;
        this.img_url = img_url;
        this.us_id = us_id;
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.video_id = video_id;

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


    public String getUs_id() {
        return us_id;
    }

    public void setUs_id(String us_id) {
        this.us_id = us_id;
    }


    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
