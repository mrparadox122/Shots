package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class PostVideo_Activity extends AppCompatActivity {

    EditText description_edit;
    ImageView video_thumbnail;
    TextView privcy_type_txt;

    Switch comment_switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        description_edit = findViewById(R.id.description_edit);
        video_thumbnail = findViewById(R.id.video_thumbnail);
        description_edit = findViewById(R.id.description_edit);
        privcy_type_txt = findViewById(R.id.privcy_type_txt);
        comment_switch = findViewById(R.id.comment_switch);

        comment_switch.setChecked(true);

        privcy_type_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}