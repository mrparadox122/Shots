package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneNewPinActivity extends AppCompatActivity {

    EditText et_newpin,et_c_newpin;
    Button cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_new_pin);

        et_newpin = findViewById(R.id.et_newpin);
        et_c_newpin = findViewById(R.id.et_c_newpin);
        cont = findViewById(R.id.cont);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNewPinActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}