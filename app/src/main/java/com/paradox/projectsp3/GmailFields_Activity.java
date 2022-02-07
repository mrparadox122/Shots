package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class GmailFields_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialog;
    private Button dateButton,btn_submit;
    EditText et_mobilenumber;
    String[] Gender = { "Male", "Female", "Others"};
    ///////////////////////////////////////////////////////////////////////////////////////////////
    String Dob,gender;
   

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

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());



        et_mobilenumber = findViewById(R.id.et_mobilenumber);






        btn_submit = findViewById(R.id.btn_submit);
        NewSignUpActivity newSignUpActivity = new NewSignUpActivity();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname,password,email,username,PhoneNumber,gndr,dob;
                fullname = newSignUpActivity.perName;
                password = newSignUpActivity.perPass;
                email = newSignUpActivity.perEmail;
                username = newSignUpActivity.perusrn;
                PhoneNumber = String.valueOf(et_mobilenumber.getText());
                gndr = "gender";
                dob = Dob;
                if (!fullname.equals("")&&!password.equals("")&&!email.equals("")&&!username.equals("")&&!PhoneNumber.equals("")&&!gndr.equals("")&&!dob.equals("")){
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
                            data[5] = gndr;
                            data[6] = dob;
                            PutData putData = new PutData("http://13.127.217.99/dashboard/signup.php","POST",field,data);
                            Toast.makeText(GmailFields_Activity.this, String.valueOf(data), Toast.LENGTH_SHORT).show();
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String result = putData.getResult();
                                    Toast.makeText(GmailFields_Activity.this, result, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "run: "+result );
                                    Log.e(TAG, "field: "+field );
                                    Log.e(TAG, "data: "+data );
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(GmailFields_Activity.this, result, Toast.LENGTH_SHORT).show();
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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Dob = date;
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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}