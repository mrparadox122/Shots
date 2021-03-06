package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paradox.projectsp3.Adapter.PostAdapter;
import com.paradox.projectsp3.Adapter.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SliderView sliderView;

    private static final String TAG = "SearchActivity";
    private ArrayList<String> mImageUrls = new ArrayList<>();


    int[] images = {R.drawable.picone,
            R.drawable.pictwo,
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            };
  //SliderAdapter sliderAdapter;

    RecyclerView rv_post,rv_post1,rv_post2,rv_post3,rv_post4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);

        sliderView = findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        getImages();

    }

    private void getImages() {

        mImageUrls.add("https://www.imagesjunction.com/images/img/aesthetic_profile_pics.jpg");
        mImageUrls.add("https://www.imagesjunction.com/images/img/aesthetic_profile_pics.jpg");
        mImageUrls.add("https://www.imagesjunction.com/images/img/aesthetic_profile_pics.jpg");
        mImageUrls.add("https://www.imagesjunction.com/images/img/aesthetic_profile_pics.jpg");
        mImageUrls.add("https://www.imagesjunction.com/images/img/aesthetic_profile_pics.jpg");
        initRecyclerView();
    }

    private void initRecyclerView() {

        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_post = findViewById(R.id.rv_post);
        rv_post.setLayoutManager(layoutManager);
        PostAdapter adapter = new PostAdapter(this, mImageUrls);
        rv_post.setAdapter(adapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_post1 = findViewById(R.id.rv_post1);
        rv_post1.setLayoutManager(layoutManager1);
        PostAdapter adapter1 = new PostAdapter(this, mImageUrls);
        rv_post1.setAdapter(adapter1);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_post2 = findViewById(R.id.rv_post2);
        rv_post2.setLayoutManager(layoutManager2);
        PostAdapter adapter2 = new PostAdapter(this, mImageUrls);
        rv_post2.setAdapter(adapter2);


        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_post3 = findViewById(R.id.rv_post3);
        rv_post3.setLayoutManager(layoutManager3);
        PostAdapter adapter3 = new PostAdapter(this, mImageUrls);
        rv_post3.setAdapter(adapter3);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SearchActivity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }


}