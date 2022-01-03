package com.paradox.projectsp3.Responses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    ////getting posts///
    @GET("get_details")
    Call<Users> performAllPosts();

}
