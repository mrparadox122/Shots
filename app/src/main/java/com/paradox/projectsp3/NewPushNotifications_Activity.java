package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPushNotifications_Activity extends AppCompatActivity {

    Button notification_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_push_notifications);

        notification_btn = findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Shots is comming soon ";

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewPushNotifications_Activity.this)
                        .setSmallIcon(R.drawable.shots_icon)
                        .setContentTitle("My Shots")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent intent = new Intent(NewPushNotifications_Activity.this,NotificationView_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",message);

                PendingIntent pendingIntent = PendingIntent.getActivity(NewPushNotifications_Activity.this,
                        0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());

            }
        });

    }
}