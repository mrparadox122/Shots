package com.paradox.projectsp3.Model;

public class UserDetails {
    private String id,fulllname,email,Gender,PhoneNumber,ProfilePic,Dob;

    public UserDetails(String id, String fulllname, String email, String Gender, String PhoneNumber, String ProfilePic, String Dob){
        this.id = id;
        this.fulllname = fulllname;
        this.email = email;
        this.Gender = Gender;
        this.PhoneNumber = PhoneNumber;
        this.ProfilePic = ProfilePic;
        this.Dob = Dob;
    }




    public String getId() {
        return id;
    }

    public String getFulllname() {
        return fulllname;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return Gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public String getDob() {
        return Dob;
    }
}
