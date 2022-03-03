package com.paradox.projectsp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Model.My_VideosModel;
import com.paradox.projectsp3.Profile.MyVideosScreen_Activity;
import com.paradox.projectsp3.R;

import java.util.List;

public class MyVideos_Adapter extends RecyclerView.Adapter<MyVideos_Adapter.myviewHolder> {

    Context context;
    List<My_VideosModel>my_videosModelList;
//    GlobalVariables g = new GlobalVariables();

    public MyVideos_Adapter(Context context, List<My_VideosModel> my_videosModelList) {
        this.context = context;
        this.my_videosModelList = my_videosModelList;
    }

    @NonNull
    @Override
    public MyVideos_Adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myvideo_layout,parent,false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideos_Adapter.myviewHolder holder, int position) {
        context = holder.itemView.getContext();
        holder.view_txt.setText(my_videosModelList.get(position).getViewtext());

        Glide.with(context).load(my_videosModelList.get(position).getImg_url()).into(holder.thumb_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MyVideosScreen_Activity.class);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return my_videosModelList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {


        ImageView thumb_image;
        TextView view_txt;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            thumb_image = itemView.findViewById(R.id.thumb_image);
            view_txt = itemView.findViewById(R.id.view_txt);
        }
    }
}
