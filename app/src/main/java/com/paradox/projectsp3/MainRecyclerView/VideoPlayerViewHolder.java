package com.paradox.projectsp3.MainRecyclerView;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;

    ImageView like,Share,Comment;
    TextView title;
    ImageView thumbnail, volumeControl,soundDisk;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;
    boolean checklike=true;


    TextView likesn;




    TextView commentn;




    TextView share;




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
        Share=itemView.findViewById(R.id.imageView6);
        Comment=itemView.findViewById(R.id.imageView8);







        share = itemView.findViewById(R.id.share);






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