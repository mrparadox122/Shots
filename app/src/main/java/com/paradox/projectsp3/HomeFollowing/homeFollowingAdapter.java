package com.paradox.projectsp3.HomeFollowing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.R;

import java.util.List;

public class homeFollowingAdapter extends RecyclerView.Adapter<homeFollowingAdapter.myviewholder> {

    Context context;
    List<homeFollwoingmodel>homeFollwoingmodelList;

    public homeFollowingAdapter(Context context, List<homeFollwoingmodel> homeFollwoingmodelList) {
        this.context = context;
        this.homeFollwoingmodelList = homeFollwoingmodelList;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main,parent,false);
        return new homeFollowingAdapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {


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
