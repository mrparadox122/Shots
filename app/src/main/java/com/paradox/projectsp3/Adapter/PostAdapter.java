package com.paradox.projectsp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.VideoEditorFolder.PortraitCameraActivity;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postviewholder> {

    private static final String TAG = "RecyclerViewAdapter";


    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public PostAdapter(Context context, ArrayList<String> imageUrls) {

        mImageUrls = imageUrls;
        mContext = context;
    }

    @NonNull
    @Override
    public postviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_posts, parent, false);
        return new postviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postviewholder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.mypost);


        holder.mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, HomeActivty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class postviewholder extends RecyclerView.ViewHolder {

        ImageView  mypost;

        public postviewholder(@NonNull View itemView) {
            super(itemView);
            mypost = itemView.findViewById(R.id.mypost);

        }
    }
}
