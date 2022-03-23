package com.paradox.projectsp3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PostVideo_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText description_edit;
    ImageView video_thumbnail;
    TextView privcy_type_txt;
    Switch comment_switch;
    String[] Section = { "Public", "Following", "Followers" };

    String[] Category = { "Dance", "Fashion", "Comedy","Food","Motivational", "News", "Medical","Gaming",

            "Traditional", "Fitness", "Duet","Health","Jobs", "Entertainment", "Cooking","Science" };
    Spinner spinneracces,spinner_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        description_edit = findViewById(R.id.description_edit);
        video_thumbnail = findViewById(R.id.video_thumbnail);
        description_edit = findViewById(R.id.description_edit);
        privcy_type_txt = findViewById(R.id.privcy_type_txt);
        comment_switch = findViewById(R.id.comment_switch);


        comment_switch.setChecked(true);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Section);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        spinner_category = findViewById(R.id.spinner_category);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter category_ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Category);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(category_ad);

//        singleSelectionDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
////                singleChoiceDialog();
//
//            }
//        });

//        privcy_type_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getApplicationContext(), Section[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

//    private void singleChoiceDialog() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("Single Choice Dialog");
//        alertDialog.setPositiveButton("OK", null);
//        alertDialog.setNeutralButton("Cancel", null);
//        String[] items = {"Public", "Kotlin", "Dart"};
//        int checkItem = 1;
//        alertDialog.setSingleChoiceItems(items, checkItem, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:
//                        Toast.makeText(getApplication(), "Java", Toast.LENGTH_SHORT).show(); break; case 1:
//                        Toast.makeText(getApplication() , "Kotlin", Toast.LENGTH_SHORT).show();
//                        break;
//                        case 2:
//                        Toast.makeText(getApplication(), "Dart", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
//        alertDialog.show();
//    }
}