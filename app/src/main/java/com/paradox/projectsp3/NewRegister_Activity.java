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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewRegister_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_name,et_Remail ,et_Rphonenumber,et_Rpassword, et_RConfirmpassword;
    Button btn_signup,btn_submit,datePicker_btn;
    String Dob,gender;

    int mDay,mMonth,mYear;
    boolean isDob;


    String[] Gender = {"Male", "Female", "Others"};
    String[] Options = {"Phone"};

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
    Date dt_1 = objSDF.parse("2009-1-1");

    private Button dateButton;
    LinearLayout verification;
    boolean iscode = true;
    private DatePickerDialog datePickerDialog;

    public NewRegister_Activity() throws ParseException {
    }


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


        datePicker_btn = findViewById(R.id.datePicker_btn);
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
                Integer Pc;
                fullname = String.valueOf(et_name.getText());
                password = String.valueOf(et_Rpassword.getText());
                cpass = String.valueOf(et_RConfirmpassword.getText());
                email = String.valueOf(et_Remail.getText());
                username = String.valueOf(et_name.getText());
                PhoneNumber = String.valueOf(et_Rphonenumber.getText());
                Pc = PhoneNumber.length();
                gndr = gender;
                dob = Dob;


                Log.e(TAG, "onClick: " + "name:" + fullname + "pas:" + password + "email" + email + "usrname" + username + "phno" + PhoneNumber + "gndr" + gndr + "dob" + dob);
                if (!password.equals(cpass)){
                    if (isEmailValid(email)){
                        et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                    }
                    else if (!isEmailValid(email)){
                        et_Remail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                        et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                    }

                }
//                else if (password.equals(cpass)){
//                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
//                }
                else if (!isEmailValid(email)){
                    if (password.equals(cpass)){
                        et_Remail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(NewRegister_Activity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.equals(cpass)){
                        et_Remail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                        et_Rpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                    }



                }
                else if (!(Pc > 9)){
                    et_Rphonenumber.getBackground().mutate().setColorFilter(getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
                }
                else if (!fullname.equals("") && !password.equals("") && !email.equals("") && !username.equals("") && !PhoneNumber.equals("") && !gndr.equals("") && !dob.equals("") && password.equals(cpass) && isEmailValid(email)) {
                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
                    et_Remail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorwhite_50), PorterDuff.Mode.SRC_ATOP);
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
//                            Toast.makeText(NewRegister_Activity.this, dob, Toast.LENGTH_LONG).show();
                            PutData putData = new PutData("http://13.127.217.99/dashboard/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "run: " + result);
                                    Log.e(TAG, "field: " + field);
                                    Log.e(TAG, "data: " + data);
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NewRegister_Activity.this, Login.class);
                                        startActivity(intent);
                                    }
                                    if (result.equals("Sign up Failed")){
                                        Toast.makeText(NewRegister_Activity.this, "User already exist!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(NewRegister_Activity.this, "Plz Login instead!!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NewRegister_Activity.this, Login.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    });

                }



//
//
//

               else
                {
//                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        datePicker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                //set time zone
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                int selectedYear = calendar.get(Calendar.YEAR);
                int selectedMonth = calendar.get(Calendar.MONTH);
                int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRegister_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                mDay = selectedDay;
                                mMonth = selectedMonth;
                                mYear = selectedYear;
                                StringBuilder Date = new StringBuilder("");
                                String conver = Integer.toString(selectedYear);
                                Date.append(conver);
                                Date.append("-");
                                selectedMonth++;
                                conver = Integer.toString(selectedMonth);
                                Date.append(conver);
                                Date.append("-");
                                conver = Integer.toString(selectedDay);
                                Date.append(conver);
                                isDob = true;
                            }
                        }, mDay, mMonth, mYear);

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());


                //Set Today date to calendar
                final Calendar calendar2 = Calendar.getInstance();
                //Set Minimum date of calendar
                calendar2.set(2009, 1, 1);
                datePickerDialog.getDatePicker().setMaxDate(calendar2.getTimeInMillis());
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();

                final Calendar calendar3 = Calendar.getInstance();
                //Set Maximum date of calendar
                calendar3.set(1900, 1, 1);
                //Set One Month date from today date to calendar
                //calendar3.add(Calendar.MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar3.getTimeInMillis());
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       // Toast.makeText(getApplicationContext(), Gender[i], Toast.LENGTH_SHORT).show();
        gender = Gender[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}