package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class HomeActivty extends AppCompatActivity {

    LottieAnimationView lottielike;
    private boolean fav=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.home_activty);
        lottielike = findViewById(R.id.animationView);


        lottielike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav){
                    lottielike.setMinAndMaxProgress(0.5f,1.0f);
                    lottielike.playAnimation();
                    fav=false;
                }else {
                    lottielike.setMinAndMaxProgress(0.0f,1.0f);
                    lottielike.playAnimation();
                    fav=true;

                }
            }
        });


    }
}