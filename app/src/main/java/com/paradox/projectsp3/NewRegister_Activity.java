package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;
import static com.google.android.gms.vision.L.d;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class NewRegister_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_name,et_Remail ,et_Rphonenumber,et_Rpassword, et_RConfirmpassword;
    Button btn_signup,btn_submit;
    String Dob,gender;


    String[] Gender = {"Select Gender", "Male", "Female", "Others"};
    String[] Options = {"","Email", "Phone"};

    private Button dateButton;
    LinearLayout verification;
    boolean iscode = true;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_register);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);


        Spinner spinner01 = findViewById(R.id.spinner01);
        spinner01.setOnItemSelectedListener(this);

        ArrayAdapter add = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Options);
        add.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner01.setAdapter(add);

        et_name = findViewById(R.id.et_name);
        et_Remail = findViewById(R.id.et_Remail);
        et_Rphonenumber = findViewById(R.id.et_Rphonenumber);
        et_Rpassword = findViewById(R.id.et_Rpassword);
        et_RConfirmpassword = findViewById(R.id.et_RConfirmpassword);
        btn_signup = findViewById(R.id.btn_signup);
        btn_submit = findViewById(R.id.btn_submit);
        verification = findViewById(R.id.verification);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iscode == true){
                    verification.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter Verification Code", Toast.LENGTH_SHORT).show();
                }else {
                    verification.setVisibility(View.GONE);
                }

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname,password,email,username,cpass,PhoneNumber,gndr,dob;
                fullname = String.valueOf(et_name.getText());
                password = String.valueOf(et_Rpassword.getText());
                cpass = String.valueOf(et_RConfirmpassword.getText());
                email = String.valueOf(et_Remail.getText());
                username = String.valueOf(et_name.getText());
                PhoneNumber = String.valueOf(et_Rphonenumber.getText());
                gndr = gender;
                dob = Dob;


                Log.e(TAG, "onClick: " + "name:" + fullname + "pas:" + password + "email" + email + "usrname" + username + "phno" + PhoneNumber + "gndr" + gndr + "dob" + dob);
                if (!fullname.equals("") && !password.equals("") && !email.equals("") && !username.equals("") && !PhoneNumber.equals("") && !gndr.equals("") && !dob.equals("") && password.equals(cpass)) {
                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
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
                            String[] data = new String[7];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            data[4] = PhoneNumber;
                            data[5] = dob;
                            data[6] = gndr;
                            Toast.makeText(NewRegister_Activity.this, dob, Toast.LENGTH_LONG).show();
                            PutData putData = new PutData("http://13.127.217.99/dashboard/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "run: " + result);
                                    Log.e(TAG, "field: " + field);
                                    Log.e(TAG, "data: " + data);
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NewRegister_Activity.this, Login.class);
                                        startActivity(intent);
                                       }
                                }
                            }
                        }
                    });
                }
                else
                {
                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }



    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Dob = makeDateitgkm(year,month,day);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String makeDateitgkm(int year, int month , int day)
    {
        return year + "-" + month + "-" + day;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }


    public void openDatePick(View view)
    {
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       // Toast.makeText(getApplicationContext(), Gender[i], Toast.LENGTH_SHORT).show();
        gender = Gender[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}