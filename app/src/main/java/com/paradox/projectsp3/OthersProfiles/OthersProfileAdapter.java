package com.paradox.projectsp3.OthersProfiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.R;

import java.util.List;

public class OthersProfileAdapter extends RecyclerView.Adapter<OthersProfileAdapter.myviewholder> {

    Context context;
    List<OthersProfileModel>othersProfileModelList;


    public OthersProfileAdapter(Context context, List<OthersProfileModel> othersProfileModelList) {
        this.context = context;
        this.othersProfileModelList = othersProfileModelList;
    }

    @NonNull
    @Override
    public OthersProfileAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_othersvideo_layout, parent, false);
        return new OthersProfileAdapter.myviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OthersProfileAdapter.myviewholder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myviewholder extends RecyclerView.ViewHolder {



        public myviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
