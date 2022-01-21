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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.paradox.projectsp3.Message;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.R;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {
      Fragment fragment;
      FragmentManager fragmentManager;
      FragmentTransaction fragmentTransaction;
      FrameLayout media_container;
      ImageView like,Share,Comment;
      TextView title;
      ImageView thumbnail, volumeControl,soundDisk;
      ProgressBar progressBar;
      View parent;
      RequestManager requestManager;
      LinearLayout CommentView;
      boolean checklike=true;
      TextView likesn;
      TextView commentn;
      TextView share;
      Context context;
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
              dialog.setContentView(R.layout.comment_message);
              LinearLayout linearLayout1=dialog.findViewById(R.id.Layout_message);
              LinearLayout linearLayout2=dialog.findViewById(R.id.Layout_comment);

              dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
              dialog.getWindow().setGravity(Gravity.BOTTOM);

               linearLayout1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Dialog dialog1=new Dialog(context1);
                       dialog1.setContentView(R.layout.activity_message);
                       dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                       dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       dialog1.getWindow().setGravity(Gravity.BOTTOM);
                       dialog1.show();

                   }

               });
               linearLayout2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Dialog dialog2=new Dialog(context1);
                       dialog2.setContentView(R.layout.activity_comment);
                       dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                       dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       dialog2.getWindow().setGravity(Gravity.BOTTOM);
                       dialog2.show();

                   }
               });

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

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("video/mp4");
                String body = "Your body here";
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                context.startActivity(Intent.createChooser(myIntent, "Share Using"));



            }
        });





    }



    @SuppressLint("SetTextI18n")
    public void onBind(MediaObject mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        likesn.setText(mediaObject.getLikes());
        commentn.setText(mediaObject.getComments());
        commentn.setText(mediaObject.getShares());

        title.setText(mediaObject.getDescription()+"\n"+mediaObject.getPost_categories());

        this.requestManager.load(mediaObject.getThumbnail()).into(thumbnail);
    }

}