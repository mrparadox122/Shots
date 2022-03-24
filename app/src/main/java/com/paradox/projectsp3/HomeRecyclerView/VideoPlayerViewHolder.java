package com.paradox.projectsp3.HomeRecyclerView;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.HomeComment.HomeComment_Activity;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.OthersProfiles.OthersProfile_Activity;
import com.paradox.projectsp3.Profile.Comments_Adapter;
import com.paradox.projectsp3.Profile.Comments_Model;
import com.paradox.projectsp3.Profile.P_Commnets;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;
    ImageView like,Share,Comment;
    TextView title,views;
    ImageView thumbnail, volumeControl,soundDisk;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;
    LinearLayout CommentView;
    TextView likesn;
    TextView commentn;
    TextView share;
    Context context;
    String mediaObjectUrl;
    Boolean checklike = true;
    Boolean checkKomment = false;
    String video_id;
    String video_id_to_c;
    String us_id_to_c;
    int likesno;
    int likesnominus;
    int shareno;
    ImageView profilepic;
    TextView u_nam;
    Comments_Adapter cmadapter;
    List<Comments_Model> comments_model;
    Comments_Model comments_model1;
    int i;
    ImageView Goback,send_btn;
    RecyclerView rv_comments;
    String message_editst;
    EditText message_edit;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        views = itemView.findViewById(R.id.noViews);
        media_container = itemView.findViewById(R.id.media_container);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        title = itemView.findViewById(R.id.textView3);
        progressBar = itemView.findViewById(R.id.progressBar);
        volumeControl = itemView.findViewById(R.id.imageView12);
        soundDisk= itemView.findViewById(R.id.imageView3);
        like=itemView.findViewById(R.id.imageView9);
        likesn = itemView.findViewById(R.id.likesn);
        commentn = itemView.findViewById(R.id.comments);
        Comment=itemView.findViewById(R.id.imageView8);
        context= itemView.getContext();
        Share=itemView.findViewById(R.id.imageView6);
        CommentView=itemView.findViewById(R.id.commentView);
        share =itemView.findViewById(R.id.share);
        profilepic =itemView.findViewById(R.id.profilepic);
        u_nam =itemView.findViewById(R.id.textView4);
        checkKomment = false;
        comments_model = new ArrayList<>();
        Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context1= itemView.getContext();
                Dialog dialog=new Dialog(context1);
                dialog.setContentView(R.layout.newcommentlist);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                rv_comments = dialog.findViewById(R.id.recylerview_comm);
                Goback = dialog.findViewById(R.id.Goback);
                message_edit = dialog.findViewById(R.id.message_edit);
                send_btn = dialog.findViewById(R.id.send_btn);
                dialog.show();

                JSONObject data = new JSONObject();
                try {
                    data.put("video_id",video_id_to_c);
                    Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getPost_id()+video_id_to_c);
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
                        Goback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        send_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                message_editst = String.valueOf(message_edit.getText());
                                if (GlobalVariables.getId() == null || GlobalVariables.getId() == "null"){
                                    Toast.makeText(context1.getApplicationContext(), "login to comment", Toast.LENGTH_SHORT).show();
                                }
                                else if (message_editst.equals("")){
                                    Toast.makeText(context1.getApplicationContext(), "enter comment", Toast.LENGTH_SHORT).show();
                                }
                                else if (GlobalVariables.getId() != null && !message_editst.equals("") ){
                                    JSONObject data = new JSONObject();
                                    try {
                                        data.put("video_id",video_id_to_c);
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
                                        public void onResponse(Call<String> call, Response<String> response1) {
                                            Log.e(TAG, "Responsestring//////////////////////" + String.valueOf(response1.body()));
                                            //Toast.makeText()
                                            if (response1.isSuccessful()) {
                                                Gson gson = new Gson();
                                                if (response1.body() != null) {
                                                    Log.i("onSuccess", response1.body().toString());
                                                    String jsonresponse = response1.body().toString();
                                                    if (response1.body().equals("ss1")){
                                                        message_edit.setText("");
                                                        int nc = Integer.parseInt(commentn.getText().toString());
                                                        nc+=1;
                                                        commentn.setText(String.valueOf(nc));
                                                        Toast.makeText(context1.getApplicationContext(), "comment added", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();


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
                        });

                        //Toast.makeText()
                        if (response.isSuccessful()) {
                            comments_model.clear();



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
                                            comments_model1 = new Comments_Model();

                                            comments_model1.setImg_url(dataobj.getString("profile_pic"));
                                            comments_model1.setUsername(dataobj.getString("fullname"));
                                            comments_model1.setMassege(dataobj.getString("comments"));
                                            comments_model1.setUs_id(dataobj.getString("user_id"));
                                            comments_model1.setVideo_id(dataobj.getString("video_id"));
                                            comments_model1.setComment_id(dataobj.getString("comment_id"));
                                            comments_model1.setPost_id(dataobj.getString("post_id"));
                                            comments_model.add(comments_model1);
                                            rv_comments.setLayoutManager(new LinearLayoutManager(context1,RecyclerView.VERTICAL,false));
                                            cmadapter = new Comments_Adapter(context1,comments_model);
                                            rv_comments.setAdapter(cmadapter);
                                        }
                                    }else {
                                        rv_comments.setLayoutManager(new LinearLayoutManager(context1,RecyclerView.VERTICAL,false));

                                        cmadapter = new Comments_Adapter(context1,comments_model);
                                        rv_comments.setAdapter(cmadapter);

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

        });







        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checklike)
                {
                    JSONObject dislike = new JSONObject();
                    try {
                        dislike.put("us_id_who_liked",GlobalVariables.getId());
                        dislike.put("uploader_us_id",us_id_to_c);
                        dislike.put("video_id", video_id);
                        dislike.put("flag", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit.Builder retrofit = new Retrofit.Builder()
                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit2 = retrofit.build();


                    //get client
                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                    Call<String> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                          //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: "+response.body()+GlobalVariables.getId());

                            likesn.setText(String.valueOf(likesno));
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                        }
                    });
                    like.setImageResource(R.drawable.ic_icon_material_favorite_red);
                    checklike=false;
                }
                else if (checklike!=true)
                {
                    JSONObject dislike = new JSONObject();
                    try {
                        dislike.put("us_id_who_liked",GlobalVariables.getId());
                        dislike.put("uploader_us_id",us_id_to_c);
                        dislike.put("video_id", video_id);
                        dislike.put("flag", "4");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit.Builder retrofit = new Retrofit.Builder()
                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit2 = retrofit.build();

                    //get client
                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                    Call<String> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                             likesn.setText(String.valueOf(likesnominus));
                            Log.e(TAG, "onResponse: "+response.body()+GlobalVariables.getId() );
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                        }
                    });


                    like.setImageResource(R.drawable.ic_icon_material_favorite);
                    checklike=true;
                }
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context sharecontext= itemView.getContext();
//                Dialog dialog=new Dialog(sharecontext);
//                dialog.setContentView(R.layout.activity_share_bottom_sheet);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.show();

                JSONObject dislike = new JSONObject();
                try {
                    dislike.put("video_id", video_id);
                    dislike.put("flag", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Retrofit.Builder retrofit = new Retrofit.Builder()
                        .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit2 = retrofit.build();


                //get client
                ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                Call<String> call_like = apiInterface.getStringScalar(dislike.toString());
                call_like.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()){
                            shareno+=1;
                            share.setText(String.valueOf(shareno));
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                    }
                });

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("video/mp4");
                String body = String.valueOf(mediaObjectUrl);
                String sub = "Shots";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                context.startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void onBind(MediaObject mediaObject, RequestManager requestManager) throws IOException {
        this.requestManager = requestManager;
        parent.setTag(this);




        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("username", mediaObject.getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Unirest.setTimeouts(0, 0);
//            String response = Unirest.post("http://13.127.217.99/dashboard/update.php")
//                    .header("Content-Type", "text/plain")
//                    .body("{\r\n    \"video_id\": \"14\",\r\n    \"flag\": \"6\"\r\n}")
//                    .toString();
//            Log.e(TAG, "onBind://////////// "+response);

        Retrofit.Builder retrofit1 = new Retrofit.Builder()
                .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit3 = retrofit1.build();


        //get client
        ApiInterface apiInterface1 = retrofit3.create(ApiInterface.class);
        Call<String> calll = apiInterface1.getStringScalar_for_hm(jsonObject1.toString());
        calll.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: "+response.body() );


                if (response.body() != null) {
                    Log.i("onSuccess", response.body().toString());
                    String jsonresponse = response.body().toString();

                    try {
                        //getting the whole json object from the response
                        JSONObject obj = new JSONObject(jsonresponse);
                        if (true) {

                            JSONArray dataArray = obj.getJSONArray("body");
                            for (i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                u_nam.setText(dataobj.getString("fullname"));
                                Glide.with(itemView.getContext()).load(dataobj.getString("profile_pic")).into(profilepic);



                            }

                        } else {


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }













            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_LONG).show();
            }

        });





















        mediaObjectUrl = mediaObject.getMedia_url();
        likesn.setText(mediaObject.getLikes());
        likesnominus = Integer.parseInt(mediaObject.getLikes());
        likesno = 0b1 + Integer.parseInt(mediaObject.getLikes());
        commentn.setText(mediaObject.getComments());
        share.setText(mediaObject.getShares());

        shareno = Integer.parseInt(mediaObject.getShares());


        title.setText(mediaObject.getDescription()+"\n"+mediaObject.getPost_categories());

        this.requestManager.load(mediaObject.getThumbnail()).into(thumbnail);
        ////// set view to video
        video_id = mediaObject.getVideo_id().toString();
        video_id_to_c = mediaObject.getVideo_id().toString();
        us_id_to_c = mediaObject.getUser_id().toString();
        checkKomment = false;

        u_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables globalVariables = new GlobalVariables();
                globalVariables.Setus_id_ofr_singl_vid(mediaObject.getUser_id());
                Intent intent = new Intent(context,OthersProfile_Activity.class);
                context.startActivity(intent);



            }
        });

