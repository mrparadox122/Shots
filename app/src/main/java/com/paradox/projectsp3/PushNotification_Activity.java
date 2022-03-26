package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class PushNotification_Activity extends AppCompatActivity {

    ImageView back;

    EditText shots,wishes,body;
    Button notification_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_push_notification);


        back = findViewById(R.id.back);
        shots = findViewById(R.id.shots);
        wishes = findViewById(R.id.wishes);
        body = findViewById(R.id.body);
        notification_btn = findViewById(R.id.notification_btn);



        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tittle=shots.getText().toString();
                String subject=wishes.getText().toString();
                String bodi =body.getText().toString();


                NotificationManager notif = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify
                        = new Notification.Builder(getApplicationContext()).setContentTitle(tittle).setContentText(bodi)
                        .setContentTitle(subject).setSmallIcon(R.drawable.shots_icon).build();



                notify.flags|= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0,  notify);


            }
        });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PushNotification_Activity.this,ProfileSettings_Activity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PushNotification_Activity.this,ProfileSettings_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }
}