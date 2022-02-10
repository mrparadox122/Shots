package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    public boolean bool;
    Button btn_login;
    EditText mobileNumbr,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mobileNumbr = findViewById(R.id.et_mobile);

        btn_login = findViewById(R.id.login_bt);


        pin = findViewById(R.id.et_gmail);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneNumber,password;
                PhoneNumber = String.valueOf(mobileNumbr.getText());
                password = String.valueOf(pin.getText());

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[3];
                        field[1] = "username";
                        field[2] = "password";
                        String[] data = new String[3];
                        data[1] = PhoneNumber;
                        data[2] = password;

//                            Toast.makeText(NewRegister_Activity.this, dob, Toast.LENGTH_LONG).show();
                        PutData putData = new PutData("http://13.127.217.99/dashboard/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "run: " + result);
                                Log.e(TAG, "field: " + field);
                                Log.e(TAG, "data: " + data);
                                if (result.equals("Login Success")) {
                                    Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, HomeActivty.class);
                                    startActivity(intent);
                                }
                                if (result.equals("Username or Password wrong")){
                                    Toast.makeText(Login.this, "enter correct username and password", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }
                });


            }
        });


    }

    public void foegot_pass(View view) {

        Intent intent = new Intent(Login.this, ForgotPassword_Activity.class);
        startActivity(intent);
    }



    public void login_btn(View view) {

//        Intent intent = new Intent(Login.this, PinNumber_Activity.class);
//        startActivity(intent);
    }
}