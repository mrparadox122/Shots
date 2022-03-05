package com.paradox.projectsp3.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;


public class CommentsFragment extends Fragment {


    View view;
    Context context;
    RecyclerView recyclerView;
    Comments_Adapter adapter;
    List<Comments_Model> comments_modelList;
    FrameLayout comment_screen;

    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        comment_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        view.findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        initviews(view);
        return  view;
    }

    private void initviews(View view) {

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        comments_modelList = new ArrayList<>();
        adapter = new Comments_Adapter(getContext(),comments_modelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}