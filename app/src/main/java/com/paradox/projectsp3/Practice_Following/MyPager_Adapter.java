package com.paradox.projectsp3.Practice_Following;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.paradox.projectsp3.Followers_Following_Likes.Followers_Fragment;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Fragment;

public class MyPager_Adapter extends FragmentStatePagerAdapter {

    int mytabCount;

    public MyPager_Adapter(@NonNull FragmentManager fm,int mytabCount) {
        super(fm);
        this.mytabCount= mytabCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                My_Following tab1 = new My_Following();
                return tab1;
            case 1:
                FollowingFragment tab2 = new FollowingFragment();
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
        return mytabCount;
    }
}
