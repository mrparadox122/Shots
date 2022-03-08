package com.paradox.projectsp3.HomeComment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Profile.Comments_Model;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeComment_Activity extends AppCompatActivity {

    View view;
    Context context;
    RecyclerView hm_recyclerview;
    homeCommentAdapter homeadapter;

    int i;


    List<HomeCommentModel> homeCommentModelList;
    //    FrameLayout comment_screen;
    EditText message_edit;
    ImageView send_btn,Goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_comment);

        getSupportActionBar().hide();

        hm_recyclerview = findViewById(R.id.hm_recyclerview);
        // comment_screen=findViewById(R.id.comment_screen);
        send_btn = findViewById(R.id.send_btn);


        homeCommentModelList = new ArrayList<>();


        Goback = findViewById(R.id.Goback);















        JSONObject data = new JSONObject();
        try {


            data.put("video_id",GlobalVariables.getVideoid());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getPost_id()+GlobalVariables.getVideoid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getUserus_c(data.toString());
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
                        //Following_Fragment following_fragment = new Following_Fragment();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(jsonresponse);
                            if(true){
                                ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("body");
                                for (i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    HomeCommentModel homecommentmodle1 = new HomeCommentModel();
                                    homecommentmodle1.setImg_url(dataobj.getString("profile_pic"));
                                    homecommentmodle1.setUsername(dataobj.getString("fullname"));
                                    homecommentmodle1.setMassege(dataobj.getString("comments"));
                                    homeCommentModelList.add(homecommentmodle1);

//

                                    hm_recyclerview.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

                                    homeadapter = new homeCommentAdapter(context,homeCommentModelList);
                                    hm_recyclerview.setAdapter(homeadapter);


                                    Log.e(TAG, "writeTv: "+ homeCommentModelList+homecommentmodle1.getUsername() );
                                }
                                for (int j = 0; j < UserDetailsArrayList.size(); j++){
//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                                }
                            }else {
                                Toast.makeText(context, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
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



























        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
//
////                String message=message_edit.getText().toString();
////                if(!TextUtils.isEmpty(message)){
////                    // if(Variables.sharedPreferences.getBoolean(Variables.islogin,false)){
////                    // send_Comments(video_id,message);
////                    message_edit.setText(null);
////                    // send_progress.setVisibility(View.VISIBLE);
////                    send_btn.setVisibility(View.GONE);
////                }
////                else {
////                    Toast.makeText(context, "Please Login into the app", Toast.LENGTH_SHORT).show();
////                }

            }
        });

    }
}