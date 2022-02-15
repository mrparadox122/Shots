package com.paradox.projectsp3.Model;

public class UserDetails {


    private String id;
    private String fulllname;
    private String email;
    private String Gender;
    private String PhoneNumber;
    private String ProfilePic;
    private String Dob;



    private String following;
    private String followers;
    private String total_likes;



    private String bio;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFulllname() {
        return fulllname;
    }

    public void setFulllname(String fulllname) {
        this.fulllname = fulllname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }


    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }




//    public UserDetails(String id, String fulllname, String email, String Gender, String PhoneNumber, String ProfilePic, String Dob){
//        this.id = id;
//        this.fulllname = fulllname;
//        this.email = email;
//        this.Gender = Gender;
//        this.PhoneNumber = PhoneNumber;
//        this.ProfilePic = ProfilePic;
//        this.Dob = Dob;
//    }


}
