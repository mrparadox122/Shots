package com.paradox.projectsp3.Followers_Following_Likes;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class FollowersAdapter  extends RecyclerView.Adapter<FollowersAdapter.myviewHolder> {

    List<Follower_model> follower_model = new ArrayList<>();
    Context context;


    public FollowersAdapter(Context context, List<Follower_model> follower_model){
        this.context = context;
        this.follower_model = follower_model;
        Log.e(TAG, "FollowersAdapter: "+follower_model.get(0) );
    }

    @NonNull
    @Override
    public FollowersAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_followers, parent, false);
        return new myviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.myviewHolder holder, int position) {
        holder.usenname_txt.setText(follower_model.get(position).getUsername());
        Glide.with(context).load(follower_model.get(position).getProfile_pic()).into(holder.pic_img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return follower_model.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        TextView usenname_txt;
        ImageView pic_img;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            usenname_txt = itemView.findViewById(R.id.usenname_txt);
            pic_img = itemView.findViewById(R.id.pic_img);



        }
    }
}
