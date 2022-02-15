package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.paradox.projectsp3.Model.MediaObject;
import com.paradox.projectsp3.Model.UserDetails;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile_Activity extends AppCompatActivity {

   ImageView settings , back;
   CircleImageView pro_pic;
   TextView pro_name,email,bio;
   Button edit_profile;
   private ArrayList<UserDetails> userDetailsArrayList = new ArrayList<>();
   public static ApiInterface apiInterface;
   List<UserDetails> userDetails;

   TextView following_text,followers_text,likes_text,suggested_text;

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollwer() {
        return follwer;
    }

    public void setFollwer(String follwer) {
        this.follwer = follwer;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    String following;
    String follwer;
    String like;
    String suggest;

    UserDetails UserDetails = new UserDetails();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
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
        suggested_text = findViewById(R.id.suggested_text);




        apiInterface = ApiClient.getUserDetails().create(ApiInterface.class);
        userDetails = new ArrayList<>();


        LoadAllDetails();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this,HomeActivty.class);
                startActivity(intent);
              }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this,ProfileSettings_Activity.class);
                startActivity(intent);
            }
        });

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
            Glide.with(this).load(String.valueOf(personPhoto)).into(pro_pic);
        }

    }


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
            Log.e(TAG, "getResponse:json data put for api "+globalVariables.getUsername());
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
                    UserDetailsArrayList.add(UserDetails);
                    GlobalVariables.setBio(UserDetails.getBio());
                    GlobalVariables.setFullname(UserDetails.getFulllname());
                    GlobalVariables.setGender(UserDetails.getGender());
                    GlobalVariables.setPhonenumber(UserDetails.getPhoneNumber());
                    GlobalVariables.setProfile_pic(UserDetails.getProfilePic());
                    GlobalVariables.setId(UserDetails.getId());
                    GlobalVariables.setEmail(UserDetails.getEmail());
                    GlobalVariables.setDob(UserDetails.getDob());
                    setFollowing(UserDetails.getFollowing());
                    setFollwer(UserDetails.getFollowers());
                    setLike(UserDetails.getTotal_likes());
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
    private void add_details(){
        bio.setText(GlobalVariables.getBio());
        email.setText(GlobalVariables.getEmail());
        followers_text.setText(getFollwer());
        following_text.setText(getFollowing());
        likes_text.setText(getLike());
        Log.e(TAG, "add_details: "+followers_text.getText()+following_text.getText()+likes_text.getText() );
        suggested_text.setText(suggest);
    }
}