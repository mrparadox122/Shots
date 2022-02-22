package com.paradox.projectsp3.Followers_Following_Likes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.paradox.projectsp3.R;

public class BaseActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_base);



        //Initializing the tablayout
        tabLayout =  findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("Followers"));
//        tabLayout.addTab(tabLayout.newTab().setText("News"));
//        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
//        tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
//        tabLayout.addTab(tabLayout.newTab().setText("Fashion"));
//        tabLayout.addTab(tabLayout.newTab().setText("Sales"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewPager);

        //Creating our pager adapter
        Pager_Adapter adapter = new Pager_Adapter(getSupportFragmentManager());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}