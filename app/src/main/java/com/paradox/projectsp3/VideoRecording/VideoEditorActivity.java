package com.paradox.projectsp3.VideoRecording;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.paradox.projectsp3.R;

public class VideoEditorActivity extends AppCompatActivity {


    VideoView video;
//    ImageView volumeID,playid,doneid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_editor2);



        video = findViewById(R.id.video);
//        volumeID = findViewById(R.id.volumeID);
//        playid = findViewById(R.id.playid);
//        doneid = findViewById(R.id.doneid);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VideoEditorActivity.this, "Video Click", Toast.LENGTH_SHORT).show();
            }
        });

//        volumeID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(VideoEditorActivity.this, "Volume Click", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//        playid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(VideoEditorActivity.this, "Play Click", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//        doneid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(VideoEditorActivity.this, "Sound done Click", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });



    }
}