package com.paradox.projectsp3.Followers_Following_Likes;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.NewEditProfile_Activity;
import com.paradox.projectsp3.OthersProfiles.OthersProfile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.myviewHolder> {


    Context context;
    List<Following_Model> followingModels = new ArrayList<>();
    ImageView pic_img;
    TextView txt_name;




    public FollowingAdapter(Context context, List<Following_Model> followingModels) {
        this.context = context;
        this.followingModels = followingModels;
        Log.e(TAG, "FollowingAdapter: "+followingModels.get(0) );
//        Log.e(TAG, "FollowingAdapter: "+followingModels.get(1) );

    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_following, parent, false);
        return new FollowingAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, @SuppressLint("RecyclerView") int position) {



        //Log.e(TAG, "onBindViewHolder: "+Entries.get(0).getUsername() );
        Log.e(TAG, "onBindViewHolder: "+followingModels.size() );
        holder.txt_name.setText(String.valueOf(followingModels.get(position).getUsername()));
        Glide.with(context).load(followingModels.get(position).getProfile_pic()).into(holder.pic_img);
        holder.txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables globalVariables = new GlobalVariables();
                globalVariables.Setus_id_ofr_singl_vid(followingModels.get(position).getId());
                Intent intent = new Intent(context, OthersProfile_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.pic_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables globalVariables = new GlobalVariables();
                globalVariables.Setus_id_ofr_singl_vid(followingModels.get(position).getId());
                Intent intent = new Intent(context, OthersProfile_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



        holder.btn_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, followingModels.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
                holder.ll_relative.setVisibility(View.GONE);
                Retrofit.Builder retrofit = new Retrofit.Builder()
                        .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit2 = retrofit.build();
                MultipartBody.Part idmy= MultipartBody.Part.createFormData("idmy",GlobalVariables.getId().toString());
                MultipartBody.Part idpr= MultipartBody.Part.createFormData("idpr",followingModels.get(position).getId().toString());
                MultipartBody.Part flag= MultipartBody.Part.createFormData("flag","2");

                //get client
                ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                Call<String> call_like = apiInterface.unfollo_follo(idmy,idpr,flag);
                call_like.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()){
                            Log.e(TAG, "onResponse: "+response.body() );
                            Log.e(TAG, "onResponse: "+response );
                            if (response.body().equals("s")){
                                Toast.makeText(context, "unfollow", Toast.LENGTH_SHORT).show();
                            }
//                            GlobalVariables.setBio(String.valueOf(editphone_et.getText()));
//                            phonenumber_txt.setText(String.valueOf(editphone_et.getText()));
//                            Toast.makeText(NewEditProfile_Activity.this, "Successfully changed the number to: "+GlobalVariables.getPhonenumber(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, "/"+t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


    }

    @Override
    public int getItemCount() {

        return followingModels.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView txt_name;
        Button btn_following;
        RelativeLayout ll_relative;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            pic_img = itemView.findViewById(R.id.pic_img);
            txt_name = itemView.findViewById(R.id.txt_name);
            btn_following = itemView.findViewById(R.id.btn_following);
            ll_relative = itemView.findViewById(R.id.ll_relative);


        }


    }
}
