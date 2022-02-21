package com.paradox.projectsp3.Followers_Following_Likes;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Following_Fragment extends Fragment {

    public String id;
    RecyclerView rv_following;
    ArrayList<Following_Model> followingdata = new ArrayList<Following_Model>();
    FollowingAdapter followingAdapter;

    public Following_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_, container, false);
        initviews(view);
        return view;
    }

    private void initviews(View view) {

        rv_following = view.findViewById(R.id.rv_following);
        followingAdapter = new FollowingAdapter(getContext(),followingdata,this);
        rv_following.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rv_following.setAdapter(followingAdapter);

    }


    public void writeTv_following(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(true){

                ArrayList<Following_Model> UserDetailsArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("body");


                for (int i = 0; i < dataArray.length(); i++) {
                    Following_Model following_model = new Following_Model();
                    JSONObject dataobj = dataArray.getJSONObject(i);
//                    followingdata.add(dataArray.get(i));
                    following_model.setFullname(dataobj.getString("fullname"));
                    following_model.setProfilePic(dataobj.getString("profile_pic"));

                    Log.e(TAG, "writeTv_following: "+following_model.getFullname());
                    Log.e(TAG, "writeTv_following: "+following_model.getProfilePic());
                    Log.e(TAG, "writeTv: "+ GlobalVariables.getFullname()+GlobalVariables.getUsername() );
                }

                for (int j = 0; j < UserDetailsArrayList.size(); j++){

//                    Log.e(TAG, "writeTv: "+ userDetailsArrayList.get(j));
                }
            }else {
                Toast.makeText(getContext(), obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}