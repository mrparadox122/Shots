package com.paradox.projectsp3.Profile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paradox.projectsp3.Followers_Following_Likes.FollowingAdapter;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.GlobalVariables;
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

public class P_Commnets extends AppCompatActivity {


    View view;
    Context context;
    RecyclerView recyclerview;
    Comments_Adapter adapter;
    List<Comments_Model> comments_model;
    String message_editst;
//    FrameLayout comment_screen;
    EditText message_edit;
    ImageView send_btn,Goback;

    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcommnets);
        getSupportActionBar().hide();

        recyclerview = findViewById(R.id.recyclerview);
       // comment_screen=findViewById(R.id.comment_screen);
        send_btn = findViewById(R.id.send_btn);

        message_edit = findViewById(R.id.message_edit);

        comments_model = new ArrayList<>();


        Goback = findViewById(R.id.Goback);






        JSONObject data = new JSONObject();
        try {

            data.put("post_id", GlobalVariables.getPost_id());
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
                                      Comments_Model comments_model1 = new Comments_Model();
//                                    Following_Model following_model1 = new Following_Model();
                                      comments_model1.setImg_url(dataobj.getString("profile_pic"));
                                      comments_model1.setUsername(dataobj.getString("fullname"));
                                      comments_model1.setMassege(dataobj.getString("comments"));
                                      comments_model1.setUs_id(dataobj.getString("user_id"));
                                      comments_model1.setVideo_id(dataobj.getString("video_id"));
                                      comments_model1.setComment_id(dataobj.getString("comment_id"));
                                      comments_model.add(comments_model1);
//                                    following_model1.setUsername(dataobj.getString("fullname").toString());
//                                    following_model1.setProfile_pic(dataobj.getString("profile_pic").toString());
//                                    following_model1.setId(dataobj.getString("id").toString());
//                                    following_model.add(following_model1);
//                                    rv_following = view.findViewById(R.id.rv_following);
//                                    followingAdapter = new FollowingAdapter(getContext(), following_model);
//                                    rv_following.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
//                                    rv_following.setAdapter(followingAdapter);


                                    recyclerview.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

                                    adapter = new Comments_Adapter(context,comments_model);
                                    recyclerview.setAdapter(adapter);

                                    Log.e(TAG, "writeTv: "+ comments_model+comments_model1.getUsername() );
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


























        //adapter.notifyDataSetChanged();

//        comment_screen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message_editst = String.valueOf(message_edit.getText());

                if (GlobalVariables.getId() == null || GlobalVariables.getId() == "null"){
                    Toast.makeText(getApplicationContext(), "login to comment", Toast.LENGTH_SHORT).show();
                }
                else if (message_editst.equals("")){
                    Toast.makeText(getApplicationContext(), "enter comment", Toast.LENGTH_SHORT).show();
                }
                else if (GlobalVariables.getId() != null && !message_editst.equals("") ){
                    JSONObject data = new JSONObject();
                    try {

                        data.put("video_id",GlobalVariables.getVideoid());
                        data.put("komment","'"+message_edit.getText().toString()+"'");
                        data.put("user_id",GlobalVariables.getId());
                        data.put("comment_id","value-1");
                        data.put("flag","1");
                        Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getPost_id()+GlobalVariables.getVideoid());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiInterface.userdetail_following_url)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();

                    ApiInterface apii = retrofit.create(ApiInterface.class);
                    Call<String> calll = apii.getUserus_c_p(data.toString());
                    calll.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e(TAG, "Responsestring//////////////////////" + String.valueOf(response.body()));
                            //Toast.makeText()
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                if (response.body() != null) {
                                    Log.i("onSuccess", response.body().toString());
                                    String jsonresponse = response.body().toString();
                                    if (response.body().equals("s")){
                                        Toast.makeText(P_Commnets.this, "comment", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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

























                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

//                String message=message_edit.getText().toString();
//                if(!TextUtils.isEmpty(message)){
//                    // if(Variables.sharedPreferences.getBoolean(Variables.islogin,false)){
//                    // send_Comments(video_id,message);
//                    message_edit.setText(null);
//                    // send_progress.setVisibility(View.VISIBLE);
//                    send_btn.setVisibility(View.GONE);
//                }
//                else {
//                    Toast.makeText(context, "Please Login into the app", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }
}