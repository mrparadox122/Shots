package com.paradox.projectsp3.Responses;

import com.google.gson.annotations.SerializedName;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.Model.SoundModel;

import java.util.ArrayList;
import java.util.List;


public class Users {

    @SerializedName("soosle_details")
    private List<MediaObject> AllPosts;
    @SerializedName("soosle_AllSound")
    private List<SoundModel> AllSounds;



    public List<MediaObject> getAllPosts()
    {
        return AllPosts;
    }
    public List<SoundModel> getAllSounds()
    {
        return AllSounds;
    }



}
