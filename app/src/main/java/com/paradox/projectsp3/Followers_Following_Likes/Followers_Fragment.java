package com.paradox.projectsp3.Followers_Following_Likes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paradox.projectsp3.Adapter.PostAdapter;
import com.paradox.projectsp3.R;


public class Followers_Fragment extends Fragment {

    RecyclerView rv_followers;

    public Followers_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_followers_, container, false);

        initviews(view);

        return view;
    }

    private void initviews(View view) {

        rv_followers = view.findViewById(R.id.rv_followers);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_followers.setLayoutManager(layoutManager3);
        FollowersAdapter adapter = new FollowersAdapter();
        rv_followers.setAdapter(adapter);

    }


}