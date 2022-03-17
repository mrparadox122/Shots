package com.paradox.projectsp3.HomeFollowing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.HomeRecyclerView.VideoPlayerRecyclerView;
import com.paradox.projectsp3.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FollowingActivity extends AppCompatActivity {


    private VideoPlayerRecyclerView  homefollowing_recyclerview;
    List<homeFollwoingmodel>homeFollwoingmodelList;
    homeFollowingAdapter homeFollowingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_following);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        init();

        homefollowing_recyclerview = findViewById(R.id.homefollowing_recyclerview);

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


        ///// RecyclerView//////////

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homefollowing_recyclerview.setLayoutManager(layoutManager3);
        homeFollowingAdapter  = new homeFollowingAdapter(this, homeFollwoingmodelList);
        homefollowing_recyclerview.setAdapter(homeFollowingAdapter);


//        homefollowing_recyclerview = (VideoPlayerRecyclerView) findViewById(R.id.homefollowing_recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        homefollowing_recyclerview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        VerticalSpacingItemDecorator itemDecorator=new VerticalSpacingItemDecorator(0);
//        homefollowing_recyclerview.addItemDecoration(itemDecorator);
//
//        SnapHelper mSnapHelper = new PagerSnapHelper();
//        mSnapHelper.attachToRecyclerView(homefollowing_recyclerview);



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
    private RequestManager initGlide()
    {
        RequestOptions options=new RequestOptions()
                .placeholder(R.color.black)
                .error(R.color.black);
        return Glide.with(this).setDefaultRequestOptions(options);
    }
//    @Override
//    protected void onDestroy() {
//        if(recyclerview!=null)
//            recyclerview.releasePlayer();
//        super.onDestroy();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        recyclerview.releasePlayer();
//        finish();
//    }
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        finish();
//    }


    public void foryouBtn(View view) {
        Intent intent=new Intent(FollowingActivity.this, HomeActivty.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSwipeLeft(this);
        finish();


//        Api calling following


    }
}



