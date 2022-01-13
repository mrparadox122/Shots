package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MyVideoView_Activity extends AppCompatActivity {


    Button btn_video;
    VideoView v_video;
    MediaController mc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_view);


        btn_video = findViewById(R.id.btn_video);
        v_video = findViewById(R.id.v_video);

        mc = new MediaController(MyVideoView_Activity.this);
        v_video.setMediaController(mc);

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,1);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

            Uri videouri = data.getData();
            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(videouri);
            v_video.start();
        }

    }
}