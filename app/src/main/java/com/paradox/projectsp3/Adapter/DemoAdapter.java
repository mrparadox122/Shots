package com.paradox.projectsp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.Model.MediaObject;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {


    List<MediaObject> mediaObjectList;
    Context context;

    public List<MediaObject> getMediaObjectList() {
        return mediaObjectList;
    }

    public void setMediaObjectList(List<MediaObject> mediaObjectList) {
        this.mediaObjectList = mediaObjectList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int viewType) {
        View view = LayoutInflater.from()
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mediaObjectList.size();
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {
        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
