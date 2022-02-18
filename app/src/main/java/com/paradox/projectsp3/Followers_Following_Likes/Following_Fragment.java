package com.paradox.projectsp3.Followers_Following_Likes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;


public class Following_Fragment extends Fragment {

    RecyclerView rv_following;
    List<Following_Model> followingdata = new ArrayList<>();
    FollowingAdapter followingAdapter;

    public Following_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_, container, false);

        initviews(view);

        return view;
    }

    private void initviews(View view) {

        rv_following = view.findViewById(R.id.rv_following);
      followingAdapter = new FollowingAdapter(getContext(),followingdata,this);
       rv_following.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
       rv_following.setAdapter(followingAdapter);




    }
}