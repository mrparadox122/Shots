package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Upload_Activity extends AppCompatActivity {


    EditText videoname;
    Button videotrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        videoname = (EditText) findViewById( R.id.videoName );
        videotrim = (Button) findViewById( R.id.trimVideo );


        if (ContextCompat.checkSelfPermission(Upload_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Upload_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },1);
            }

        }

        videotrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( videoname.getText().toString().isEmpty() ) {

                    Toast.makeText(Upload_Activity.this ,"First Enter Video Name .... ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent pickIntent = new Intent(Intent.ACTION_PICK);
                    pickIntent.setType("video/*");
                    startActivityForResult(pickIntent, 1);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ( resultCode==RESULT_OK && data != null && data.getData() !=null && requestCode == 1 ) {


            Uri uri =  data.getData();
            String videopath = WritetoExternalStorage.getVideopath( uri  , this);

            TrimVedio_Activity.addpath( videopath );

            Intent i = new Intent( Upload_Activity.this , TrimVedio_Activity.class );
            i.putExtra( "key" , videoname.getText().toString() );
            startActivity( i );
            videoname.setText("");


        }

        else if (resultCode == RESULT_CANCELED) {

            Toast.makeText(Upload_Activity.this ,"Video Selected Canceled ", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Upload_Activity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }

}