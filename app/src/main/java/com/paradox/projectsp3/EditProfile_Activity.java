package com.paradox.projectsp3;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.drjacky.imagepicker.ImagePicker;

public class EditProfile_Activity extends AppCompatActivity {

TextView pic_change;
ImageView profilepic;
TextView txt_name;

LinearLayout ll_editname,ll_editemail,ll_editphone,ll_editDoB,ll_editgender,ll_editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        pic_change = findViewById(R.id.pic_change);
        profilepic = findViewById(R.id.profilepic);
        ll_editname = findViewById(R.id.ll_editname);
        ll_editemail = findViewById(R.id.ll_editemail);
        ll_editphone = findViewById(R.id.ll_editphone);
        ll_editDoB = findViewById(R.id.ll_editDoB);
        ll_editgender = findViewById(R.id.ll_editgender);
        ll_editBio = findViewById(R.id.ll_editBio);
        txt_name = findViewById(R.id.txt_name);

        ll_editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameDialog();
            }
        });
        ll_editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmailDialog();
            }
        });
        ll_editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhoneDialog();
            }
        });
        ll_editDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoBDialog();
            }
        });
        ll_editgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog();
            }
        });
        ll_editBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBioDialog();
            }
        });

        pic_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(EditProfile_Activity.this)
                        .crop()
                        .maxResultSize(1080,1080)
                        .start(10);
            }
        });

    }

    private void showNameDialog() {
        final Dialog namedialog = new Dialog(EditProfile_Activity.this);
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
        final Dialog biodialog = new Dialog(EditProfile_Activity.this);
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
        final Dialog genderdialog = new Dialog(EditProfile_Activity.this);
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
        final Dialog doBdialog = new Dialog(EditProfile_Activity.this);
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
        final Dialog phonedialog = new Dialog(EditProfile_Activity.this);
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
        final Dialog editdialog = new Dialog(EditProfile_Activity.this);
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
        Intent intent=new Intent(EditProfile_Activity.this,Profile_Activity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideDown(this);
        finish();

    }

}