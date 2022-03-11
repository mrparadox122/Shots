package com.paradox.projectsp3.OthersProfiles;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paradox.projectsp3.Followers_Following_Likes.FollowersAdapter;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Model.UserDetails;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

        othersProfileModelList = new ArrayList<>();

        o_pro_pic = findViewById(R.id.o_pro_pic);
        o_following_ll = findViewById(R.id.o_following_ll);
        o_follower_ll = findViewById(R.id.o_follower_ll);
        o_recyclerview = findViewById(R.id.o_recyclerview);








        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        o_recyclerview.setLayoutManager(layoutManager4);
        othersProfileAdapter  = new OthersProfileAdapter(this,othersProfileModelList);
        o_recyclerview.setAdapter(othersProfileAdapter);

    }

    private void getResponse(){
        JSONObject data = new JSONObject();
        try {
            GlobalVariables globalVariables = new GlobalVariables();
            data.put("username", globalVariables.other_p_ud);
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


//                    id = dataobj.getString("id");
//                    add_details();
//                    Log.e(TAG, "writeTv: "+GlobalVariables.getFullname()+GlobalVariables.getUsername() );
                }

                for (int j = 0; j < UserDetailsArrayList.size(); j++){

//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                }
            }else {
                Toast.makeText(this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}