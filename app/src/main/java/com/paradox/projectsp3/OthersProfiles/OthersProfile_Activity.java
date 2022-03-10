package com.paradox.projectsp3.OthersProfiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OthersProfile_Activity extends AppCompatActivity {


    ImageView o_pro_pic;
    LinearLayout o_following_ll,o_follower_ll;
    RecyclerView o_recyclerview;


    List<OthersProfileModel>othersProfileModelList;
    OthersProfileAdapter othersProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);
        getSupportActionBar().hide();


        o_pro_pic = findViewById(R.id.o_pro_pic);
        o_following_ll = findViewById(R.id.o_following_ll);
        o_follower_ll = findViewById(R.id.o_follower_ll);
        o_recyclerview = findViewById(R.id.o_recyclerview);


        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        o_recyclerview.setLayoutManager(layoutManager4);
        othersProfileAdapter  = new OthersProfileAdapter(this,othersProfileModelList);
        o_recyclerview.setAdapter(othersProfileAdapter);

    }
}