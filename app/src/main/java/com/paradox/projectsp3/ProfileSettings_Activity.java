package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class ProfileSettings_Activity extends AppCompatActivity {


    ImageView back;
    TextView logout_btn;

    RelativeLayout ll_manageACC,ll_pushnotification,ll_privacysettings,ll_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_settings);


        ll_manageACC= findViewById(R.id.ll_manageACC);
        ll_pushnotification= findViewById(R.id.ll_pushnotification);
        ll_privacysettings= findViewById(R.id.ll_privacysettings);
        ll_language= findViewById(R.id.ll_language);
        back= findViewById(R.id.back);
        logout_btn= findViewById(R.id.logout_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


        ll_manageACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettings_Activity.this, ManageAccount_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        ll_pushnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettings_Activity.this, PushNotification_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        ll_privacysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettings_Activity.this, PrivacySettings_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        ll_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettings_Activity.this, ContentLanguages_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettings_Activity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProfileSettings_Activity.this,Profile_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }
}