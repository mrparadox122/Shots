package com.paradox.projectsp3.Followers_Following_Likes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.http.Body;

public class Following_Model  {


    private String Username;
    private String profile_pic;


    private String id;


//    private String img;

    public Following_Model(){

    }
    public Following_Model(String Username,String profile_pic,String id) {
        this.Username = Username;
        this.profile_pic = profile_pic;
        this.id = id;
//        this.img = img;
    }




    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }




    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }







}
