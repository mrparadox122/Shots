package com.paradox.projectsp3.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class P_Commnets extends AppCompatActivity {


    View view;
    Context context;
    RecyclerView recyclerview;
    Comments_Adapter adapter;
    List<Comments_Model> comments_modelList;
//    FrameLayout comment_screen;
    EditText message_edit;
    ImageView send_btn,Goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcommnets);
        getSupportActionBar().hide();

        recyclerview = findViewById(R.id.recyclerview);
       // comment_screen=findViewById(R.id.comment_screen);
        send_btn = findViewById(R.id.send_btn);
        Goback = findViewById(R.id.Goback);

        recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        comments_modelList = new ArrayList<>();
        adapter = new Comments_Adapter(this,comments_modelList);
        recyclerview.setAdapter(adapter);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//
//

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
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
//
            }
        });

    }
}