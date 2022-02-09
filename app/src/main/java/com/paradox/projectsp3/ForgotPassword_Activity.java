package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ForgotPassword_Activity extends AppCompatActivity {

    EditText et_mobile;
    Button send_btn,btn_verify;
    LinearLayout verification;

    boolean send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        send_btn = findViewById(R.id.send_btn);
        et_mobile = findViewById(R.id.et_mobile);
        btn_verify = findViewById(R.id.btn_verify);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send == true){

                    verification.setVisibility(View.VISIBLE);

                }else {

                    verification.setVisibility(View.GONE);
                }
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword_Activity.this,PhoneNewPinActivity.class);
                startActivity(intent);
            }
        });

    }

}