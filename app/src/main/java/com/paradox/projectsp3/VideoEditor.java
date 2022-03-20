package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import java.io.File;
import java.lang.reflect.Method;

public class VideoEditor extends AppCompatActivity {

    VideoView videoView;
    private File storage;
    private String[] allpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_video_editor);
        videoView = findViewById(R.id.video);

        allpath = StorageUtil.getStorageDirectories(this);

        for (String path: allpath){
            storage = new File(path);
            Methodl.load_Directory_Files(storage);
        }
    }
}