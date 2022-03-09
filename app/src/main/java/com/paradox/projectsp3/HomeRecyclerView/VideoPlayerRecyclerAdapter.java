package com.paradox.projectsp3.HomeRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.R;

import java.io.IOException;
import java.util.ArrayList;


public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MediaObject> mediaObjects;
    private RequestManager requestManager;
    int j;


    public VideoPlayerRecyclerAdapter(ArrayList<MediaObject> mediaObjects, RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new com.paradox.projectsp3.HomeRecyclerView.VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_main, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        try {


            ((VideoPlayerViewHolder) viewHolder).onBind(mediaObjects.get(i), requestManager);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }

}