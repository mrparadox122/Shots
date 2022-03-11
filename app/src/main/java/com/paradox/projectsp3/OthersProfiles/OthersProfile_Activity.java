package com.paradox.projectsp3.OthersProfiles;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.paradox.projectsp3.Adapter.MyVideos_Adapter;
import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Model.My_VideosModel;
import com.paradox.projectsp3.Model.UserDetails;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OthersProfile_Activity extends AppCompatActivity {


    ImageView o_pro_pic;
    LinearLayout o_following_ll,o_follower_ll;

    SwipeRefreshLayout refrestly;


    RecyclerView o_recyclerview;
    TextView following_text,followers_text,likes_text,pro_name,bio;
    int i;


    List<OthersProfileModel>othersProfileModelList;
    OthersProfileAdapter othersProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);
        getSupportActionBar().hide();

        othersProfileModelList = new ArrayList<>();

        o_pro_pic = findViewById(R.id.o_pro_pic);
        o_following_ll = findViewById(R.id.o_following_ll);
        o_follower_ll = findViewById(R.id.o_follower_ll);
        o_recyclerview = findViewById(R.id.o_recyclerview);
        pro_name = findViewById(R.id.pro_name);
        following_text = findViewById(R.id.following_text);
        bio = findViewById(R.id.bio);
        followers_text = findViewById(R.id.followers_text);
        likes_text = findViewById(R.id.likes_text);
        refrestly = findViewById(R.id.refrestly);
        getResponse();

        refrestly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });








        JSONObject dataa = new JSONObject();
        try {
            GlobalVariables globalVariables = new GlobalVariables();
            dataa.put("username",globalVariables.getOther_p_ud());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + globalVariables.getOther_p_ud());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofittt = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface apiii = retrofittt.create(ApiInterface.class);
        Call<String> callll = apiii.getUserdetails_videos(dataa.toString());
        callll.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "Responsestring//////////////////////" + String.valueOf(response.body()));
                //Toast.makeText()
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        //Following_Fragment following_fragment = new Following_Fragment();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(jsonresponse);
                            if(true){
                                ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("body");

                                for (i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    OthersProfileModel othersProfileModel = new OthersProfileModel();
                                    My_VideosModel my_videosModel = new My_VideosModel();
//                                    Suggest_Model suggest_model = new Suggest_Model();
//                                    suggest_model.setUsernamee(dataobj.getString("fullname").toString());
//                                    suggest_model.setProfile_picc(dataobj.getString("profile_pic").toString());
//                                    suggest_model.setIdd(dataobj.getString("id").toString());
                                    othersProfileModel.setImg_url(dataobj.getString("url"));
                                    othersProfileModel.setViewtext(dataobj.getString("views"));
                                    othersProfileModel.setVideoid(dataobj.getString("videoid"));
                                    othersProfileModel.setPid(dataobj.getString("post_id"));
                                    //suggestmodel.add(suggest_model);
                                    othersProfileModelList.add(othersProfileModel);

                                    LinearLayoutManager layoutManager4 = new LinearLayoutManager(OthersProfile_Activity.this, LinearLayoutManager.HORIZONTAL, false);
                                    o_recyclerview.setLayoutManager(layoutManager4);
                                    othersProfileAdapter  = new OthersProfileAdapter(OthersProfile_Activity.this,othersProfileModelList);
                                    o_recyclerview.setAdapter(othersProfileAdapter);

//                                    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                                    suggest_rv.setLayoutManager(layoutManager3);
//                                    Suggest_Adapter adapter = new Suggest_Adapter(getApplicationContext(),suggestmodel);
//                                    suggest_rv.setAdapter(adapter);
                                    //following_model.add(following_model1);


                                    //Log.e(TAG, "writeTv: "+ GlobalVariables.getFullname()+GlobalVariables.getUsername()+following_model+following_model1.getUsername() );
                                }
                                for (int j = 0; j < UserDetailsArrayList.size(); j++){
//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), obj.optString("message")+"", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OthersProfile_Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }

        });










    }

    private void getResponse(){
        JSONObject data = new JSONObject();
        try {
            GlobalVariables globalVariables = new GlobalVariables();
            data.put("username", globalVariables.getOther_p_ud());

            Log.e(TAG, "getResponse:json data put for api"+globalVariables.getOther_p_ud());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getUsersOp(data.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", String.valueOf(response.body()));
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        writeTv(jsonresponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeTv(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(true){

                ArrayList<UserDetails> UserDetailsArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("body");

                for (int i = 0; i < dataArray.length(); i++) {

                    UserDetails UserDetails = new UserDetails();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    pro_name.setText(dataobj.getString("fullname"));
                    Glide.with(this).load(dataobj.getString("profile_pic")).into(o_pro_pic);
                    followers_text.setText(dataobj.getString("followers"));
                    following_text.setText(dataobj.getString("following"));
                    likes_text.setText(dataobj.getString("total_likes"));
                    bio.setText(dataobj.getString("bio"));



//                    id = dataobj.getString("id");
//                    add_details();
//                    Log.e(TAG, "writeTv: "+GlobalVariables.getFullname()+GlobalVariables.getUsername() );
                }

                for (int j = 0; j < UserDetailsArrayList.size(); j++){

//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                }
            }else {
                Toast.makeText(this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}