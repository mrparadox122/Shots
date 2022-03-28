package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paradox.projectsp3.Adapter.MyVideos_Adapter;
import com.paradox.projectsp3.Followers_Following_Likes.BaseActivity;
import com.paradox.projectsp3.Followers_Following_Likes.Follower_model;
import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.Followers_Following_Likes.Followers_Fragment;
import com.paradox.projectsp3.Followers_Following_Likes.FollowingAdapter;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Fragment;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.Followers_Following_Likes.Suggest_Adapter;
import com.paradox.projectsp3.Followers_Following_Likes.Suggest_Model;
import com.paradox.projectsp3.Model.My_VideosModel;
import com.paradox.projectsp3.Model.UserDetails;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile_Activity extends AppCompatActivity {

    public String id;
   ImageView settings , back,share_img,profilemenu;
   CircleImageView pro_pic;
   TextView pro_name,email,bio,edit_profile;
//   private Button edit_profile;
   private ArrayList<UserDetails> userDetailsArrayList = new ArrayList<>();
   public static ApiInterface apiInterface;

   List<UserDetails> userDetails;

   LinearLayout following_ll,follower_ll;

   public TextView following_text,followers_text,likes_text;

   RecyclerView suggest_rv;
    int i;
    List<Suggest_Model> suggestmodel;
    List<My_VideosModel> videomodle;
    SwipeRefreshLayout refrestly;


    String suggest;

    UserDetails UserDetails = new UserDetails();

    public RecyclerView recyclerview;

    MyVideos_Adapter myVideos_adapter;
    Context context;
//    List<My_VideosModel> my_videosModelList;

    Resources resources;
    int lang_selected;
    Button btn_login,email_button,mobile_button;
    TextView createnewACC,skip_txt,back_btn;

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().getDecorView().setBackgroundColor(R.color.app_color);
        setContentView(R.layout.activity_account);


        settings = findViewById(R.id.settings);
        back = findViewById(R.id.back);
        edit_profile = findViewById(R.id.edit_profile);
        pro_pic = findViewById(R.id.pro_pic);
        pro_name = findViewById(R.id.pro_name);
        email = findViewById(R.id.email);
        bio = findViewById(R.id.bio);
        following_text = findViewById(R.id.following_text);
        followers_text = findViewById(R.id.followers_text);
        likes_text = findViewById(R.id.likes_text);
        following_ll = findViewById(R.id.following_ll);
        share_img = findViewById(R.id.share_img);
        follower_ll = findViewById(R.id.follower_ll);
        suggest_rv = findViewById(R.id.suggest_rv);
        suggestmodel = new ArrayList<>();
        videomodle = new ArrayList<>();
        refrestly = findViewById(R.id.refrestly);
        recyclerview = findViewById(R.id.recyclerview);
        profilemenu = findViewById(R.id.profilemenu);

        btn_login = findViewById(R.id.login_bt);
        skip_txt = findViewById(R.id.skip_txt);



        if(LocaleHelper.getLanguage(Profile_Activity.this).equalsIgnoreCase("en"))
        {
            context = LocaleHelper.setLocale(Profile_Activity.this,"en");
            resources =context.getResources();
//            btn_login = findViewById(R.id.login_bt);
//           skip_txt = findViewById(R.id.skip_txt);
//            btn_login.setText(resources.getString(R.string.login));
//            skip_txt.setText(resources.getString(R.string.skip_login));
            setTitle(resources.getString(R.string.app_name));
            lang_selected = 0;
        }else if(LocaleHelper.getLanguage(Profile_Activity.this).equalsIgnoreCase("hi")){
            context = LocaleHelper.setLocale(Profile_Activity.this,"hi");
            resources =context.getResources();
            skip_txt = findViewById(R.id.skip_txt);
            btn_login.setText(resources.getString(R.string.login));
            skip_txt.setText(resources.getString(R.string.skip_login));

            setTitle(resources.getString(R.string.app_name));
            lang_selected =1;
        }
        else if(LocaleHelper.getLanguage(Profile_Activity.this).equalsIgnoreCase("kn")){
            context = LocaleHelper.setLocale(Profile_Activity.this,"kn");
            resources =context.getResources();
//            skip_txt = findViewById(R.id.skip_txt);
//            btn_login.setText(resources.getString(R.string.login));
//            skip_txt.setText(resources.getString(R.string.skip_login));
//            setTitle(resources.getString(R.string.app_name));
            setTitle(resources.getString(R.string.app_name));
            lang_selected =2;
        }


        follower_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Followers_Fragment.class);
                startActivity(intent);
            }
        });
        following_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Following_Fragment.class);
                startActivity(intent);
            }
        });

        profilemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Profile_Activity.this);
                dialog.setContentView(R.layout.bottomsheetlayout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                TextView reffer_txt = dialog.findViewById(R.id.reffer_txt);
                reffer_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile_Activity.this,RefferandEarn_Activity.class);
                        startActivity(intent);
                    }
                });

                TextView diamonds_txt = dialog.findViewById(R.id.diamonds_txt);
                diamonds_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                TextView settings_txt = dialog.findViewById(R.id.settings_txt);
                settings_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile_Activity.this,ProfileSettings_Activity.class);
                        startActivity(intent);
                    }
                });

                TextView language_txt = dialog.findViewById(R.id.language_txt);
                language_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        languageselect();
                    }
                });

                TextView notification_txt = dialog.findViewById(R.id.notification_txt);
                notification_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile_Activity.this,RefferandEarn_Activity.class);
                        startActivity(intent);
                    }
                });


                TextView inbox_txt = dialog.findViewById(R.id.inbox_txt);
                inbox_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile_Activity.this,RefferandEarn_Activity.class);
                        startActivity(intent);
                    }
                });



            }
        });

        refrestly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });
        JSONObject dataa = new JSONObject();
        try {

            dataa.put("username",GlobalVariables.getId());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofittt = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface apiii = retrofittt.create(ApiInterface.class);
        Call<String> callll = apiii.getUserdetails_videos(dataa.toString());
        callll.enqueue(new Callback<String>() {
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
                                    My_VideosModel my_videosModel = new My_VideosModel();
//                                    Suggest_Model suggest_model = new Suggest_Model();
//                                    suggest_model.setUsernamee(dataobj.getString("fullname").toString());
//                                    suggest_model.setProfile_picc(dataobj.getString("profile_pic").toString());
//                                    suggest_model.setIdd(dataobj.getString("id").toString());
                                    my_videosModel.setImg_url(dataobj.getString("url"));
                                    my_videosModel.setViewtext(dataobj.getString("views"));
                                    my_videosModel.setVideoid(dataobj.getString("videoid"));
                                    my_videosModel.setPid(dataobj.getString("post_id"));
                                    //suggestmodel.add(suggest_model);
                                    videomodle.add(my_videosModel);

                                    recyclerview.setLayoutManager(new GridLayoutManager(context,3));

                                    myVideos_adapter = new MyVideos_Adapter(context,videomodle);
                                    recyclerview.setAdapter(myVideos_adapter);

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
//                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }

        });





//        apiInterface = ApiClient.getUserDetails().create(ApiInterface.class);
        userDetails = new ArrayList<>();

        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("video/mp4");
                String body = "Shots Commming Soon";
                String sub = "Shots is identifying ypur creativity";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        LoadAllDetails();

        follower_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Profile_Activity.this, BaseActivity.class);
