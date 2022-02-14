package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.drjacky.imagepicker.ImagePicker;

public class NewEditProfile_Activity extends AppCompatActivity {


    TextView pic_change;
    ImageView profilepic;
    TextView txt_name;

    //LinearLayout ll_editname,ll_editemail,ll_editphone,ll_editDoB,ll_editgender,ll_editBio;
    ImageView back_btn;

    TextView nameedit_btn,emailedit_btn,phoneedit_btn,dobedit_btn,genderedit_btn,bioedit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_edit_profile);


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
        dobedit_btn = findViewById(R.id.dobedit_btn);
        genderedit_btn = findViewById(R.id.genderedit_btn);
        bioedit_btn = findViewById(R.id.bioedit_btn);
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
        dobedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoBDialog();
            }
        });
        genderedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog();
            }
        });
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


        biodialog.show();
        biodialog.getWindow().setAttributes(lp);

    }

    private void showGenderDialog() {
        final Dialog genderdialog = new Dialog(NewEditProfile_Activity.this);
        genderdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        genderdialog.setContentView(R.layout.layout_editgender);
        genderdialog.setCancelable(true);

// set texts here


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(genderdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        genderdialog.show();
        genderdialog.getWindow().setAttributes(lp);
    }

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

}