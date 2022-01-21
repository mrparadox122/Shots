package com.paradox.projectsp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.Model.Comment_Model;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class Comment_RecyclerView extends RecyclerView.Adapter<Comment_RecyclerView.comments> {
   List<Comment_Model> comment_modelList=new ArrayList<>();
   Context context;
   public Comment_RecyclerView(Context context,List<Comment_Model> comment_modelList)
   {
       this.context=context;
       this.comment_modelList=comment_modelList;
   }

    @NonNull
    @Override
    public Comment_RecyclerView.comments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_recyclerview,parent,false);

        return new comments(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Comment_RecyclerView.comments holder, int position) {
             holder.imageView.setImageResource(comment_modelList.get(position).getImage());
             holder.name.setText(comment_modelList.get(position).getUsers_name());
             holder.comment.setText(comment_modelList.get(position).getUsers_comment());
             holder.data.setText(comment_modelList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return comment_modelList.size();
    }

    public class comments extends RecyclerView.ViewHolder {

       ImageView imageView;
       TextView name;
       EditText comment;
       TextView data;
        public comments(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.user_dp);
            name=itemView.findViewById(R.id.user_name);
            comment=itemView.findViewById(R.id.user_comment);
            data=itemView.findViewById(R.id.date);
        }
    }
}
