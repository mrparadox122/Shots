package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GmailNewPin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_new_pin);

        getSupportActionBar().hide();
    }

    public void btnDone(View view) {
        Intent intent = new Intent(GmailNewPin_Activity.this,HomeActivty.class);
        startActivity(intent);
    }
}