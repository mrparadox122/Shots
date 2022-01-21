package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ForgotPassword_Activity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
    }

    public void bt_forget(View view) {
        ///// Sent Link Email Verification Link
        Toast.makeText(getApplicationContext(), "Verification code Successfully Send", Toast.LENGTH_SHORT).show();
    }

    public void bt_submit(View view) {
         Intent intent = new Intent( ForgotPassword_Activity.this, PinNumber_Activity.class);
         startActivity(intent);
    }
}