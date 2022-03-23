package com.paradox.projectsp3.VideoEditorFolder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paradox.projectsp3.R;

public class NewCameraSettings extends AppCompatActivity {



    Button sutterSound,resolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera_settings);

        resolution = findViewById(R.id.resolution);
        sutterSound = findViewById(R.id.sutterSound);

        resolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResolutionIt();
            }
        });


        sutterSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sutterSoundinIt();
            }
        });

    }


        private void ResolutionIt() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Single Choice Dialog");
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNeutralButton("Cancel", null);
        String[] items = {"On", "Off"};
        int checkItem = 1;
        alertDialog.setSingleChoiceItems(items, checkItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(getApplication(), "On", Toast.LENGTH_SHORT).show(); break;
                        case 1:
                        Toast.makeText(getApplication() , "Off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        alertDialog.show();
    }


    private void sutterSoundinIt() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Single Choice Dialog");
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNeutralButton("Cancel", null);
        String[] items = {"1080p", "720p", "480p"};
        int checkItem = 1;
        alertDialog.setSingleChoiceItems(items, checkItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(getApplication(), "1080p", Toast.LENGTH_SHORT).show(); break; case 1:
                        Toast.makeText(getApplication() , "720p", Toast.LENGTH_SHORT).show();
                        break;
                        case 2:
                        Toast.makeText(getApplication(), "480p", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        alertDialog.show();
    }



}