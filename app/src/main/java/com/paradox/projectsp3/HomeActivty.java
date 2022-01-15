package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;


import com.paradox.projectsp3.MainRecyclerView.VerticalSpacingItemDecorator;
import com.paradox.projectsp3.MainRecyclerView.VideoPlayerRecyclerAdapter;
import com.paradox.projectsp3.MainRecyclerView.VideoPlayerRecyclerView;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;
import com.paradox.projectsp3.VideoEditorFolder.PortraitCameraActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivty extends AppCompatActivity {

    private ArrayList<MediaObject> mediaObjectList=new ArrayList<>();
    private VideoPlayerRecyclerView  recyclerview;
    public static ApiInterface apiInterface;
    private static final int CAMERA_PERMISSION_REQUEST=888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
     setContentView(R.layout.home_activty);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        init();
        ImageView Ghar=(ImageView)findViewById(R.id.imageView14);
        ImageView profile=(ImageView)findViewById(R.id.imageView17);
        ImageView comment=(ImageView)findViewById(R.id.imageView16);
        ImageView Search=(ImageView)findViewById(R.id.imageView15) ;
        checkPermission();

        //// onclick///

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ghar.setImageResource(R.drawable.ic_icon_feather_home);
                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
                Search.setImageResource(R.drawable.ic_icon_feather_search);
                profile.setImageResource(R.drawable.ic_icon_awesome_user_1);

                Intent intent=new Intent(HomeActivty.this,AccountActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Animatoo.animateSlideUp(HomeActivty.this);
                finish();
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment.setImageResource(R.drawable.aaa);
                Ghar.setImageResource(R.drawable.ic_icon_feather_home);

                Search.setImageResource(R.drawable.ic_icon_feather_search);
                profile.setImageResource(R.drawable.ic_icon_awesome_user);

                Intent intent=new Intent(HomeActivty.this,MessageMainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Animatoo.animateSlideUp(HomeActivty.this);
                finish();
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search.setImageResource(R.drawable.ic_icon_feather_search_1);
                Ghar.setImageResource(R.drawable.ic_icon_feather_home);
                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
                profile.setImageResource(R.drawable.ic_icon_awesome_user);


                Intent intent=new Intent(HomeActivty.this,SearchActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Animatoo.animateSlideUp(HomeActivty.this);
                finish();
            }
        });

        Ghar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ghar.setImageResource(R.drawable.ic_icon_feather_home_1);

                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
                Search.setImageResource(R.drawable.ic_icon_feather_search);
                profile.setImageResource(R.drawable.ic_icon_awesome_user);
            }
        });


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
                    Toast.makeText(HomeActivty.this,"Network Error.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(HomeActivty.this,"Network Error.",Toast.LENGTH_SHORT).show();
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
        //finish();
    }


    public void following(View view) {
        Intent intent=new Intent(HomeActivty.this,FollowingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Animatoo.animateSwipeRight(this);
        finish();
    }

    public void addBtn(View view) {
        showbottomsheet();
//        Intent intent=new Intent(HomeActivty.this, PortraitCameraActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        Animatoo.animateSlideUp(this);
//        finish();
    }

    public void showbottomsheet() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_bottom_sheet);

        LinearLayout ll_createshort = dialog.findViewById(R.id.ll_createshort);
        LinearLayout ll_uploadvedio = dialog.findViewById(R.id.ll_uploadvedio);
        LinearLayout ll_golive = dialog.findViewById(R.id.ll_golive);

        ll_createshort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivty.this, PortraitCameraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Log.e(TAG, "onClick: //////////////////////////////////" );
                //Animatoo.animateSlideUp(this);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500);
                finish();
            }
        });


        ll_uploadvedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                checkPermission();
                Intent intent=new Intent(HomeActivty.this, MyVideoView_Activity.class);
                startActivity(intent);
//                intent.setType("video/mp4");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Video"),100);
////                //Animatoo.animateSlideUp(this);

                Log.e(TAG, "onClick: //////////////////////////////////" );

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500);
                finish();


            }

        });

        ll_golive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeActivty.this, GoLive_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Log.e(TAG, "onClick: //////////////////////////////////" );

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1);
                finish();

            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//     checkPermission();
//    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return;
        }
        //request camera permission if it has not been granted
        if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
          requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},CAMERA_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case CAMERA_PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {


                }
                else
                {

                }
                break;
        }
    }

}