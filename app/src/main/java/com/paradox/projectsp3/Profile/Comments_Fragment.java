package com.paradox.projectsp3.Profile;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.R;

public class Comments_Fragment extends Fragment {

    Context context;
    ImageView Goback;
    FrameLayout comment_screen;

    public Comments_Fragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments_, container, false);

        context=getContext();


        comment_screen=view.findViewById(R.id.comment_screen);


        return  view;
    }






}