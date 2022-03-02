package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class Buydiamonds_Activity extends AppCompatActivity {

    TextView text_diamondcount;
    RecyclerView diamondscount_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydiamonds);



        text_diamondcount = findViewById(R.id.text_diamondcount);
        diamondscount_rv = findViewById(R.id.diamondscount_rv);



    }
}