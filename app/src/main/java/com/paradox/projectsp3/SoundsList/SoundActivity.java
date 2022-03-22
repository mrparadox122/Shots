package com.paradox.projectsp3.SoundsList;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.VideoEditorFolder.BaseCameraActivity;

import java.io.File;
import java.util.ArrayList;

public class SoundActivity extends AppCompatActivity {



    ListView sounlst;

    String[] itms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        sounlst = (ListView) findViewById(R.id.sounlst);
        runtimePremession();
    }

    public void runtimePremession() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(SoundActivity.this, "Got the Permission :)", Toast.LENGTH_SHORT).show();
                        Displysog();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(SoundActivity.this, "Cant get the permission :(", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }




    public ArrayList<File> findSn(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        Log.e(TAG, "findSn: " + files);
        if (files != null) {
           for (File singlfile : files) {
               if (singlfile.isDirectory() && !singlfile.isHidden()) {
                   arrayList.addAll(findSn(singlfile));
               } else {
                   if (singlfile.getName().endsWith(".mp3") || singlfile.getName().endsWith(".wav")) {
                       arrayList.add(singlfile);
                   }
               }
           }
       }






        return arrayList;


    }


    public void Displysog(){
        new Thread(new Runnable() { @Override public void run() {

            final ArrayList<File> mySongs = findSn(Environment.getExternalStorageDirectory());
            itms = new String[mySongs.size()];
            for (int i=0; i <mySongs.size();i++){
                itms[i]=mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
            }
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    customAdaptor custmAdaptor = new customAdaptor();
                    sounlst.setAdapter(custmAdaptor);

                }
            });
            Log.e(TAG, "run://////////////////////////////////////////////////// " );



        } }).start();




    }

    class customAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return itms.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View Convertview, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.soundlistitems,null);
            TextView txtSong = view.findViewById(R.id.songttx);
            txtSong.setSelected(true);
            txtSong.setText(itms[i]);
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SoundActivity.this, BaseCameraActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(SoundActivity.this);
        finish();
    }
}