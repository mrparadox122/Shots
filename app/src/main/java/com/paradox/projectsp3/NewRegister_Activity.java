package com.paradox.projectsp3;

import static com.google.android.gms.vision.L.TAG;
import static com.google.android.gms.vision.L.d;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.paradox.projectsp3.Model.UserModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.regex.Pattern;


public class NewRegister_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_name,et_Remail ,et_Rphonenumber,et_Rpassword, et_RConfirmpassword,et_veryfi;
    Button btn_signup,btn_submit,datePickerButton,btn_firebase_submit;
    String Dob,gender;
    TextView date_et,terms,privacy;
    String[] Gender = {"Male", "Female", "Others"};
    String[] Options = {"Phone"};
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    private String verificationId;

    ImageView back_btn;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
    Date dt_1 = objSDF.parse("2009-1-1");

    private Button dateButton;
    LinearLayout verification;
    boolean iscode = true;
    private DatePickerDialog picker;

    int mDay,mMonth,mYear;
    boolean isDob;

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
        Spinner spinner01 = findViewById(R.id.spinner01);
        spinner01.setOnItemSelectedListener(this);
        ArrayAdapter add = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Options);
        add.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner01.setAdapter(add);
        mAuth = FirebaseAuth.getInstance();
        et_name = findViewById(R.id.et_name);
        et_Remail = findViewById(R.id.et_Remail);
        et_Rphonenumber = findViewById(R.id.et_Rphonenumber);
        et_Rpassword = findViewById(R.id.et_Rpassword);
        et_RConfirmpassword = findViewById(R.id.et_RConfirmpassword);
        et_veryfi = findViewById(R.id.et_verify);
        btn_signup = findViewById(R.id.btn_signup);
        btn_submit = findViewById(R.id.btn_submit);
        date_et = findViewById(R.id.date_et);
        verification = findViewById(R.id.verification);
        terms = findViewById(R.id.terms);
        privacy = findViewById(R.id.privacy);
        back_btn = findViewById(R.id.back_btn);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();


//        btn_firebase_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createUsers();
//            }
//        });


        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shots.ddns.net/dashboard/ShotsLegal/Terms&conditions.html"));
                startActivity(browserIntent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shots.ddns.net/dashboard/ShotsLegal/index.html"));
                startActivity(browserIntent);
            }
        });


        date_et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                    DatePickerDialog picker = new DatePickerDialog(NewRegister_Activity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        date_et.setText("Date of Birth: "+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        Dob = (year+"-"+monthOfYear+"-"+dayOfMonth);
                                    }
                                }, year, month, day);
                        picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                        picker.show();
                    }
                });

//               // Set Today date to calendar
//                final Calendar calendar2 = Calendar.getInstance();
//                //Set Minimum date of calendar
//                calendar2.set(2009, 1, 1);
//                picker.getDatePicker().setMaxDate(calendar2.getTimeInMillis());
//                picker.setTitle("Select Date");
//                picker.show();
//
//                final Calendar calendar3 = Calendar.getInstance();
//                //Set Maximum date of calendar
//                calendar3.set(1900, 1, 1);
//                //Set One Month date from today date to calendar
//                //calendar3.add(Calendar.MONTH, 1);
//                picker.getDatePicker().setMinDate(calendar3.getTimeInMillis());
//                picker.setTitle("Select Date");
//                picker.show();

                initDatePicker();

        btn_submit.setOnClickListener(new View.OnClickListener() {
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
                else if (!fullname.equals("") && !password.equals("") && !email.equals("") && !username.equals("") && !PhoneNumber.equals("") && !gndr.equals("") && !dob.equals("") && password.equals(cpass) && isEmailValid(email)){


                    String phone = "+91" + et_Rphonenumber.getText().toString();
                    sendVerificationCode(phone);

                    verification.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter Verification Code", Toast.LENGTH_SHORT).show();
                }
                else {
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

                verifyCode(et_veryfi.getText().toString());

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
//                    Handler handler = new Handler();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            String[] field = new String[7];
//                            field[0] = "fullname";
//                            field[1] = "username";
//                            field[2] = "email";
//                            field[3] = "password";
//                            field[4] = "PhoneNumber";
//                            field[5] = "Dob";
//                            field[6] = "Gender";
//                            String[] data = new String[7];
//                            data[0] = fullname;
//                            data[1] = username;
//                            data[2] = email;
//                            data[3] = password;
//                            data[4] = PhoneNumber;
//                            data[5] = dob;
//                            data[6] = gndr;
////                            Toast.makeText(NewRegister_Activity.this, dob, Toast.LENGTH_LONG).show();
//                            PutData putData = new PutData("http://13.127.217.99/dashboard/paradoxApi/signup.php", "POST", field, data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    String result = putData.getResult();
//                                    Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
//                                    Log.e(TAG, "run: " + result);
//                                    Log.e(TAG, "field: " + field);
//                                    Log.e(TAG, "data: " + data);
//                                    if (result.equals("Sign Up Success")) {
//                                        Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(NewRegister_Activity.this, Login.class);
//                                        startActivity(intent);
//                                    }
//                                    if (result.equals("Sign up Failed")){
//                                        Toast.makeText(NewRegister_Activity.this, "User already exist!", Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(NewRegister_Activity.this, "Plz Login instead!!!", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(NewRegister_Activity.this, Login.class);
//                                        startActivity(intent);
//                                    }
//                                }
//                            }
//                        }
//                    });
                }
               else
                {
//                    et_RConfirmpassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void createUsers() {
//        String username = et_name.getText().toString().trim();
//        String useremail = et_Remail.getText().toString().trim();
//        String userpassword = et_Rpassword.getText().toString().trim();
//
//        if (TextUtils.isEmpty(username)){
//            Toast.makeText(this, "Username is Empty", Toast.LENGTH_SHORT).show();
//        }
//
//        if (TextUtils.isEmpty(useremail)){
//            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
//        }
//
//        if (TextUtils.isEmpty(userpassword)){
//            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
//        }
//
//        if (userpassword.length()< 6){
//            Toast.makeText(this, "Password lenght must be greter then 6 Digits", Toast.LENGTH_SHORT).show();
//        }
//
//        mAuth.createUserWithEmailAndPassword(useremail,userpassword )
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            UserModel userModel = new UserModel(username,useremail,userpassword);
//                            String id = task.getResult().getUser().getUid();
//                            database.getReference().child("User").child(id).setValue(userModel);
//                            startActivity(new Intent(NewRegister_Activity.this,Login.class));
//                            Toast.makeText(NewRegister_Activity.this, "Registration is Success", Toast.LENGTH_SHORT).show();
//                        }else {
//
//                            Toast.makeText(NewRegister_Activity.this, "Registration is Failed"+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                date_et.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        picker = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
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


    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
                                    PutData putData = new PutData("http://shots.ddns.net/dashboard/paradoxApi/signup.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "run: " + result);
                                            Log.e(TAG, "field: " + field);
                                            Log.e(TAG, "data: " + data);
                                            if (result.equals("Sign Up Success")) {
                                                Toast.makeText(NewRegister_Activity.this, result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(NewRegister_Activity.this, HomeActivty.class);
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
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
//                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                            startActivity(i);
//                            Intent intent = new Intent(NewRegister_Activity.this,HomeActivty.class);
//                            startActivity(intent);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(NewRegister_Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                et_veryfi.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
//            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

}