package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.drjacky.imagepicker.ImagePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class NewEditProfile_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String Dob,gender;


    String[] Gender = {"Male", "Female", "Others"};
    String[] Options = {"Phone"};

    int mDay,mMonth,mYear;
    boolean isDob;
    TextView pic_change;
    ImageView profilepic;

    ImageView back_btn;

    TextView nameedit_btn,emailedit_btn,phoneedit_btn,datePickerButton,genderedit_btn,bioedit_btn;

    EditText editname_et,editphone_et,editgender_et,editeamil_et,editdob_et,editbio_et;
    Button btn_savephone,btn_savename,btn_savegender,btn_saveemail,btn_savedob,btn_savebio;

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
        profilepic = findViewById(R.id.profilepic);
        nameedit_btn = findViewById(R.id.nameedit_btn);
        emailedit_btn = findViewById(R.id.emailedit_btn);
        phoneedit_btn = findViewById(R.id.phoneedit_btn);
        datePickerButton = findViewById(R.id.datePickerButton);
        genderedit_btnS = findViewById(R.id.spinner);
        bioedit_btn = findViewById(R.id.bioedit_btn);



        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                //set time zone
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                int selectedYear = calendar.get(Calendar.YEAR);
                int selectedMonth = calendar.get(Calendar.MONTH);
                int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewEditProfile_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                mDay = selectedDay;
                                mMonth = selectedMonth;
                                mYear = selectedYear;
//                                Dob = makeDateitgkm(selectedYear,selectedMonth,selectedDay);
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
                calendar3.set(1990, 1, 1);
                //Set One Month date from today date to calendar
                //calendar3.add(Calendar.MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar3.getTimeInMillis());
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });
//        editname_et = findViewById(R.id.editname_et);


        nameedit_btn.setText(String.valueOf(GlobalVariables.getFullname()));
        emailedit_btn.setText(String.valueOf(GlobalVariables.getEmail()));
        phoneedit_btn.setText(String.valueOf(GlobalVariables.getPhonenumber()));
//        genderedit_btn.setText(String.valueOf(GlobalVariables.getGender()));
//        dobedit_btn.setText(String.valueOf(GlobalVariables.getDob()));
        bioedit_btn.setText(String.valueOf(GlobalVariables.getBio()));
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
        btn_savephone = phonedialog.findViewById(R.id.btn_savephone);

        btn_savephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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

        editeamil_et = editdialog.findViewById(R.id.editeamil_et);
        btn_saveemail = editdialog.findViewById(R.id.btn_saveemail);

        btn_saveemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}