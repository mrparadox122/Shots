package com.paradox.projectsp3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paradox.projectsp3.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.holder> {

    int[] images;
    public SliderAdapter(int[] images){
        this.images = images;
    }


    @Override
    public SliderAdapter.holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_layout,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapter.holder viewHolder, int position) {

        viewHolder.image_view.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class holder extends ViewHolder {

        ImageView image_view;
        public holder(View itemView) {
            super(itemView);

            image_view = itemView.findViewById(R.id.image_view);


        }

    }
}
