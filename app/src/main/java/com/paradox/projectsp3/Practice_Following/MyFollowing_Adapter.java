package com.paradox.projectsp3.Practice_Following;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.Followers_Following_Likes.FollowingAdapter;
import com.paradox.projectsp3.R;
import com.squareup.picasso.Picasso;

public class MyFollowing_Adapter extends RecyclerView.Adapter<MyFollowing_Adapter.Holder> {


    private Pojo[] listdata;
    Context context;

    public MyFollowing_Adapter(Pojo[] listdata) {
        this.listdata = listdata;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_following, parent, false);
        return new MyFollowing_Adapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.txt_name.setText(listdata[position].getUsername());
//        holder.pic_img.setImageResource(Integer.parseInt(listdata[position].getImg()));
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView txt_name;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            pic_img = itemView.findViewById(R.id.pic_img);

        }
    }
}
