package com.paradox.projectsp3.Practice_Following;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.paradox.projectsp3.Followers_Following_Likes.Pager_Adapter;
import com.paradox.projectsp3.R;

public class MyBase_Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    public TabLayout mytabLayout;
    public ViewPager myviewPager;

    Pager_Adapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_base);


        mytabLayout =  findViewById(R.id.mytabLayout);
        myviewPager =  findViewById(R.id.myviewPager);
        mytabLayout.addTab(mytabLayout.newTab().setText("Followers"));
        mytabLayout.addTab(mytabLayout.newTab().setText("Following"));
        mytabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Pager_Adapter adapter = new Pager_Adapter(getSupportFragmentManager());
        myviewPager.setAdapter(adapter);

        mytabLayout.setupWithViewPager(myviewPager);

        //Adding onTabSelectedListener to swipe views
        mytabLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myviewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}