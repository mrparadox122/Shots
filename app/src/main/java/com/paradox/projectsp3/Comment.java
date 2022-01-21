package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.paradox.projectsp3.Adapter.Comment_RecyclerView;
import com.paradox.projectsp3.Model.Comment_Model;

import java.util.ArrayList;
import java.util.List;

public class Comment extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Comment_Model> comment_modelList=new ArrayList<>();
    Comment_Model comment_model;
    Comment_RecyclerView comment_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView=findViewById(R.id.recyclerview);
        comment_recyclerView=new Comment_RecyclerView(this,comment_modelList);

        recyclerView.setAdapter(comment_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(linearLayoutManager);

        allCommentsDetails();

    }

    private void allCommentsDetails() {
        comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.btn_camera_all,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.bg_2,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.add_person,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.flash,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cameraclip,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.cast_abc_scrubber_control_to_pressed_mtrl_000,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.hat,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model); comment_model=new Comment_Model(R.drawable.d_round_black75,"rabindra","rehirirri","10 jan 2022");
        comment_modelList.add(comment_model);
        comment_recyclerView.notifyDataSetChanged();
    }
}