package com.paradox.projectsp3.HomeFollowing;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.Followers_Following_Likes.Suggest_Model;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.HomeRecyclerView.VideoPlayerRecyclerView;
import com.paradox.projectsp3.MessageMainActivity;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.SearchActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FollowingActivity extends AppCompatActivity {


    private ViewPager2 homefollowing_recyclerview;
    List<HomeFollwoingmodel>homeFollwoingmodelList;
    homeFollowingAdapter homeFollowingAdapter;
    TextView foltx;
    int i;

    public boolean user = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_following);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        homeFollwoingmodelList = new ArrayList<>();


        init();


        ImageView Ghar=(ImageView)findViewById(R.id.imageView14);
        ImageView profile=(ImageView)findViewById(R.id.imageView17);
        ImageView comment=(ImageView)findViewById(R.id.imageView16);
        ImageView Search=(ImageView)findViewById(R.id.imageView15) ;

        homefollowing_recyclerview = findViewById(R.id.homefollowing_recyclerview);

//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (user ==true){
//                    Intent intent = new Intent(FollowingActivity.this, MessageMainActivity.class);
//                    startActivity(intent);
//                    user = false;
//                }else {
//                    Intent intent = new Intent(FollowingActivity.this,MessageMainActivity.class);
//                    startActivity(intent);
//                    user = true;
//                }
//
//                Ghar.setImageResource(R.drawable.ic_icon_feather_home);
//                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
//                Search.setImageResource(R.drawable.ic_icon_feather_search);
//                profile.setImageResource(R.drawable.ic_icon_awesome_user_1);
//
//                Intent intent=new Intent(FollowingActivity.this, Profile_Activity.class);
//                startActivity(intent);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                Animatoo.animateSlideUp(FollowingActivity.this);
//                finish();
//            }
//        });
//
//        comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                comment.setImageResource(R.drawable.aaa);
//                Ghar.setImageResource(R.drawable.ic_icon_feather_home);
//                Search.setImageResource(R.drawable.ic_icon_feather_search);
//                profile.setImageResource(R.drawable.ic_icon_awesome_user);
//
//
//                Intent intent=new Intent(FollowingActivity.this,MessageMainActivity.class);
//                startActivity(intent);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                Animatoo.animateSlideUp(FollowingActivity.this);
//                finish();
//            }
//        });
//        Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Search.setImageResource(R.drawable.ic_icon_feather_search_1);
//                Ghar.setImageResource(R.drawable.ic_icon_feather_home);
//                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
//                profile.setImageResource(R.drawable.ic_icon_awesome_user);
//
//
//                Intent intent=new Intent(FollowingActivity.this, SearchActivity.class);
//                startActivity(intent);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                Animatoo.animateSlideUp(FollowingActivity.this);
//                finish();
//            }
//        });
//
//        Ghar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Ghar.setImageResource(R.drawable.ic_icon_feather_home_1);
//                comment.setImageResource(R.drawable.ic_icon_feather_message_circle);
//                Search.setImageResource(R.drawable.ic_icon_feather_search);
//                profile.setImageResource(R.drawable.ic_icon_awesome_user);
//            }
//        });
//
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



        JSONObject data = new JSONObject();
        try {

            data.put("username", GlobalVariables.folglist.toString());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.folglist.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofitt = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface apii = retrofitt.create(ApiInterface.class);
        Call<String> calll = apii.getUserdetails_d(data.toString());
        calll.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "Responsestring//////////////////////" + String.valueOf(response.body()));
                //Toast.makeText()
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    if (response.body() != null) {

                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        //Following_Fragment following_fragment = new Following_Fragment();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(jsonresponse);
                            if(true){
                                ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("body");

                                for (i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    Suggest_Model suggest_model = new Suggest_Model();
                                    HomeFollwoingmodel homeFollwoingmodel = new HomeFollwoingmodel();
                                    homeFollwoingmodel.setComments(dataobj.getString("comments"));
                                    homeFollwoingmodel.setDescription(dataobj.getString("description"));
                                    homeFollwoingmodel.setProfile_pic(dataobj.getString("profile_pic"));
                                    homeFollwoingmodel.setMedia_url(dataobj.getString("url"));
                                    homeFollwoingmodel.setLikes(dataobj.getString("likes"));
                                    homeFollwoingmodel.setShares(dataobj.getString("shares"));
                                    homeFollwoingmodel.setViews(dataobj.getString("views"));
                                    homeFollwoingmodel.setPost_categories(dataobj.getString("catergory_name"));
                                    homeFollwoingmodel.setUrl(dataobj.getString("url"));
                                    homeFollwoingmodel.setUser_name(dataobj.getString("fullname"));
                                    homeFollwoingmodel.setPost_id(dataobj.getString("post_id"));
                                    homeFollwoingmodel.setUser_id(dataobj.getString("user_id"));
                                    homeFollwoingmodel.setVideo_id(dataobj.getString("videoid"));
                                    homeFollwoingmodelList.add(homeFollwoingmodel);
                                    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                                    homeFollowingAdapter  = new homeFollowingAdapter(getApplicationContext(), homeFollwoingmodelList);
                                    homefollowing_recyclerview.setAdapter(homeFollowingAdapter);
//                                    foltx.setVisibility(View.GONE);

//                                    suggestmodel.add(suggest_model);
//                                    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                                    suggest_rv.setLayoutManager(layoutManager3);
//                                    Suggest_Adapter adapter = new Suggest_Adapter(getApplicationContext(),suggestmodel);
//                                    suggest_rv.setAdapter(adapter);
                                    //following_model.add(following_model1);


                                    //Log.e(TAG, "writeTv: "+ GlobalVariables.getFullname()+GlobalVariables.getUsername()+following_model+following_model1.getUsername() );
                                }
                                for (int j = 0; j < UserDetailsArrayList.size(); j++){
//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), obj.optString("message")+"", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: //////////////////" + t);

            }
        });



