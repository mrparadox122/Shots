package com.paradox.projectsp3.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.paradox.projectsp3.Followers_Following_Likes.Following_Model;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.Model.My_VideosModel;
import com.paradox.projectsp3.Profile.MyVideosScreen_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

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

public class MyVideos_Adapter extends RecyclerView.Adapter<MyVideos_Adapter.myviewHolder> {

    Context context;
    List<My_VideosModel>provedio;
    int i;
//    GlobalVariables g = new GlobalVariables();


    public MyVideos_Adapter(Context context, List<My_VideosModel> provedio) {
        this.context = context;
        this.provedio = provedio;
    }

    @NonNull
    @Override
    public MyVideos_Adapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myvideo_layout,parent,false);
        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyVideos_Adapter.myviewHolder holder, @SuppressLint("RecyclerView") int position) {
        context = holder.itemView.getContext();
        holder.view_txt.setText(provedio.get(position).getViewtext());

        Glide.with(context).load(provedio.get(position).getImg_url()).into(holder.thumb_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                JSONObject dataa = new JSONObject();
                try {
                    GlobalVariables globalVariables = new GlobalVariables();
                    globalVariables.Setus_id_ofr_singl_vid(GlobalVariables.getId());

                    dataa.put("post_id",provedio.get(position).getPid());
                    dataa.put("video_id",provedio.get(position).getVideoid());
                    Log.e(TAG, "getResponse:json data put for api ///////////////" + GlobalVariables.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Retrofit retrofittt = new Retrofit.Builder()
                        .baseUrl(ApiInterface.userdetail_following_url)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();

                ApiInterface apiii = retrofittt.create(ApiInterface.class);
                Call<String> callll = apiii.getUserdetails_video(dataa.toString());
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
//                                            My_VideosModel my_videosModel = new My_VideosModel();
//                                    Suggest_Model suggest_model = new Suggest_Model();
//                                    suggest_model.setUsernamee(dataobj.getString("fullname").toString());
//                                    suggest_model.setProfile_picc(dataobj.getString("profile_pic").toString());
//                                    suggest_model.setIdd(dataobj.getString("id").toString());
//                                            my_videosModel.setImg_url(dataobj.getString("url"));
//                                            my_videosModel.setViewtext(dataobj.getString("views"));
//                                            my_videosModel.setVideoid(dataobj.getString("videoid"));
//                                            my_videosModel.setPid(dataobj.getString("post_id"));
                                            GlobalVariables.setUrl(dataobj.getString("url"));
                                            GlobalVariables.setViews(dataobj.getString("views"));
                                            GlobalVariables.setVideoid(dataobj.getString("videoid"));
                                            GlobalVariables.setPost_id(dataobj.getString("post_id"));
                                            GlobalVariables.setDescription(dataobj.getString("description"));
                                            GlobalVariables.setCategories(dataobj.getString("categories"));
                                            GlobalVariables.setLikes(dataobj.getString("likes"));
                                            GlobalVariables.setShares(String.valueOf(dataobj.getString("shares ").toString()));
                                            GlobalVariables.setComments(dataobj.getString("comments"));
                                            Intent intent = new Intent(context, MyVideosScreen_Activity.class);
                                            context.startActivity(intent);

//                                            //suggestmodel.add(suggest_model);
//                                            videomodle.add(my_videosModel);
//
//                                            recyclerview.setLayoutManager(new GridLayoutManager(context,3));
//
//                                            myVideos_adapter = new MyVideos_Adapter(context,videomodle);
//                                            recyclerview.setAdapter(myVideos_adapter);

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
                                        Toast.makeText(context, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }

                });




            }
        });



    }

    @Override
    public int getItemCount() {
        return provedio.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {


        ImageView thumb_image;
        TextView view_txt;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            thumb_image = itemView.findViewById(R.id.thumb_image);
            view_txt = itemView.findViewById(R.id.view_txt);
        }
    }
}
