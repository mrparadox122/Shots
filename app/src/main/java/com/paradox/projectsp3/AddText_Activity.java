package com.paradox.projectsp3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.paradox.projectsp3.databinding.ActivityMainBinding;
import com.paradox.projectsp3.utils.CameraUtils;

import java.util.List;


public class AddText_Activity extends AppCompatActivity implements CameraUtils.OnCameraResult {

    Context context;
    CameraUtils cameraUtils;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);


        cameraUtils = new CameraUtils(this, (CameraUtils.OnCameraResult) this);
        cameraUtils.alertVideoSelcetion();


    }

    @Override
    public void onSuccess(List<ChosenImage> images) {
        
    }

    @Override
    public void onVideoSuccess(List<ChosenVideo> list) {
        if (list != null && list.size() > 0) {
//            Intent i = new Intent(AddText_Activity.this, PreviewVideoActivity.class);
//            i.putExtra("DATA", list.get(0).getOriginalPath());
//            //binding.ivProfilePic.setImageURI(Uri.fromFile(selectedImageFile));
//            startActivity(i);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        cameraUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    public void onError(String error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraUtils.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}