//                startActivity(intent);
            }
        });


        following_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this, BaseActivity.class);
                startActivity(intent);
            }
        });

        follower_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this, BaseActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this,HomeActivty.class);
                startActivity(intent);
              }
        });

//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Profile_Activity.this,ProfileSettings_Activity.class);
//                startActivity(intent);
//            }
//        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this,NewEditProfile_Activity.class);
                startActivity(intent);
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            GlobalVariables.setUsername(acct.getEmail());
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            pro_name.setText(personName);
            email.setText(personEmail);
            UserDetails.setProfilePic(String.valueOf(personPhoto));
            GlobalVariables.setProfile_pic(String.valueOf(personPhoto));
            JSONObject data1 = new JSONObject();
            try {
                data1.put("subject", GlobalVariables.getId());
                Log.e(TAG, "getResponse:json data put for api"+GlobalVariables.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String p = personPhoto.getPath();
            File file = null;
            file = new File(p);
            RequestBody descriptionPart = RequestBody.create(MediaType.parse("application/json"), data1.toString());
            RequestBody filepart1 = RequestBody.create(MediaType.parse("image/*"),file);
            MultipartBody.Part file2= MultipartBody.Part.createFormData("avatar",file.getName(),filepart1);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.userfoto)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<String> call = api.upload_pic(GlobalVariables.getId(),file2);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("Responsestring", String.valueOf(response.body()));
                    //Toast.makeText()
                    if (response.isSuccessful()) {
                        Toast.makeText(Profile_Activity.this, "New pic added", Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString());

                            String jsonresponse = response.body().toString();
                            //writeTv(jsonresponse);

                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
            Glide.with(this).load(String.valueOf(personPhoto)).into(pro_pic);
            LoadAllDetails();
        }
    }

    private void languageselect() {
        Login login = new Login();
            AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        dialogBuilder .setTitle("Select a Language");
        dialogBuilder .setPositiveButton("OK", null);
        dialogBuilder .setNeutralButton("Cancel", null);
            String[] Language = {"ENGLISH", "हिन्दी","తెలుగు"};
            int checkItem = 1;
        dialogBuilder .setSingleChoiceItems(Language, checkItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(Language[i].equals("ENGLISH")){
                        context = LocaleHelper.setLocale(Profile_Activity.this,"en");
                        resources =context.getResources();
                        lang_selected = 0;
                            login.btn_login = findViewById(R.id.login_bt);
                            login.skip_txt = findViewById(R.id.skip_txt);
                            login.btn_login.setText(resources.getString(R.string.login));
                            login.skip_txt.setText(resources.getString(R.string.skip_login));
                            setTitle(resources.getString(R.string.app_name));
                    }
                    if(Language[i].equals("हिन्दी"))
                    {
                        context = LocaleHelper.setLocale(Profile_Activity.this,"hi");
                        resources =context.getResources();
                        lang_selected = 1;
                        login.btn_login = findViewById(R.id.login_bt);
                        login.skip_txt = findViewById(R.id.skip_txt);
                        login.btn_login.setText(resources.getString(R.string.login));
                        login.skip_txt.setText(resources.getString(R.string.skip_login));
                        setTitle(resources.getString(R.string.app_name));
                    }
                    if(Language[i].equals("తెలుగు"))
                    {
                        context = LocaleHelper.setLocale(Profile_Activity.this,"kn");
                        resources =context.getResources();
                        lang_selected = 2;
                        login.btn_login = findViewById(R.id.login_bt);
                        login.skip_txt = findViewById(R.id.skip_txt);
                        login.btn_login.setText(resources.getString(R.string.login));
                        login.skip_txt.setText(resources.getString(R.string.skip_login));
                        setTitle(resources.getString(R.string.app_name));
                    }
                }
        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        dialogBuilder .show();
        }


