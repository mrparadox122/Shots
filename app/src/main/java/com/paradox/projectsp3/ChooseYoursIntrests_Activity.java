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
    TextView dance,fashion,comedy,food,motivational,news,medical,gaming,traditional,fitness,duet,health,popculture,production,jobs,entertainment,cooking,science,celebration,songs,sports;
    List<String> itemsToAdd = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_yours_intrests);
        getSupportActionBar().hide();

        btnSubmit = findViewById(R.id.btnSubmit);
        dance = findViewById(R.id.dance);
        fashion = findViewById(R.id.fashion);
        comedy = findViewById(R.id.comedy);
        food = findViewById(R.id.food);
        motivational = findViewById(R.id.motivational);
        news = findViewById(R.id.news);
        medical = findViewById(R.id.medical);
        gaming = findViewById(R.id.gaming);
        traditional = findViewById(R.id.traditional);
        fitness = findViewById(R.id.fitness);
        duet = findViewById(R.id.duet);
        health = findViewById(R.id.health);
        popculture = findViewById(R.id.popculture);
        production = findViewById(R.id.production);
        jobs = findViewById(R.id.jobs);
        entertainment = findViewById(R.id.entertainment);
        cooking = findViewById(R.id.cooking);
        science = findViewById(R.id.science);
        celebration = findViewById(R.id.celebration);
        songs = findViewById(R.id.songs);
        sports = findViewById(R.id.sports);
        Boolean checkselect = true;

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someList.addAll(itemsToAdd);
                if (itemsToAdd.isEmpty()){
                    Toast.makeText(ChooseYoursIntrests_Activity.this, "choose atleast one interest category", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(ChooseYoursIntrests_Activity.this,Login.class);
                    startActivity(intent);
                }

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

        fashion.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                fashion.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(fashion.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click fashion", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        comedy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                comedy.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(comedy.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click comedy", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                food.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(food.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click food", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        motivational.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                motivational.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(motivational.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click motivational", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                news.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(news.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click news", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        medical.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                medical.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(medical.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click medical", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        gaming.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                gaming.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(gaming.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click gaming", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        traditional.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                traditional.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(traditional.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click traditional", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        fitness.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                fitness.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(fitness.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click fitness", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        duet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                duet.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(duet.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click duet", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                health.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(health.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click health", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        popculture.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                popculture.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(popculture.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click popculture", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        production.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                production.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(production.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click production", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                jobs.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(jobs.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click jobs", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                entertainment.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(entertainment.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click entertainment", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        cooking.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                cooking.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(cooking.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click cooking", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        science.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                science.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(science.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click science", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        celebration.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                celebration.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(celebration.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click celebration", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        songs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                songs.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(songs.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click songs", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                sports.setTextColor(getResources().getColor(R.color.red));
                //dance.setTextColor(R.color.red);
                //dance.setBackground(R.color.app_color);
                itemsToAdd.add(sports.getText().toString());
                //itemsToAdd.add("two");

               // or use handy method which creates temporary list internally:
                //someList.addAll(Arrays.asList("three", "four"));
                Toast.makeText(ChooseYoursIntrests_Activity.this, "Click sports", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChooseYoursIntrests_Activity.this,itemsToAdd.toString() , Toast.LENGTH_SHORT).show();
            }
        });


    }
}