//        like_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checklike)
//                {
//                    JSONObject dislike = new JSONObject();
//                    try {
//                        dislike.put("video_id", GlobalVariables.getVideoid());
//                        dislike.put("flag", "1");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    Retrofit.Builder retrofit = new Retrofit.Builder()
//                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
//                            .addConverterFactory(ScalarsConverterFactory.create())
//                            .addConverterFactory(GsonConverterFactory.create());
//                    Retrofit retrofit2 = retrofit.build();
//
//
//                    //get client
//                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
//                    Call<String> call_like = apiInterface.getStringScalar(dislike.toString());
//                    call_like.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
//                            if (response.isSuccessful()){
//                                likesno+=1;
//                                like_txt.setText(String.valueOf(likesno));
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
////                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                    like_image.setImageResource(R.drawable.ic_icon_material_favorite_red);
//                    checklike=false;
//                }
//                else if (!checklike)
//                {
//                    JSONObject dislike = new JSONObject();
//                    try {
//                        dislike.put("video_id", GlobalVariables.getVideoid());
//                        dislike.put("flag", "4");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    Retrofit.Builder retrofit = new Retrofit.Builder()
//                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
//                            .addConverterFactory(ScalarsConverterFactory.create())
//                            .addConverterFactory(GsonConverterFactory.create());
//                    Retrofit retrofit2 = retrofit.build();
//
//                    //get client
//                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
//                    Call<String> call_like = apiInterface.getStringScalar(dislike.toString());
//                    call_like.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            if (response.isSuccessful()){
//                                likesno-=1;
//                                Toast.makeText(getApplicationContext(), "//"+"disliked"+response, Toast.LENGTH_SHORT).show();
//                                like_txt.setText(String.valueOf(likesno));
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    like_image.setImageResource(R.drawable.ic_icon_material_favorite);
//                    checklike=true;
//                }
//            }
//        });
//
//
//        comment_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyVideosScreen_Activity.this, P_Commnets.class);
//                startActivity(intent);
//
//                final Dialog dialog = new Dialog(MyVideosScreen_Activity.this);
//                dialog.setContentView(R.layout.comment_list);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
////                recyclerview_cm = findViewById(R.id.recyclerview);
////                // comment_screen=findViewById(R.id.comment_screen);
////                cmsend_btn = findViewById(R.id.send_btn);
////                cmGoback = findViewById(R.id.Goback);
////
////                recyclerview_cm.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
////                cmcomments_modelList = new ArrayList<>();
////                cm_adapter = new Comments_Adapter(this,cmcomments_modelList);
////                recyclerview_cm.setAdapter(cm_adapter);
//            }
//
//        });
//
//
//        ///// RecyclerView//////////
//
//
//
//
////        homefollowing_recyclerview = (VideoPlayerRecyclerView) findViewById(R.id.homefollowing_recyclerview);
////        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
////        layoutManager.setOrientation(RecyclerView.VERTICAL);
////        homefollowing_recyclerview.setLayoutManager(layoutManager);
////        layoutManager.setReverseLayout(true);
////        layoutManager.setStackFromEnd(true);
////        VerticalSpacingItemDecorator itemDecorator=new VerticalSpacingItemDecorator(0);
////        homefollowing_recyclerview.addItemDecoration(itemDecorator);
////
////        SnapHelper mSnapHelper = new PagerSnapHelper();
////        mSnapHelper.attachToRecyclerView(homefollowing_recyclerview);



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



