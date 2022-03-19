package com.paradox.projectsp3.HomeFollowing;

public class HomeFollwoingmodel {

    private String title;
    private String video_id;
    private String description;
    private String date;
    private String user_id;
    private String post_categories;
    private String post_id;
    private String views;
    private String user_name;
    private String url;
    private String thumbnail;
    private String likes;
    private String comments;
    private String profile_pic;
    private String shares;



    public HomeFollwoingmodel(String title, String video_id, String description, String date, String user_id, String post_categories, String post_id, String views, String user_name, String media_url, String thumbnail,String profile_pic)
    {
        this.video_id = video_id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.user_id =user_id;
        this.profile_pic = profile_pic;
        this.post_categories = post_categories;
        this.post_id = post_id;
        this.views = views;
        this.user_name = user_name;
        this.url = media_url;
        this.thumbnail = thumbnail;
        this.likes = likes;
        this.shares = shares;
        this.comments = comments;
        this.views = views;
    }
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }



    public HomeFollwoingmodel(){
    }
    public String getVideo_id(){
        return video_id;
    }

    public String getComments() {
        return comments;
    }

    public String getLikes() {
        return likes;
    }

    public String getShares() {
        return shares;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_categories() {
        return post_categories;
    }

    public void setPost_categories(String post_categories) {
        this.post_categories = post_categories;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMedia_url() {
        return url;
    }

    public void setMedia_url(String media_url) {
        this.url = media_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
