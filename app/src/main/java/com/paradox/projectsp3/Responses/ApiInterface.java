package com.paradox.projectsp3.Responses;

import org.json.JSONArray;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    ////getting posts///
    @GET("get_details")
    Call<Users> performAllPosts();

//
//    @POST("upload")
//    Call<JSONArray> postUpload(@Body List<data> dataList);

    /// getting all sounds ////
    @GET("sounds")
    Call<Users> PerformAllSounds();


    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part RequestBody body);
    // You can add other parameters too



}
