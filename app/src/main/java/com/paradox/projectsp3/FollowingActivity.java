package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.paradox.projectsp3.HomeRecyclerView.VerticalSpacingItemDecorator;
import com.paradox.projectsp3.HomeRecyclerView.VideoPlayerRecyclerAdapter;
import com.paradox.projectsp3.HomeRecyclerView.VideoPlayerRecyclerView;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingActivity extends AppCompatActivity {
    private ArrayList<MediaObject> mediaObjectList=new ArrayList<>();
    private VideoPlayerRecyclerView  recyclerview;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_following);
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
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


        ///// RecyclerView//////////

        recyclerview = (VideoPlayerRecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        VerticalSpacingItemDecorator itemDecorator=new VerticalSpacingItemDecorator(0);
        recyclerview.addItemDecoration(itemDecorator);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerview);

        LoadAllPosts();





    }

    private void LoadAllPosts() {
        Call<Users> call=apiInterface.performAllPosts();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.isSuccessful())
                {
                    mediaObjectList=(ArrayList<MediaObject>) response.body().getAllPosts();
                    recyclerview.setMediaObjects(mediaObjectList);
                    VideoPlayerRecyclerAdapter adapter=new VideoPlayerRecyclerAdapter(mediaObjectList,initGlide());
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerview.setKeepScreenOn(true);
                    recyclerview.smoothScrollToPosition(mediaObjectList.size()+1);

                }
                else
                {
                    Toast.makeText(FollowingActivity.this,"Network Error.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(FollowingActivity.this,"Network Error.",Toast.LENGTH_SHORT).show();


            }
        });
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
    @Override
    protected void onDestroy() {
        if(recyclerview!=null)
            recyclerview.releasePlayer();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerview.releasePlayer();
        finish();
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }


    public void foryouBtn(View view) {
        Intent intent=new Intent(FollowingActivity.this,HomeActivty.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSwipeLeft(this);
        finish();
    }
}



