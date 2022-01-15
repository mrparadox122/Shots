package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class MyVideoView_Activity extends AppCompatActivity {

    Button btn_video,btn_image;
    VideoView v_video;
    MediaController mc;
    ImageView imageview;
    int SELECT_IMAGE_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_video_view);

        btn_video = findViewById(R.id.btn_video);
        btn_image = findViewById(R.id.btn_image);
        v_video = findViewById(R.id.v_video);
        imageview = findViewById(R.id.imageview);

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

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"title"),SELECT_IMAGE_CODE);

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
        if (requestCode == 2){
            Uri imageuri = data.getData();
            imageview.setImageURI(imageuri);
            Toast.makeText(this, String.valueOf(imageuri), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MyVideoView_Activity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }

}