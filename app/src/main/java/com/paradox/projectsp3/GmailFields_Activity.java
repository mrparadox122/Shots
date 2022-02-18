package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.time.Year;
import java.util.Calendar;
import java.util.TimeZone;

public class GmailFields_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Button datePickerButton,btn_submit;
    EditText et_mobilenumber,pin,cpass;
    String[] Gender = { "Male", "Female", "Others"};
    ///////////////////////////////////////////////////////////////////////////////////////////////
    String Dob,gender;

    int mDay,mMonth,mYear;
    boolean isDob;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gmail_fields);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);


        pin = findViewById(R.id.et_Rpassword);
        cpass = findViewById(R.id.et_RConfirmpassword);
        datePickerButton = findViewById(R.id.datePickerButton);
        et_mobilenumber = findViewById(R.id.et_mobilenumber);
        btn_submit = findViewById(R.id.btn_submit);
        NewSignUpActivity newSignUpActivity = new NewSignUpActivity();

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(GmailFields_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePickerButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Dob = (year+"-"+monthOfYear+"-"+dayOfMonth);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                String fullname,password,email,username,PhoneNumber,gndr,dob,cp;
                Integer Pc;
                fullname = String.valueOf(acct.getDisplayName());
                password = String.valueOf(pin.getText());
                cp = String.valueOf(cpass.getText());
                email = String.valueOf(acct.getEmail());
                username = String.valueOf(acct.getId());
                PhoneNumber = String.valueOf(et_mobilenumber.getText());
                Pc = PhoneNumber.length();
                gndr = gender;
                dob = Dob;
                Log.e(TAG, "onClick: "+fullname+password+email+username+PhoneNumber+gndr+dob+password+cp);

//                Toast.makeText(getApplicationContext(), String.valueOf(PhoneNumber+username+email+fullname+gndr+dob), Toast.LENGTH_SHORT).show();
                if (!password.equals(cp)){
                    pin.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                    cpass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                }
                else if (PhoneNumber.equals("")&&!(Pc > 9)){
                    et_mobilenumber.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color),PorterDuff.Mode.SRC_ATOP);
                }
                else if (!fullname.equals("")&&!password.equals("")&&!email.equals("")&&!username.equals("")&&!PhoneNumber.equals("")&&!gndr.equals("")&&!dob.equals("")&& password.equals(cp)){
                    pin.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
                    cpass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
                    et_mobilenumber.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50),PorterDuff.Mode.SRC_ATOP);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[7];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "password";
                            field[4] = "PhoneNumber";
                            field[5] = "Dob";
                            field[6] = "Gender";
                            String[] data = new  String[7];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            data[4] = PhoneNumber;
                            data[5] = dob;
                            data[6] = gndr;
//                            Toast.makeText(GmailFields_Activity.this, String.valueOf(data), Toast.LENGTH_SHORT).show();
                            PutData putData = new PutData("http://13.127.217.99/dashboard/paradoxApi/signup.php","POST",field,data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String result = putData.getResult();
                                    Toast.makeText(GmailFields_Activity.this, result, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "run: "+result );
                                    Log.e(TAG, "field: "+field );
                                    Log.e(TAG, "data: "+data );
                                    if (result.equals("Sign Up Success")){
//                                        Toast.makeText(GmailFields_Activity.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(GmailFields_Activity.this, Login.class);
                                        startActivity(intent);
                                    }

                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getApplicationContext(), Gender[i], Toast.LENGTH_SHORT).show();
        gender = Gender[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}