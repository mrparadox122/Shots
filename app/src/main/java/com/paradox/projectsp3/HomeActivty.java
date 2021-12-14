package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class HomeActivty extends AppCompatActivity {

    LottieAnimationView lottielike,lottiecomment,lottieshare;
    private boolean fav=false,comment=false,share=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.home_activty);
        lottielike = findViewById(R.id.animationView);
        lottiecomment = findViewById(R.id.comment);
        lottieshare = findViewById(R.id.share);


        lottielike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav){
                    lottielike.setSpeed(-1);
                    lottielike.playAnimation();
                    fav=false;
                }else {
                    lottielike.setSpeed(1);
                    lottielike.playAnimation();
                    fav=true;

                }
            }
        });
        lottiecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment){
                    lottiecomment.setSpeed(-1);
                    lottiecomment.playAnimation();
                    fav=false;
                }else {
                    lottiecomment.setSpeed(1);
                    lottiecomment.playAnimation();
                    comment=true;
                }
            }
        });

        lottieshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (share){
                    lottieshare.setSpeed(-1);
                    lottieshare.playAnimation();
                    share=false;
                }else {
                    lottieshare.setSpeed(1);
                    lottieshare.playAnimation();
                    share=true;
                }
            }
        });


    }
}