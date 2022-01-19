package com.paradox.projectsp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.projectsp3.Model.SoundModel;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.VideoEditorFolder.PortraitCameraActivity;

import java.util.ArrayList;
import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    Context context;
    List<SoundModel> soundModelList=new ArrayList<>();

    public SoundAdapter(Context context,List<SoundModel> list)
    {
        this.context=context;
        this.soundModelList=list;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sound,parent,false);
        return new SoundViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        SoundModel soundModel=soundModelList.get(position);
        holder.textView.setText(soundModel.getSound_title());
        holder.imageView.setImageResource(soundModel.getSound_img());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PortraitCameraActivity.class);
                intent.putExtra("sound_url",soundModel.getSound_sound());
                intent.putExtra("sound_title",soundModel.getSound_title());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return soundModelList.size();
    }

  public class SoundViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
      public SoundViewHolder(@NonNull View itemView) {
          super(itemView);

          textView=itemView.findViewById(R.id.textView);
          imageView=itemView.findViewById(R.id.imageView);
      }
  }


}
