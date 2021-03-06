package com.paradox.projectsp3.Profile;

import android.annotation.SuppressLint;
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
import com.paradox.projectsp3.OthersProfiles.OthersProfile_Activity;
import com.paradox.projectsp3.R;

import java.util.List;

public class Comments_Adapter  extends RecyclerView.Adapter<Comments_Adapter.myviewholder> {

    Context context;
    List<Comments_Model>comments_modelList;

    public Comments_Adapter(Context context, List<Comments_Model> comments_modelList) {
        this.context = context;
        this.comments_modelList = comments_modelList;
    }


    @NonNull
    @Override
    public Comments_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Comments_Adapter.myviewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.username.setText(comments_modelList.get(position).getUsername());
        holder.message.setText(comments_modelList.get(position).getMassege());
        Glide.with(holder.itemView.getContext()).load(comments_modelList.get(position).getImg_url()).into(holder.user_pic);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables globalVariables = new GlobalVariables();
                globalVariables.Setus_id_ofr_singl_vid(comments_modelList.get(position).getUs_id());
                Intent intent = new Intent(holder.itemView.getContext(),OthersProfile_Activity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments_modelList.size() ;
    }

    public class myviewholder extends RecyclerView.ViewHolder  {

        Context context1;
        ImageView user_pic;
        TextView username,message;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            user_pic = itemView.findViewById(R.id.user_pic);
            username = itemView.findViewById(R.id.username);
            message = itemView.findViewById(R.id.message);




        }


    }
}
