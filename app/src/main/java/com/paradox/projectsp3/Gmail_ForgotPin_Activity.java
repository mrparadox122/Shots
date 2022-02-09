package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gmail_ForgotPin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_forgot_pin);


        getSupportActionBar().hide();
    }

    public void gmailVerify(View view) {

        Intent intent = new Intent(Gmail_ForgotPin_Activity.this,GmailNewPin_Activity.class);
        startActivity(intent);
    }
}