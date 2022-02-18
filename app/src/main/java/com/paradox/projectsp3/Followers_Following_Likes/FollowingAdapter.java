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

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.myviewHolder> {


    Context context;
    List<Following_Model> Entries;


    public FollowingAdapter(Context context, List<Following_Model> entries, Following_Fragment following_fragment) {
        this.context = context;
        this.Entries = entries;

    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_following, parent, false);
        return new FollowingAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Following_Model following_model = new Following_Model();
        Log.e(TAG, "onBindViewHolder: "+following_model.getName()+following_model.getProfilepic() );
        Log.e(TAG, "onBindViewHolder: "+Entries.toString() );
        holder.txt_name.setText(Entries.get(position).getName());


        Glide.with(this.context).load(String.valueOf(following_model.getProfilepic())).into(holder.pic_img);

    }

    @Override
    public int getItemCount() {
        Following_Model following_model = new Following_Model();
        Log.e(TAG, "onBindViewHolder: "+following_model.getName()+following_model.getProfilepic() );
        Log.e(TAG, "onBindViewHolder: "+Entries.toString() );
        return Entries.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView txt_name;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            pic_img = itemView.findViewById(R.id.pic_img);
            txt_name = itemView.findViewById(R.id.txt_name);

        }


    }
}
