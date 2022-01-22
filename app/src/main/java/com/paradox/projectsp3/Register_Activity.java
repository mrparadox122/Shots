package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.google.android.gms.vision.L.TAG;

public class Register_Activity extends AppCompatActivity {

    EditText et_name,et_Remail ,et_Rphonenumber,et_Rpassword, et_RConfirmpassword;
    Button btn_signup;
    TextView alreadylogin_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        et_name = findViewById(R.id.et_name);
        et_Remail = findViewById(R.id.et_Remail);
        et_Rphonenumber = findViewById(R.id.et_Rphonenumber);
        et_Rpassword = findViewById(R.id.et_Rpassword);
        et_RConfirmpassword = findViewById(R.id.et_RConfirmpassword);
        alreadylogin_txt = findViewById(R.id.alreadylogin_txt);
        btn_signup = findViewById(R.id.btn_signup);
        //goBack_btn = findViewById(R.id.Goback);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname,password,email,username;
                fullname = String.valueOf(et_name.getText());
                password = String.valueOf(et_Rpassword.getText());
                email = String.valueOf(et_name.getText());
                username = String.valueOf(et_Rphonenumber);
                if (!fullname.equals("")&&!password.equals("")&&!email.equals("")&&!username.equals("")){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "password";
                            String[] data = new  String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            PutData putData = new PutData("http://13.127.217.99/dashboard/signup.php","POST",field,data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String result = putData.getResult();
                                    Toast.makeText(Register_Activity.this, result, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "run: "+result );
                                    Log.e(TAG, "field: "+field );
                                    Log.e(TAG, "data: "+data );
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(Register_Activity.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register_Activity.this , Login.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(Register_Activity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}