package com.paradox.projectsp3.Practice_Following;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paradox.projectsp3.Followers_Following_Likes.FollowingAdapter;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_Following extends Fragment {

    RecyclerView rv_myfollowing;
    MyFollowing_Adapter adapter;
//    List<> data = new ArrayList<>();


    public My_Following() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initviews(view);
    }

    private void initviews(View view) {


        Pojo[] mylisrdata = new Pojo[] {
                new Pojo("Name"),

        };

       // ViewPager viewPager =view.findViewById(R.id.fragmentcontainer);

        rv_myfollowing =  view.findViewById(R.id.rv_myfollowing);
        MyFollowing_Adapter adapter = new MyFollowing_Adapter(mylisrdata);
        rv_myfollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_myfollowing.setAdapter(adapter);


//        rv_myfollowing = view.findViewById(R.id.rv_myfollowing);
//
//        rv_myfollowing = view.findViewById(R.id.rv_following);
//        rv_myfollowing.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));


    }

    }