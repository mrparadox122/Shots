package com.paradox.projectsp3.Model;

public class Comment_Model {


    int Image;
    String users_name;
    String users_comment;
    String date;


    public Comment_Model(int Image,String users_name,String users_comment,String data)
    {
        this.Image=Image;
        this.users_comment=users_comment;
        this.date=data;
        this.users_name=users_name;
    }



    public int getImage() {
        return Image;
    }

    public String getDate() {
        return date;
    }

    public String getUsers_comment() {
        return users_comment;
    }

    public String getUsers_name() {
        return users_name;
    }
}
