package com.paradox.projectsp3.Followers_Following_Likes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paradox.projectsp3.R;

import java.util.List;


public class Suggest_Fragment extends Fragment {


    RecyclerView rv_suggest;
    //Following_Model following_model;
    List<Suggest_Model> suggest_models;
    Suggest_Adapter suggest_adapter;

    public Suggest_Fragment() {
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
        View view= inflater.inflate(R.layout.fragment_suggest_, container, false);

        initviews(view);

        return  view;
    }

    private void initviews(View view) {


        rv_suggest = view.findViewById(R.id.rv_suggest);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_suggest.setLayoutManager(layoutManager3);
        Suggest_Adapter adapter = new Suggest_Adapter(getContext(), suggest_models);
        rv_suggest.setAdapter(adapter);
    }
}