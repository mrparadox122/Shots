package com.paradox.projectsp3.Comments;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.comments> {


    public Context context;
    private Comment_Adapter.OnItemClickListener listener;
    private ArrayList<Comment_Model> dataList;

    public Comment_Adapter(Context context, ArrayList<Comment_Model> dataList)  {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }



    public interface OnItemClickListener {
        void onItemClick(int positon, Comment_Model item, View view);
    }


    @NonNull
    @Override
    public Comment_Adapter.comments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recyclerview_details,parent,false);

        return new comments(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Comment_Adapter.comments holder, int position) {
        holder.username.setText( dataList.get(position).getUsers_name());
        Picasso.get().
                load(dataList.get(position).getImage())
                .resize(50, 50)
                .placeholder(context.getResources().getDrawable(R.drawable.find_user_male))
                .into(holder.user_pic);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class comments extends RecyclerView.ViewHolder {

        TextView username, message;
        ImageView user_pic;
        public comments(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            user_pic = itemView.findViewById(R.id.user_pic);
            message = itemView.findViewById(R.id.message);

        }
}}
