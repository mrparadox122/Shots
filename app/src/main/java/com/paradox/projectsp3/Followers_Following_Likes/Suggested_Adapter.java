package com.paradox.projectsp3.Followers_Following_Likes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.R;

public class Suggested_Adapter extends RecyclerView.Adapter<Suggested_Adapter.myviewholder> {


    @NonNull
    @Override
    public Suggested_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_suggested, parent, false);
        return new Suggested_Adapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Suggested_Adapter.myviewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView txt_name;
        Button follow_btn,remove_btn;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            pic_img = itemView.findViewById(R.id.pic_img);
            txt_name = itemView.findViewById(R.id.txt_name);
            follow_btn = itemView.findViewById(R.id.follow_btn);
            remove_btn = itemView.findViewById(R.id.remove_btn);
        }
    }
}
