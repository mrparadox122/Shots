package com.paradox.projectsp3.Followers_Following_Likes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.Adapter.PostAdapter;
import com.paradox.projectsp3.R;

public class FollowersAdapter  extends RecyclerView.Adapter<FollowersAdapter.myviewHolder> {



    @NonNull
    @Override
    public FollowersAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_followers, parent, false);
        return new myviewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.myviewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        public myviewHolder(@NonNull View itemView) {
            super(itemView);



        }
    }
}
