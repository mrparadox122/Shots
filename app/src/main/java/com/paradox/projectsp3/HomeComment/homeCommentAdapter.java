package com.paradox.projectsp3.HomeComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.R;

import java.util.List;

public class homeCommentAdapter extends RecyclerView.Adapter<homeCommentAdapter.myviewholder> {

    Context context;
    List<HomeCommentModel> homeCommentModelList;

    public homeCommentAdapter(Context context, List<HomeCommentModel> homeCommentModelList) {
        this.context = context;
        this.homeCommentModelList = homeCommentModelList;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.username.setText(homeCommentModelList.get(position).getUsername());
        holder.message.setText(homeCommentModelList.get(position).getMassege());

        //Glide.with(context).load(homeCommentModelList.get(position).getImg_url()).into(holder.user_pic);
    }

    @Override
    public int getItemCount() {
        return homeCommentModelList.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

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
