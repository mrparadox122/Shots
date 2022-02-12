package com.paradox.projectsp3.Responses;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
   public static final String BASE_URL="http://13.127.217.99:8080/soosleApi/soosle/";
   public static final String BASE_URL2="http://13.127.217.99:/dashboard/paradoxApi/";
   public static final String BASE_URL1="http://13.127.217.99/dashboard/";
   public static Retrofit retrofit;
   public static Retrofit retrofit2;
   public static Retrofit retrofit1;
//   public static Retrofit retrofit=null;
   public static Retrofit getApiClient()
   {
      // if(retrofit == null)
       //{

          retrofit1 = new Retrofit.Builder().baseUrl(BASE_URL1).addConverterFactory(GsonConverterFactory.create()).build();
           retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
      // }

       return  retrofit;
   }
   public static Retrofit getUserDetails()
   {
       retrofit2 = new Retrofit.Builder().baseUrl(BASE_URL2).addConverterFactory(GsonConverterFactory.create()).build();
       return retrofit2;
   }

}
