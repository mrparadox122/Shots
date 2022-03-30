package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class Login extends AppCompatActivity {

    private static  final String  FILE_Email = "rememberne";
    public boolean checkbool;
    Button btn_login,email_button,mobile_button;
    EditText mobileNumbr,pin,et_email;
    String PhoneNumber,password;
    TextView createnewACC,skip_txt,back_btn;
    CheckBox rememberme;
    LinearLayout ll_mobile;
    Context context;
    Resources resources;

    int lang_selected;
    RelativeLayout show_lan_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        mobileNumbr = findViewById(R.id.et_mobile);
        btn_login = findViewById(R.id.login_bt);
        pin = findViewById(R.id.et_pin);
        createnewACC = findViewById(R.id.createnewACC);
        rememberme = findViewById(R.id.check_remember);
        skip_txt = findViewById(R.id.skip_txt);
        mobile_button = findViewById(R.id.mobile_button);
        email_button = findViewById(R.id.email_button);
        rememberme = (CheckBox) findViewById(R.id.check_remember);
        et_email = findViewById(R.id.et_email);
        ll_mobile = findViewById(R.id.ll_mobile);
        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    Toast.makeText(Login.this, "Checked", Toast.LENGTH_LONG).show();
                }
            }
        });

        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_email.setVisibility(View.VISIBLE);
                ll_mobile.setVisibility(View.GONE);
            }
        });

        mobile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_email.setVisibility(View.GONE);
                ll_mobile.setVisibility(View.VISIBLE);
            }
        });


        skip_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,HomeActivty.class);
                startActivity(intent);
            }
        });

        createnewACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,NewSignUpActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneNumber = String.valueOf(mobileNumbr.getText());
                password = String.valueOf(pin.getText());
                Log.e(TAG, "onClick: "+PhoneNumber+password );

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        String[] data = new String[2];
                        data[0] = PhoneNumber;
                        data[1] = password;

                        PutData putData = new PutData("http://shots.ddns.net/dashboard/paradoxApi/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            Log.e(TAG, "im here1 "+data );
                            if (putData.onComplete()) {
                                Log.e(TAG, "run: ime here 2 "+data+field);
                                String result = putData.getResult();
                                Log.e(TAG, "run: "+result );
                                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "run: " + result);
                                Log.e(TAG, "field: " + field);
                                Log.e(TAG, "data: " + data);
                                if (result.equals("Login Success")) {
                                    Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Profile_Activity.class);
                                    GlobalVariables globalVariables = new GlobalVariables();
                                    Log.e(TAG, "run: before init "+globalVariables.username+PhoneNumber );
                                    globalVariables.setUsername(String.valueOf(PhoneNumber));
                                    Log.e(TAG, "run: after init "+globalVariables.username+PhoneNumber);
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


}