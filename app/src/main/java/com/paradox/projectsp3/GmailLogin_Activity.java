package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GmailLogin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_login);
        getSupportActionBar().hide();
    }

    public void gmailForgot(View view) {
        Intent intent = new Intent(GmailLogin_Activity.this,Gmail_ForgotPin_Activity.class);
        startActivity(intent);
    }
}