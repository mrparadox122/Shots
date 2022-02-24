package com.paradox.projectsp3.Followers_Following_Likes;

public class Follower_model {

    private String Username;
    private String profile_pic;


//    private String img;

    public Follower_model(){

    }
    public Follower_model(String Username, String profile_pic) {
        this.Username = Username;
        this.profile_pic = profile_pic;
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



}
