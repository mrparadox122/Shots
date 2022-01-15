package com.paradox.projectsp3.Responses;

import org.json.JSONArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    ////getting posts///
    @GET("get_details")
    Call<Users> performAllPosts();

    @POST("upload")
    Call<ResponseBody> postUpload(@Body ResponseBody responseBody);

    /// getting all sounds ////
    @GET("sounds.php")
    Call<Users> PerformAllSounds();


}
