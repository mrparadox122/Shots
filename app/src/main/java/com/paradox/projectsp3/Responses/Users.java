package com.paradox.projectsp3.Responses;

import com.google.gson.annotations.SerializedName;
import com.paradox.projectsp3.Model.MediaObject;

import java.util.List;


public class Users {
    @SerializedName("soosle_details")
    private List<MediaObject> AllPosts;


    public List<MediaObject> getAllPosts()
    {
        return AllPosts;
    }



}