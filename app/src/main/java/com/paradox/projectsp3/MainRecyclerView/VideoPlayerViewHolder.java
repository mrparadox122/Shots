package com.paradox.projectsp3.MainRecyclerView;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.MessageMainActivity;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.ShareBottomSheetActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;
    ImageView like,Share,Comment;
    TextView title;
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
    boolean checklike=true;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
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
                if(checklike==true)
                {
                    like.setImageResource(R.drawable.ic_icon_material_favorite_red);
                    checklike=false;
                }
                else
                {
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
        commentn.setText(mediaObject.getComments());
        commentn.setText(mediaObject.getShares());

        title.setText(mediaObject.getDescription()+"\n"+mediaObject.getPost_categories());

        this.requestManager.load(mediaObject.getThumbnail()).into(thumbnail);
        ////// set view to video
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"video_id\" : \"14\",\r\n    \"flag\" : \"3\"\r\n}");
        Request request = new Request.Builder()
                .url("http://13.127.217.99/dashboard/update.php")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();



    }

}