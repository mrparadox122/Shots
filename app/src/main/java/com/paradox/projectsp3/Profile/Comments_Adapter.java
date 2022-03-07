package com.paradox.projectsp3.Profile;

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

public class Comments_Adapter  extends RecyclerView.Adapter<Comments_Adapter.myviewholder> {

    Context context;
    List<Comments_Model>cmcomments_modelList;

    public Comments_Adapter(P_Commnets context, List<Comments_Model> comments_modelList) {
        this.context = (Context) context;
        this.cmcomments_modelList = comments_modelList;
    }

    @NonNull
    @Override
    public Comments_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Comments_Adapter.myviewholder holder, int position) {


        holder.username.setText(cmcomments_modelList.get(position).getUsername());
        holder.message.setText(cmcomments_modelList.get(position).getMassege());

        Glide.with(context).load(cmcomments_modelList.get(position).getImg_url()).into(holder.user_pic);
    }

    @Override
    public int getItemCount() {
        return cmcomments_modelList.size() ;
    }

    public class myviewholder extends RecyclerView.ViewHolder  {

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