//        String users = (video_id +
//                "    \"flag\": \"6\"\n" +
//                "}");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("video_id", video_id);
            jsonObject.put("flag", "6");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Unirest.setTimeouts(0, 0);
//            String response = Unirest.post("http://13.127.217.99/dashboard/update.php")
//                    .header("Content-Type", "text/plain")
//                    .body("{\r\n    \"video_id\": \"14\",\r\n    \"flag\": \"6\"\r\n}")
//                    .toString();
//            Log.e(TAG, "onBind://////////// "+response);

        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit2 = retrofit.build();


        //get client
        ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
        Call<String> call = apiInterface.getStringScalar(jsonObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                 views.setText(mediaObject.getViews());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_LONG).show();
            }

        });

        JSONObject dislike = new JSONObject();
        try {
            dislike.put("us_id_who_liked",GlobalVariables.getId());
            dislike.put("uploader_us_id",mediaObject.getUser_id());
            dislike.put("video_id", video_id);
            dislike.put("flag", "8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Retrofit.Builder retrofit89 = new Retrofit.Builder()
                .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit29 = retrofit89.build();

        //get client
        ApiInterface apiInterface9 = retrofit29.create(ApiInterface.class);
        Call<String> call_like = apiInterface9.getStringScalar(dislike.toString());
        call_like.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("dfsrx")){

                    like.setImageResource(R.drawable.ic_icon_material_favorite_red);
                    checklike=false;
                }
                else {
                    checklike=true;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

            }
        });


//        ApiInterface service = retrofit1.create(ApiInterface.class);
//        Call<Users> call = service.update(users);
//        Log.e(TAG, "onBind: /////////////////"+String.valueOf(users));
//        call.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                Log.i("mok","S"+response);
//                views.setText(mediaObject.getViews());
//            }
//
//            @Override
//            public void onFailure(Call<Users> call,Throwable t) {
//
//                Log.i("mok","F"+t);
//                Log.i("mok",t.getCause()+"");
//                Log.i("mok","T");
//
//            }
//        });
    }

}