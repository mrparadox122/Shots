package com.paradox.projectsp3.Followers_Following_Likes;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.myviewHolder> {


    Context context;
    List<Following_Model> followingModels = new ArrayList<>();
    ImageView pic_img;
    TextView txt_name;

    boolean followclick;

    public FollowingAdapter(Context context, List<Following_Model> followingModels) {
        this.context = context;
        this.followingModels = followingModels;
        Log.e(TAG, "FollowingAdapter: "+followingModels.get(0) );
//        Log.e(TAG, "FollowingAdapter: "+followingModels.get(1) );

    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_following, parent, false);
        return new FollowingAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        
        //Log.e(TAG, "onBindViewHolder: "+Entries.get(0).getUsername() );
        Log.e(TAG, "onBindViewHolder: "+followingModels.size() );
        holder.txt_name.setText(String.valueOf(followingModels.get(position).getUsername()));
        Glide.with(context).load(followingModels.get(position).getProfile_pic()).into(holder.pic_img);

        holder.btn_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, followingModels.get(position).getId().toString(), Toast.LENGTH_SHORT).show();

                //holder.ll_relative.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {

        return followingModels.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView txt_name;
        Button btn_following;

        LinearLayout ll_relative;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            pic_img = itemView.findViewById(R.id.pic_img);
            txt_name = itemView.findViewById(R.id.txt_name);
            btn_following = itemView.findViewById(R.id.btn_following);
            ll_relative = itemView.findViewById(R.id.ll_relative);

        }
    }
}
