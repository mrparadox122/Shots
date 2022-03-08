package com.paradox.projectsp3.HomeComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.paradox.projectsp3.Profile.Comments_Adapter;
import com.paradox.projectsp3.Profile.Comments_Model;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class HomeComment_Activity extends AppCompatActivity {

    View view;
    Context context;
    RecyclerView hm_recyclerview;
    homeCommentAdapter homeadapter;
    List<homeCommentModel> homeCommentModelList;
    //    FrameLayout comment_screen;
    EditText message_edit;
    ImageView send_btn,Goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_comment);

        getSupportActionBar().hide();

        hm_recyclerview = findViewById(R.id.recyclerview);
        // comment_screen=findViewById(R.id.comment_screen);
        send_btn = findViewById(R.id.send_btn);
        Goback = findViewById(R.id.Goback);

        hm_recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        homeCommentModelList = new ArrayList<>();
        homeadapter = new homeCommentAdapter(this,homeCommentModelList);
        hm_recyclerview.setAdapter(homeadapter);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
//
////                String message=message_edit.getText().toString();
////                if(!TextUtils.isEmpty(message)){
////                    // if(Variables.sharedPreferences.getBoolean(Variables.islogin,false)){
////                    // send_Comments(video_id,message);
////                    message_edit.setText(null);
////                    // send_progress.setVisibility(View.VISIBLE);
////                    send_btn.setVisibility(View.GONE);
////                }
////                else {
////                    Toast.makeText(context, "Please Login into the app", Toast.LENGTH_SHORT).show();
////                }

            }
        });

    }
}