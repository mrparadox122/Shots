package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {


    TextView facebook_btn,google_btn,mobile_btn;
    ImageView goBAck_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        initview();
    }

    private void initview() {

        goBAck_btn = findViewById(R.id.Goback);

        facebook_btn = findViewById(R.id.facebook_btn);
        google_btn = findViewById(R.id.google_btn);
        mobile_btn = findViewById(R.id.mobile_btn);



        goBAck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,HomeActivty.class);
                startActivity(intent);
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUp.this,HomeActivty.class);
                startActivity(intent);
            }
        });
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUp.this,HomeActivty.class);
                startActivity(intent);
            }
        });
        mobile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Register_Activity.class);
                startActivity(intent);
            }
        });

    }
}