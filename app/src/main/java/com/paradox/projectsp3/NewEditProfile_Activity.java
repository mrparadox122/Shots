package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.L;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NewEditProfile_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] Gender = {"Male", "Female", "Others"};
    String[] Options = {"Phone"};

    int mDay,mMonth,mYear;
    boolean isDob;
    TextView pic_change;
    ImageView profilepic;

    private FirebaseAuth mAuth;

    private String verificationId;
    ImageView back_btn;

    LinearLayout ll_verify,ll_phoneverify;

    TextView nameedit_btn,emailedit_btn,phoneedit_btn,datePickerButton,bioedit_btn,editname_txt,
            editemail_txt,phonenumber_txt,dobtext_txt,gendertext_txt,bio_txt,user_id;
    EditText editname_et,editphone_et,editgender_et,editeamil_et,editdob_et,editbio_et,verificationphone_et,verifycode_et;
    Button btn_savephone,btn_savename,btn_savegender,btn_saveemail,btn_savedob,btn_savebio,btn_verification,btn_verifyemail;
    Spinner genderedit_btnS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_edit_profile);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        back_btn= findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        pic_change = findViewById(R.id.pic_change);
        editname_txt = findViewById(R.id.editname_txt);
        profilepic = findViewById(R.id.profilepic);
        nameedit_btn = findViewById(R.id.nameedit_btn);
        emailedit_btn = findViewById(R.id.emailedit_btn);
        phoneedit_btn = findViewById(R.id.phoneedit_btn);
        datePickerButton = findViewById(R.id.datePickerButton);
        genderedit_btnS = findViewById(R.id.spinner);
        bioedit_btn = findViewById(R.id.bioedit_btn);

        editemail_txt = findViewById(R.id.editemail_txt);
        user_id = findViewById(R.id.user_id);
        phonenumber_txt = findViewById(R.id.phonenumber_txt);
        dobtext_txt = findViewById(R.id.dobtext_txt);
        gendertext_txt = findViewById(R.id.gendertext_txt);
        bio_txt = findViewById(R.id.bio_txt);
        mAuth = FirebaseAuth.getInstance();



        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(NewEditProfile_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePickerButton.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });
//        editname_et = findViewById(R.id.editname_et);


        editname_txt.setText(String.valueOf(GlobalVariables.getFullname()));
        user_id.setText("User ID: "+String.valueOf(GlobalVariables.getId()));
        editemail_txt.setText(String.valueOf(GlobalVariables.getEmail()));
        phonenumber_txt.setText(String.valueOf(GlobalVariables.getPhonenumber()));
        gendertext_txt.setText(String.valueOf(GlobalVariables.getGender()));
        dobtext_txt.setText(String.valueOf(GlobalVariables.getDob()));
        bio_txt.setText(String.valueOf(GlobalVariables.getBio()));
        //xt_name = findViewById(R.id.txt_name);

        nameedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameDialog();
            }
        });
        emailedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmailDialog();
            }
        });
        phoneedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhoneDialog();
            }
        });

