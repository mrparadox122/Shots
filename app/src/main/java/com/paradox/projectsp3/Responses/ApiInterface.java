package com.paradox.projectsp3.Responses;

import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.Model.UserDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiInterface {

    String userdetailurl = "http://13.127.217.99/dashboard/paradoxApi/read.php/";
    String userfoto = "http://13.127.217.99/dashboard/paradoxApi/profile_pic/";
    @POST("read.php")
    Call<String> getUserdetails(@Body String body);
    @Multipart
    @POST("upload.php")
    Call<String> upload_pic(@Part("subject") String data,@Part MultipartBody.Part file);


    String userdetail_following_url = "http://13.127.217.99/dashboard/paradoxApi/";
    @POST("get_following.php")
    Call<String> getUserdetails_following(@Body String body);
    @POST("us_c.php")
    Call<String> getUserus_c(@Body String body);
    @POST("us_c_p.php")
    Call<String> getUserus_c_p(@Body String body);

    @POST("get_followers.php")
    Call<String> getUserdetails_followers(@Body String body);

    ////getting posts///
    @GET("get_details")
    Call<Users> performAllPosts();


    ////getting UserDetails///


//
//    @POST("upload")
//    Call<JSONArray> postUpload(@Body List<data> dataList);

    /// getting all sounds ////
    @GET("sounds.php")
    Call<Users> PerformAllSounds();

    @Multipart
    @POST("upload")
    Call<Users> upload(@Part("data") RequestBody description, @Part MultipartBody.Part thumbnail,  @Part MultipartBody.Part video);

    @PUT("update.php")
    Call<Users> update(@Body String data);
    @POST("update.php")
    Call<ResponseBody> getStringScalar(@Body String body);
    @POST("read.php")
    Call<ResponseBody> getStringuser(@Body String body);
    @POST("update_user.php")
    Call<ResponseBody> getStringuser_update(@Body String body);
    @Multipart
    @POST("follow_unfollo.php")
    Call<String> unfollo_follo(@Part MultipartBody.Part idmy, @Part MultipartBody.Part idpr, @Part MultipartBody.Part flag);

    @POST("get_suggested.php")
    Call<String> getUserdetails_suggestion(@Body String body);
    @POST("UserVideos.php")
    Call<String> getUserdetails_videos(@Body String body);
    @POST("Sv.php")
    Call<String> getUserdetails_video(@Body String body);

}
