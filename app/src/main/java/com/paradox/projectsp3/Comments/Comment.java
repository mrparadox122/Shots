package com.paradox.projectsp3.Comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.paradox.projectsp3.Model.Comment_Model;
import com.paradox.projectsp3.R;

import java.util.ArrayList;
import java.util.List;

public class Comment extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText message_edit;
    Comment_Adapter comment_adapter;
    FrameLayout comment_screen;
    Comment_Model comment_model;
    ArrayList<Comment_Model> data_list;
    ProgressBar send_progress;
    ImageButton send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        recyclerView = findViewById(R.id.recyclerview);
        send_btn = findViewById(R.id.send_btn);
        comment_adapter = new Comment_Adapter(this, data_list);
        recyclerView.setAdapter(comment_adapter);

        comment_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setAdapter(comment_adapter);
        message_edit=findViewById(R.id.message_edit);
        send_progress=findViewById(R.id.send_progress);
        send_btn=findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=message_edit.getText().toString();
                if(!TextUtils.isEmpty(message)){

                    Toast.makeText(getApplicationContext(), "Please Login into the app", Toast.LENGTH_SHORT).show();

//                    if(Variables.sharedPreferences.getBoolean(Variables.islogin,false)){
//                        send_Comments(video_id,message);
//                        message_edit.setText(null);
//                        send_progress.setVisibility(View.VISIBLE);
//                        send_btn.setVisibility(View.GONE);
//                    }
//                    else {
//                        //Toast.makeText(context, "Please Login into the app", Toast.LENGTH_SHORT).show();
//                    }
                }

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


}
