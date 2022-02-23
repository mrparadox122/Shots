package com.paradox.projectsp3.Followers_Following_Likes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class Pager_Adapter extends FragmentPagerAdapter {

    int tabcount;

    public Pager_Adapter(@NonNull FragmentManager fm) {
        super(fm);

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new Following_Fragment();
        else if (position == 1)
            fragment = new Followers_Fragment();

        return fragment;

    }

    @Override
    public int getCount() {
        return tabcount;
    }


}
