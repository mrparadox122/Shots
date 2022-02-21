package com.paradox.projectsp3.Followers_Following_Likes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.http.Body;

public class Following_Model  {

    private String Username;
//    private String img;

    public Following_Model(String username) {
        Username = username;
//        this.img = img;
    }

    public String getUsername() {
        return Username;
    }


}
