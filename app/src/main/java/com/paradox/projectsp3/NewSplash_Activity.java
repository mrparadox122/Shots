package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class NewSplash_Activity extends AppCompatActivity {

    ImageView s_logo,shots_img;

    private static int splashout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);
        getSupportActionBar().hide();


        s_logo = findViewById(R.id.s_logo);
        shots_img = findViewById(R.id.shots_img);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(NewSplash_Activity.this,ChooseYoursIntrests_Activity.class);
                startActivity(intent);
                finish();
            }

        },splashout);

        Animation myanimation = AnimationUtils.loadAnimation(NewSplash_Activity.this,R.anim.animation1);
        s_logo.setAnimation(myanimation);

        Animation myyanimation = AnimationUtils.loadAnimation(NewSplash_Activity.this,R.anim.animation2);
        shots_img.setAnimation(myyanimation);

    }
}