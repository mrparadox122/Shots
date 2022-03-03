package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseYoursIntrests_Activity extends AppCompatActivity {

    Button btnSubmit;
    TextView dance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_yours_intrests);
        getSupportActionBar().hide();


        btnSubmit = findViewById(R.id.btnSubmit);
        dance = findViewById(R.id.dance);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseYoursIntrests_Activity.this,Login.class);
                startActivity(intent);
            }
        });

        dance.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

               dance.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click Dance", Toast.LENGTH_SHORT).show();
            }
        });

    }
}