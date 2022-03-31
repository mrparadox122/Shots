package com.paradox.projectsp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileSettings_Activity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    ImageView back;
    TextView logout;
    RelativeLayout ll_manageACC,ll_pushnotification,ll_privacysettings,ll_communityguidelines,ll_Leagal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_settings);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ll_manageACC= findViewById(R.id.ll_manageACC);
        ll_pushnotification= findViewById(R.id.ll_pushnotification);
        ll_privacysettings= findViewById(R.id.ll_privacysettings);
        back= findViewById(R.id.back);
        logout= findViewById(R.id.logout);
        ll_Leagal= findViewById(R.id.ll_Leagal);
        ll_communityguidelines= findViewById(R.id.ll_communityguidelines);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        ll_communityguidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shots.ddns.net/dashboard/ShotsLegal/CommunitGuidelines.html"));
                startActivity(browserIntent);
            }
        });

        ll_Leagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://shots.ddns.net/dashboard/ShotsLegal/index.html"));
                startActivity(browserIntent);
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
                Intent intent = new Intent(ProfileSettings_Activity.this, NewPushNotifications_Activity.class);
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

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ProfileSettings_Activity.this,Login.class);
                        startActivity(intent);
                        Toast.makeText(ProfileSettings_Activity.this, "SignOut", Toast.LENGTH_SHORT).show();
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