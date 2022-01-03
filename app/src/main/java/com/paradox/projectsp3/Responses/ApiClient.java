package com.paradox.projectsp3.Responses;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
   public static final String BASE_URL="13.127.217.99/dashboard/api.php";
   public static Retrofit retrofit;
  // public static Retrofit retrofit=null;
   public static Retrofit getApiClient()
   {
      // if(retrofit == null)
       //{
           retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
      // }
       return  retrofit;
   }

}
