package com.paradox.projectsp3.MainRecyclerView;


import static android.content.ContentValues.TAG;

import static com.paradox.projectsp3.Responses.ApiClient.retrofit;
import static com.paradox.projectsp3.Responses.ApiClient.retrofit1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.MessageMainActivity;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;
import com.paradox.projectsp3.ShareBottomSheetActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;

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
    String video_id;
    int likesno;
    int likesnominus;

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
        share = itemView.findViewById(R.id.share);


        Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context1= itemView.getContext();
                Dialog dialog=new Dialog(context1);
                dialog.setContentView(R.layout.activity_comment);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
            }

        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(checklike)
                {
                    JSONObject dislike = new JSONObject();
                    try {
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
                    Call<ResponseBody> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                            likesn.setText(String.valueOf(likesno));
                        }



                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                        }
                    });
                    like.setImageResource(R.drawable.ic_icon_material_favorite_red);
                    checklike=false;
                }
                else if (!checklike)
                {
                    JSONObject dislike = new JSONObject();
                    try {
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
                    Call<ResponseBody> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(context.getApplicationContext(), "//"+"disliked"+response, Toast.LENGTH_SHORT).show();
                             likesn.setText(String.valueOf(likesnominus));

                        }



                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
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


                Context sharecontext= itemView.getContext();
                Dialog dialog=new Dialog(sharecontext);
                dialog.setContentView(R.layout.activity_share_bottom_sheet);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
//
//                Intent myIntent = new Intent(Intent.ACTION_SEND);
//                myIntent.setType("video/mp4");
//                String body = String.valueOf(mediaObjectUrl);
//                String sub = "Your Subject";
//                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
//                myIntent.putExtra(Intent.EXTRA_TEXT,body);
//                context.startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void onBind(MediaObject mediaObject, RequestManager requestManager) throws IOException {
        this.requestManager = requestManager;
        parent.setTag(this);
        mediaObjectUrl = mediaObject.getMedia_url();
        likesn.setText(mediaObject.getLikes());
        likesnominus = Integer.parseInt(mediaObject.getLikes());
        likesno = 0b1 + Integer.parseInt(mediaObject.getLikes());




        commentn.setText(mediaObject.getComments());
        commentn.setText(mediaObject.getShares());


        title.setText(mediaObject.getDescription()+"\n"+mediaObject.getPost_categories());

        this.requestManager.load(mediaObject.getThumbnail()).into(thumbnail);
        ////// set view to video
        video_id = mediaObject.getVideo_id().toString();

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
        Call<ResponseBody> call = apiInterface.getStringScalar(jsonObject.toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 views.setText(mediaObject.getViews());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_LONG).show();
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