//        genderedit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showGenderDialog();
//            }
//        });
        bioedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBioDialog();
            }
        });

        pic_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(NewEditProfile_Activity.this)
                        .crop()
                        .maxResultSize(1080,1080)
                        .start(10);
            }
        });

    }


    private void showNameDialog() {
        final Dialog namedialog = new Dialog(NewEditProfile_Activity.this);
        namedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        namedialog.setContentView(R.layout.layout_editname);
        namedialog.setCancelable(true);

// set texts here


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(namedialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        editname_et = namedialog.findViewById(R.id.editname_et);
        btn_savename = namedialog.findViewById(R.id.btn_savename);

        btn_savename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!String.valueOf(editname_et.getText()).equals("") &&String.valueOf(editname_et.getText())!= null) {
                    btn_savename.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JSONObject dislike = new JSONObject();
                            try {
                                dislike.put("id", String.valueOf(GlobalVariables.getId()));
                                dislike.put("flag", "1");
                                dislike.put("value", "'"+String.valueOf(editname_et.getText())+"'");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Retrofit.Builder retrofit = new Retrofit.Builder()
                                    .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit2 = retrofit.build();


                            //get client
                            ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                            Call<ResponseBody> call_like = apiInterface.getStringuser_update(dislike.toString());
                            call_like.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                                    if (response.isSuccessful()){
                                        Log.e(TAG, "onResponse: "+response.body() );
                                        Log.e(TAG, "onResponse: "+response );
                                        GlobalVariables.setFullname(String.valueOf(editname_et.getText()));
                                        editname_txt.setText(String.valueOf(editname_et.getText()));
                                        Toast.makeText(NewEditProfile_Activity.this, "Successfully changed the name to: "+GlobalVariables.getFullname(), Toast.LENGTH_SHORT).show();
                                    }
                                }



                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        namedialog.show();
        namedialog.getWindow().setAttributes(lp);
    }

    private void showBioDialog() {
        final Dialog biodialog = new Dialog(NewEditProfile_Activity.this);
        biodialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        biodialog.setContentView(R.layout.layout_editbio);
        biodialog.setCancelable(true);

// set texts here


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(biodialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        editbio_et = biodialog.findViewById(R.id.editbio_et);
        btn_savebio = biodialog.findViewById(R.id.btn_savebio);

        btn_savebio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(editbio_et.getText()).equals("") &&String.valueOf(editbio_et.getText())!= null) {
                    btn_savebio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JSONObject dislike = new JSONObject();
                            try {
                                dislike.put("id", String.valueOf(GlobalVariables.getId()));
                                dislike.put("flag", "6");
                                dislike.put("value", "'"+String.valueOf(editbio_et.getText())+"'");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Retrofit.Builder retrofit = new Retrofit.Builder()
                                    .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit2 = retrofit.build();


                            //get client
                            ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                            Call<ResponseBody> call_like = apiInterface.getStringuser_update(dislike.toString());
                            call_like.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                                    if (response.isSuccessful()){
                                        Log.e(TAG, "onResponse: "+response.body() );
                                        Log.e(TAG, "onResponse: "+response );
                                        GlobalVariables.setBio(String.valueOf(editbio_et.getText()));
                                        bio_txt.setText(String.valueOf(editbio_et.getText()));
                                        biodialog.dismiss();
                                        Toast.makeText(NewEditProfile_Activity.this, "Successfully changed the bio to: "+GlobalVariables.getBio(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "bio cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        biodialog.show();
        biodialog.getWindow().setAttributes(lp);

    }

//    private void showGenderDialog() {
//        final Dialog genderdialog = new Dialog(NewEditProfile_Activity.this);
//        genderdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        genderdialog.setContentView(R.layout.layout_editgender);
//        genderdialog.setCancelable(true);
//
//// set texts here
//
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(genderdialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        editgender_et = genderdialog.findViewById(R.id.editgender_et);
//        btn_savegender = genderdialog.findViewById(R.id.btn_savegender);
//
//        btn_savegender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        genderdialog.show();
//        genderdialog.getWindow().setAttributes(lp);
//    }

    private void showDoBDialog() {
        final Dialog doBdialog = new Dialog(NewEditProfile_Activity.this);
        doBdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        doBdialog.setContentView(R.layout.layout_editdob);
        doBdialog.setCancelable(true);

        // set texts here

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(doBdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        editdob_et = doBdialog.findViewById(R.id.editdob_et);
        btn_savedob = doBdialog.findViewById(R.id.btn_savedob);

        btn_savedob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        doBdialog.show();
        doBdialog.getWindow().setAttributes(lp);
    }

    private void showPhoneDialog() {
        final Dialog phonedialog = new Dialog(NewEditProfile_Activity.this);
        phonedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        phonedialog.setContentView(R.layout.layout_editphone);
        phonedialog.setCancelable(true);

        // set texts here

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(phonedialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        editphone_et = phonedialog.findViewById(R.id.editphone_et);
        verificationphone_et = phonedialog.findViewById(R.id.verificationphone_et);
        btn_savephone = phonedialog.findViewById(R.id.btn_savephone);
        btn_verification = phonedialog.findViewById(R.id.btn_verification);
        ll_phoneverify = phonedialog.findViewById(R.id.ll_phoneverify);


        btn_savephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editphone_et.getText().equals("") && !editphone_et.getText().equals(null)) {
                    Toast.makeText(NewEditProfile_Activity.this, "Verification code has been sent to: " + GlobalVariables.getPhonenumber(), Toast.LENGTH_LONG).show();
                    String phone = "+91" + GlobalVariables.getPhonenumber().toString();
                    sendVerificationCode(phone);
                    ll_phoneverify.setVisibility(View.VISIBLE);

                }else {
                    Toast.makeText(NewEditProfile_Activity.this, "number Cannot be empty", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(verificationphone_et.getText().toString());
                phonedialog.dismiss();
            }
        });

        phonedialog.show();
        phonedialog.getWindow().setAttributes(lp);
    }

    private void showEmailDialog() {
        final Dialog editdialog = new Dialog(NewEditProfile_Activity.this);
        editdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editdialog.setContentView(R.layout.layout_editemail);
        editdialog.setCancelable(true);

        // set texts here


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(editdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ll_verify = editdialog.findViewById(R.id.ll_verify);
        editeamil_et = editdialog.findViewById(R.id.editeamil_et);
        verifycode_et = editdialog.findViewById(R.id.verifycode_et);
        btn_saveemail = editdialog.findViewById(R.id.btn_saveemail);
        btn_verifyemail = editdialog.findViewById(R.id.btn_verifyemail);

        btn_saveemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editeamil_et.getText().equals("")&&!editeamil_et.equals(null)) {


                    Toast.makeText(NewEditProfile_Activity.this, "Verification code has been sent to: " + GlobalVariables.getPhonenumber(), Toast.LENGTH_LONG).show();
                    String phone = "+91" + GlobalVariables.getPhonenumber().toString();
                    sendVerificationCode(phone);


                    //  ll_verify.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(NewEditProfile_Activity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });



        btn_verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCodeEmail(verifycode_et.getText().toString());
                editdialog.dismiss();
            }
        });

        editdialog.show();
        editdialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){
            Uri uri = data.getData();
            profilepic.setImageURI(uri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NewEditProfile_Activity.this,Profile_Activity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideDown(this);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                verificationphone_et.setText(code);

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

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            JSONObject dislike = new JSONObject();
                            try {
                                dislike.put("id", String.valueOf(GlobalVariables.getId()));
                                dislike.put("flag", "3");
                                dislike.put("value", "'"+String.valueOf(editphone_et.getText())+"'");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Retrofit.Builder retrofit = new Retrofit.Builder()
                                    .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit2 = retrofit.build();


                            //get client
                            ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                            Call<ResponseBody> call_like = apiInterface.getStringuser_update(dislike.toString());
                            call_like.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                                    if (response.isSuccessful()){
                                        Log.e(TAG, "onResponse: "+response.body() );
                                        Log.e(TAG, "onResponse: "+response );
                                        GlobalVariables.setBio(String.valueOf(editphone_et.getText()));
                                        phonenumber_txt.setText(String.valueOf(editphone_et.getText()));
                                        Toast.makeText(NewEditProfile_Activity.this, "Successfully changed the number to: "+GlobalVariables.getPhonenumber(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(NewEditProfile_Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void verifyCodeEmail(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredentialE(credential);
    }
    private void signInWithCredentialE(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            JSONObject dislike = new JSONObject();
                            try {
                                dislike.put("id", String.valueOf(GlobalVariables.getId()));
                                dislike.put("flag", "3");
                                dislike.put("value", "'"+String.valueOf(editeamil_et.getText())+"'");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Retrofit.Builder retrofit = new Retrofit.Builder()
                                    .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit2 = retrofit.build();


                            //get client
                            ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                            Call<ResponseBody> call_like = apiInterface.getStringuser_update(dislike.toString());
                            call_like.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                                    if (response.isSuccessful()){
                                        Log.e(TAG, "onResponse: "+response.body() );
                                        Log.e(TAG, "onResponse: "+response );
                                        GlobalVariables.setEmail(String.valueOf(editeamil_et.getText()));
                                        editemail_txt.setText(String.valueOf(editeamil_et.getText()));
                                        Toast.makeText(NewEditProfile_Activity.this, "Successfully changed the email to: "+GlobalVariables.getEmail(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
                                }
                            });



                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(NewEditProfile_Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}