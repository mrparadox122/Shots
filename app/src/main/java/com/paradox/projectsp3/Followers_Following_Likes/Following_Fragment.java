package com.paradox.projectsp3.Followers_Following_Likes;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Following_Fragment extends Fragment {

    public String id;
    int i;
    RecyclerView rv_following;
    //Following_Model following_model;
    List<Following_Model> following_model;
    FollowingAdapter followingAdapter;

    public Following_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        following_model = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_, container, false);
        initviews(view);
        return view;
    }

    private void initviews(View view) {

        JSONObject data = new JSONObject();
        try {

            data.put("username", GlobalVariables.getId());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getUserdetails_following(data.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "Responsestring//////////////////////" + String.valueOf(response.body()));
                //Toast.makeText()
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        Following_Fragment following_fragment = new Following_Fragment();
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(jsonresponse);
                                if(true){
                                    ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
                                    JSONArray dataArray  = obj.getJSONArray("body");
                                    for (i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        Following_Model following_model1 = new Following_Model();
                                        following_model1.setUsername(dataobj.getString("fullname").toString());
                                        following_model1.setProfile_pic(dataobj.getString("profile_pic").toString());
                                        following_model1.setId(dataobj.getString("id").toString());
                                        following_model.add(following_model1);
                                        rv_following = view.findViewById(R.id.rv_following);
                                        followingAdapter = new FollowingAdapter(getContext(), following_model);
                                        rv_following.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                                        rv_following.setAdapter(followingAdapter);

                                        Log.e(TAG, "writeTv: "+ GlobalVariables.getFullname()+GlobalVariables.getUsername()+following_model+following_model1.getUsername() );
                                    }
                                    for (int j = 0; j < UserDetailsArrayList.size(); j++){
//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                                    }
                                }else {
                                    Toast.makeText(getContext(), obj.optString("message")+"", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: //////////////////" + t);

            }
        });



    }



}