package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class NewEditProfile_Activity extends AppCompatActivity {

    EditText mypic;

    TextView myname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_profile);
    }
}