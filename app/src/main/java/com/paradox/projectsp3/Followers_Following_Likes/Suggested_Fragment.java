package com.paradox.projectsp3.Followers_Following_Likes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paradox.projectsp3.R;



public class Suggested_Fragment extends Fragment {

    RecyclerView rv_following;

    public Suggested_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested_, container, false);


        initview(view);
        return view;

    }

    private void initview(View view) {



    }
}