package com.paradox.projectsp3.HomeFollowing;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.HomeRecyclerView.VideoPlayerRecyclerView;
import com.paradox.projectsp3.R;

import java.net.URI;
import java.util.List;

public class homeFollowingAdapter extends RecyclerView.Adapter<homeFollowingAdapter.myviewholder> {

    Context context;
    List<HomeFollwoingmodel>homeFollwoingmodelList;

    public homeFollowingAdapter(Context context, List<HomeFollwoingmodel> homeFollwoingmodelList) {
        this.context = context;
        this.homeFollwoingmodelList = homeFollwoingmodelList;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_duplecate,parent,false);
        return new myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_duplecate, parent, false));
//        return new homeFollowingAdapter.myviewholder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {


        holder.veideo_recyclerview.setVideoURI(Uri.parse(homeFollwoingmodelList.get(position).getMedia_url()));
        holder.veideo_recyclerview.requestFocus();
        holder.veideo_recyclerview.start();

        holder.veideo_recyclerview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.veideo_recyclerview.start();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.video_p_p){

                    holder.veideo_recyclerview.pause();

                    holder.video_p_p = false;

                }
                else {
                    holder.veideo_recyclerview.start();
                    holder.video_p_p = true;

                }

            }
        });

//        holder.veideo_recyclerview.setBackgroundColor(Color.TRANSPARENT);

//        videoView.setVideoURI(vuri);
//        videoView.requestFocus();
//        videoView.start();
        holder.textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "clck", Toast.LENGTH_SHORT).show();
            }
        });

        holder.likesn.setText("");
        holder.comments.setText("");
        holder.share.setText("");
        holder.noViews.setText("");
        holder.textView3.setText("");
        holder.textView2.setText("");

    }

    @Override
    public int getItemCount() {
        return homeFollwoingmodelList.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {

        VideoView veideo_recyclerview;
        ImageView imageView9,imageView8,imageView6,imageView7,profilepic,imageView3;
        TextView likesn,comments,share,noViews,textView3,textView2,textView4;
        Button textButton;
        Boolean video_p_p = true ;


        public myviewholder(@NonNull View itemView) {
            super(itemView);


            veideo_recyclerview = (VideoView) itemView.findViewById(R.id.videovv);

            //Images
            imageView9 = itemView.findViewById(R.id.imageView9);
            imageView8 = itemView.findViewById(R.id.imageView8);
            imageView6 = itemView.findViewById(R.id.imageView6);
            imageView7 = itemView.findViewById(R.id.imageView7);
            profilepic = itemView.findViewById(R.id.profilepic);
            imageView3 = itemView.findViewById(R.id.imageView3);

            //textviews
            textView4 = itemView.findViewById(R.id.textView4);
            likesn = itemView.findViewById(R.id.likesn);
            comments = itemView.findViewById(R.id.comments);
            share = itemView.findViewById(R.id.share);
            noViews = itemView.findViewById(R.id.noViews);
            textView3 = itemView.findViewById(R.id.textView3);
            textView3 = itemView.findViewById(R.id.textView3);
            textView2 = itemView.findViewById(R.id.textView2);
            //Button
            textButton = itemView.findViewById(R.id.textButton);





        }
    }
}
