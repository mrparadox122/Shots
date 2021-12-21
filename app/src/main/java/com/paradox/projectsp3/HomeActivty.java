package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.paradox.projectsp3.Adapter.DemoAdapter;
import com.paradox.projectsp3.Model.MediaObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivty extends AppCompatActivity {

    private final List<MediaObject> mediaObjectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        ImageView sound_dis = (ImageView) findViewById(R.id.imageView3);
        setContentView(R.layout.home_activty);
        init();





    }

    private void init(){

        //////////////////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT< 21){
            setWindowFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,true);
        }
        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT>= 21){
            setWindowFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerview);


        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));
        mediaObjectList.add(new MediaObject("","","","","","","","","",""));

        DemoAdapter demoAdapter = new DemoAdapter(mediaObjectList, getApplicationContext());
        recyclerview.setAdapter(demoAdapter);
        demoAdapter.notifyDataSetChanged();


    }


    public static void setWindowFlag(@NotNull Activity activity, final int bits, boolean on){
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParms = win.getAttributes();
        if (on){
            winParms.flags |= bits;
        } else {
            winParms.flags &= ~bits;
        }
        win.setAttributes(winParms);
    }
}