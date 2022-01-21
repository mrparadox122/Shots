package com.paradox.projectsp3.Responses;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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


}
