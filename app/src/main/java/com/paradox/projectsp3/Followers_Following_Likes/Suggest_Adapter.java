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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.OthersProfiles.OthersProfile_Activity;
import com.paradox.projectsp3.Profile_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Suggest_Adapter extends RecyclerView.Adapter<Suggest_Adapter.myviewHolder> {

    List<Suggest_Model> suggestmodel = new ArrayList<>();
    Context context;

    public Suggest_Adapter( Context context,List<Suggest_Model> suggestmodel) {
        this.suggestmodel = suggestmodel;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_suggest, parent, false);
        return new Suggest_Adapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name_txt.setText(suggestmodel.get(position).getUsernamee());
        Glide.with(context).load(suggestmodel.get(position).getProfile_picc()).into(holder.img_pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables globalVariables = new GlobalVariables();
                globalVariables.Setus_id_ofr_singl_vid(suggestmodel.get(position).getIdd());
                Intent intent = new Intent(context, OthersProfile_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.btn_sug_fol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardView.setVisibility(View.GONE);
                Retrofit.Builder retrofit = new Retrofit.Builder()
                        .baseUrl("http://shotsparadox.ddns.net/dashboard/paradoxApi/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit2 = retrofit.build();
                MultipartBody.Part idmy= MultipartBody.Part.createFormData("idmy", GlobalVariables.getId().toString());
                MultipartBody.Part idpr= MultipartBody.Part.createFormData("idpr",suggestmodel.get(position).getIdd().toString());
                MultipartBody.Part flag= MultipartBody.Part.createFormData("flag","1");

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
                                Toast.makeText(context, "followed", Toast.LENGTH_SHORT).show();
//                                Profile_Activity profile_activity = new Profile_Activity();
//                                TextView follin= profile_activity.following_text = view.findViewById(R.id.following_text);
//                                follin.setText(String.valueOf(GlobalVariables.getFollowing()+1));
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

    }

    @Override
    public int getItemCount() {
        return suggestmodel.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView img_pic;
        TextView name_txt;
        TextView btn_sug_fol;
        CardView cardView;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            img_pic = itemView.findViewById(R.id.img_pic);
            name_txt = itemView.findViewById(R.id.name_txt);
            btn_sug_fol = itemView.findViewById(R.id.btn_sug_fol);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
