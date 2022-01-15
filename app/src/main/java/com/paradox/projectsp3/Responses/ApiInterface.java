package com.paradox.projectsp3.Responses;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    Call<ResponseBody> postUser(@Part("video")RequestBody video,@Part("data")String des);


}
