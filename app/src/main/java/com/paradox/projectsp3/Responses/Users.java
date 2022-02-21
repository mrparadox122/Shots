package com.paradox.projectsp3.Responses;

import com.google.gson.annotations.SerializedName;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.Model.SoundModel;
import com.paradox.projectsp3.Model.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class Users {

    @SerializedName("soosle_details")
    private List<MediaObject> AllPosts;
    @SerializedName("soosle_AllSound")
    private List<SoundModel> AllSounds;



    @SerializedName("read.php")
    private List<UserDetails> AllDetails;




    private List<Following_Model> Allfollowing;



    public List<MediaObject> getAllPosts()
    {
        return AllPosts;
    }

    public List<SoundModel> getAllSounds()
    {
        return AllSounds;
    }

    public List<UserDetails> getAllDetails() {
        return AllDetails;
    }

    public List<Following_Model> getAllFollowing()
    {
        return Allfollowing;
    }



}