//    private void followinglist() {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("username", GlobalVariables.getId());
//
//        MyInterface myInterface = MyRetrofit.getClient().create(MyInterface.class);
//        Call<JsonObject>apiResponce = myInterface.get_following(jsonObject);
//        try {
//            apiResponce.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    if (response.isSuccessful()){
//                        Log.e(TAG, "onResponse: "+response.body() );
//
//
//
//                    }else {
//                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    Log.e(TAG, "onFailure: "+t );
//                }
//            });
//        }catch (Exception e){
//            Log.e(TAG, "followinglist: "+e );
//        }
//
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Profile_Activity.this,HomeActivty.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideDown(this);
        finish();

    }
    private void LoadAllDetails(){
            getResponse();


    }


    private void getResponse(){
        JSONObject data = new JSONObject();
        try {
            GlobalVariables globalVariables = new GlobalVariables();
            data.put("username", globalVariables.username);
            Log.e(TAG, "getResponse:json data put for api"+globalVariables.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetailurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getUserdetails(data.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", String.valueOf(response.body()));
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (GlobalVariables.autorefs){
                            finish();
                            startActivity(getIntent());
                            GlobalVariables.autorefs = false;
                        }
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        writeTv(jsonresponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeTv(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(true){

                ArrayList<UserDetails> UserDetailsArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("body");

                for (int i = 0; i < dataArray.length(); i++) {
                    UserDetails UserDetails = new UserDetails();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    UserDetails.setId(dataobj.getString("id"));
                    UserDetails.setFulllname(dataobj.getString("fullname"));
                    UserDetails.setEmail(dataobj.getString("email"));
                    UserDetails.setDob(dataobj.getString("Dob"));
                    UserDetails.setGender(dataobj.getString("Gender"));
                    UserDetails.setPhoneNumber(dataobj.getString("PhoneNumber"));
                    UserDetails.setBio(dataobj.getString("bio"));
                    UserDetails.setFollowers(dataobj.getString("followers"));
                    UserDetails.setFollowing(dataobj.getString("following"));
                    UserDetails.setTotal_likes(dataobj.getString("total_likes"));
                    UserDetails.setProfilePic(dataobj.getString("profile_pic"));
                    UserDetailsArrayList.add(UserDetails);
                    GlobalVariables.setBio(UserDetails.getBio());
                    GlobalVariables.setFullname(UserDetails.getFulllname());
                    GlobalVariables.setGender(UserDetails.getGender());
                    GlobalVariables.setPhonenumber(UserDetails.getPhoneNumber());
                    GlobalVariables.setProfile_pic(UserDetails.getProfilePic());
                    GlobalVariables.setId(UserDetails.getId());
                    GlobalVariables.setEmail(UserDetails.getEmail());
                    GlobalVariables.setDob(UserDetails.getDob());
                    GlobalVariables.setProfile_pic(UserDetails.getProfilePic());
                    GlobalVariables.setId(UserDetails.getId());
                    GlobalVariables.setFollwer(Integer.parseInt(UserDetails.getFollowers()));
                    GlobalVariables.setFollowing(Integer.parseInt(UserDetails.getFollowing()));
                    GlobalVariables.setLike(Integer.parseInt(UserDetails.getTotal_likes()));

                    id = dataobj.getString("id");
                    add_details();
                    Log.e(TAG, "writeTv: "+GlobalVariables.getFullname()+GlobalVariables.getUsername() );
                }



                for (int j = 0; j < UserDetailsArrayList.size(); j++){

//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                }
            }else {
                Toast.makeText(Profile_Activity.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    ///////////////////////


//    private void writeTv_following(String response){
//
//        try {
//            //getting the whole json object from the response
//            JSONObject obj = new JSONObject(response);
//            if(true){
//
//                ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
//                JSONArray dataArray  = obj.getJSONArray("body");
//
//                for (int i = 0; i < dataArray.length(); i++) {
//
//                    Following_Model following_model = new Following_Model();
//                    JSONObject dataobj = dataArray.getJSONObject(i);
//                    following_model.setFullname(dataobj.getString("fullname"));
//                    following_model.setProfilePic(dataobj.getString("profile_pic"));
//                    Log.e(TAG, "writeTv_following: "+following_model.getFullname());
//                    Log.e(TAG, "writeTv_following: "+following_model.getProfilePic());
//
//                    Following_Fragment following_fragment = new Following_Fragment();
//
//                    Log.e(TAG, "writeTv: "+GlobalVariables.getFullname()+GlobalVariables.getUsername() );
//                }
//
//                for (int j = 0; j < UserDetailsArrayList.size(); j++){
//
////                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
//                }
//            }else {
//                Toast.makeText(Profile_Activity.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    @SuppressLint("NewApi")
    private void add_details(){
        Log.e(TAG, "add_details: "+GlobalVariables.getProfile_pic() );
        Glide.with(this).load(String.valueOf(GlobalVariables.getProfile_pic())).into(pro_pic);
        pro_name.setText(GlobalVariables.getFullname());
        bio.setText(GlobalVariables.getBio());
        email.setText(GlobalVariables.getEmail());
        followers_text.setText(String.valueOf(GlobalVariables.getFollwer()));
        following_text.setText(String.valueOf(GlobalVariables.getFollowing()));
        likes_text.setText(String.valueOf(GlobalVariables.getLike()));
        GlobalVariables.suggestlist.add(GlobalVariables.getId());
        suggest();
        Log.e(TAG, "add_details: "+followers_text.getText()+following_text.getText()+likes_text.getText());



    }
    void suggest(){
        JSONObject data1 = new JSONObject();
        try {

            data1.put("username", GlobalVariables.getId());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getUserdetails_following(data1.toString());
        call.enqueue(new Callback<String>() {
            @SuppressLint("NewApi")
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

                                JSONArray dataArray  = obj.getJSONArray("body");
                                for (i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    GlobalVariables.suggestlist.add(dataobj.getString("id"));
                                    GlobalVariables.folglist.add(dataobj.getString("id"));
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

        JSONObject data = new JSONObject();
        try {

            data.put("username", GlobalVariables.suggestlist.toString());
            Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.suggestlist.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofitt = new Retrofit.Builder()
                .baseUrl(ApiInterface.userdetail_following_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface apii = retrofitt.create(ApiInterface.class);
        Call<String> calll = apii.getUserdetails_suggestion(data.toString());
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
                                    suggest_model.setUsernamee(dataobj.getString("fullname").toString());
                                    suggest_model.setProfile_picc(dataobj.getString("profile_pic").toString());
                                    suggest_model.setIdd(dataobj.getString("id").toString());

                                    suggestmodel.add(suggest_model);
                                    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                    suggest_rv.setLayoutManager(layoutManager3);
                                    Suggest_Adapter adapter = new Suggest_Adapter(getApplicationContext(),suggestmodel);
                                    suggest_rv.setAdapter(adapter);
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
    }







}