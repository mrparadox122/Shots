package com.paradox.projectsp3;

import static com.paradox.projectsp3.GlobalVariables.someList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseYoursIntrests_Activity extends AppCompatActivity {

    Button btnSubmit;
    TextView dance;
    List<String> itemsToAdd = new ArrayList<String>();

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
                someList.addAll(itemsToAdd);
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
                itemsToAdd.add(dance.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click Dance", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

    }
}