package com.paradox.projectsp3.Followers_Following_Likes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pager_Adapter extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager_Adapter(@NonNull FragmentManager fm ,int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Followers_Fragment tab1 = new Followers_Fragment();
                return tab1;
            case 1:
                Following_Fragment tab2 = new Following_Fragment();
                return tab2;
//            case 2:
//                Suggested_Fragment tab3 = new Suggested_Fragment();
//                return tab3;
//            case 3:
//                Likes_Fragment tab4 = new Likes_Fragment();
//                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
