package com.paradox.projectsp3.Followers_Following_Likes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.http.Body;

public class Following_Model implements Serializable {
    public Extras extras;

    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }



    @SerializedName("body")
    @Expose
    private List<Body> body = null;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
    public Extras getExtras(){
        return extras;
    }
    public class Extras{
        @SerializedName("body")
        @Expose
        private List<Body> body = null;
        @SerializedName("itemCount")
        @Expose
        private Integer itemCount;

        public List<Body> getBody() {
            return body;
        }

        public void setBody(List<Body> body) {
            this.body = body;
        }

    }

}
