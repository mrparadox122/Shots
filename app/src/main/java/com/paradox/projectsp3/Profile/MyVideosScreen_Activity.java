package com.paradox.projectsp3.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.paradox.projectsp3.R;

public class MyVideosScreen_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos_screen);
        getSupportActionBar().hide();
    }



    public void addBtn(View view) {
    }
}