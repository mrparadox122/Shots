package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Login extends AppCompatActivity {
    public boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


    }

    public void foegot_pass(View view) {

        Intent intent = new Intent(Login.this, ForgotPassword_Activity.class);
        startActivity(intent);
    }

    public void login_btn(View view) {

        Intent intent = new Intent(Login.this, PinNumber_Activity.class);
        startActivity(intent);
    }
}