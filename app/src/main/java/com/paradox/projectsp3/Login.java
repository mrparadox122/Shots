package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private static  final String  FILE_Email = "rememberne";
    public boolean bool;
    Button btn_login;
    EditText mobileNumbr,pin;
    String PhoneNumber,password;
    TextView createnewACC,skip_txt,firebase_login_btn;
    CheckBox rememberme;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_Email, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String saveemail = sharedPreferences.getString("svemail","");
        String savepassword = sharedPreferences.getString("svpasswprd","");

        if (sharedPreferences.contains("checked")&& sharedPreferences.getBoolean("checked",false) == true){
            rememberme.setChecked(true);
        }else {

            rememberme.setChecked(false);
        }


        mobileNumbr.setText(saveemail);
        pin.setText(savepassword);

        mobileNumbr = findViewById(R.id.et_mobile);
        btn_login = findViewById(R.id.login_bt);
        pin = findViewById(R.id.et_pin);
        createnewACC = findViewById(R.id.createnewACC);
        rememberme = findViewById(R.id.remember_check);
        skip_txt = findViewById(R.id.skip_txt);
        firebase_login_btn = findViewById(R.id.firebase_login_btn);

        firebase_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = mobileNumbr.getText().toString().trim();
                String userpassword = pin.getText().toString().trim();

                if (rememberme.isChecked()){
                    editor.putBoolean("checked",true);
                    editor.apply();
                    StoredatausingSharepref(saveemail,savepassword);
                }else {

                    

                }

                if (TextUtils.isEmpty(useremail)){
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(userpassword)){
                    Toast.makeText(Login.this, "Plase Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (userpassword.length()< 6){
                    Toast.makeText(Login.this, "Password Must be 6 digits", Toast.LENGTH_SHORT).show();
                }

                mauth.signInWithEmailAndPassword(useremail,userpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                                }else {

                                    Toast.makeText(Login.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mauth = FirebaseAuth.getInstance();

        rememberme = (CheckBox) findViewById(R.id.remember_check);

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

//                            Toast.makeText(NewRegister_Activity.this, dob, Toast.LENGTH_LONG).show();
                        PutData putData = new PutData("http://13.127.217.99/dashboard/paradoxApi/login.php", "POST", field, data);
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

    private void StoredatausingSharepref(String saveemail, String savepassword) {


    }

    public void Logintofirebase() {




    }

    public void foegot_pass(View view) {

        Intent intent = new Intent(Login.this, ForgotPassword_Activity.class);
        startActivity(intent);
    }

    public void login_btn(View view) {

//        Intent intent = new Intent(Login.this, PinNumber_Activity.class);
//        startActivity(intent);
    }

    public void firebaselogin(View view) {


    }
}