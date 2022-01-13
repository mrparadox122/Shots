package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyVideoView_Activity extends AppCompatActivity {


    Button btn_video;
    VideoView v_video;
    MediaController mc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_view);


        btn_video = findViewById(R.id.btn_video);
        v_video = findViewById(R.id.v_video);

        mc = new MediaController(MyVideoView_Activity.this);
        v_video.setMediaController(mc);

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,1);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

            Uri videouri = data.getData();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("description", "shots");
                jsonObject.put("user_id", "468");
                jsonObject.put("category_id","2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("video","VID-20220103-WA0020.mp4",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(String.valueOf(videouri))))
                    .addFormDataPart("thumbnail","",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File("")))
                    .addFormDataPart("data", null,
                            RequestBody.create(MediaType.parse("application/json"), jsonObject.toString().getBytes()))
                .build();
        Request request = new Request.Builder()
                .url("http://13.127.217.99:8080/soosleApi/soosle/upload")
                .method("POST", body)
                .build();
            Call<ResponseBody> call = ApiClient.
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e(TAG, "onResponse: //////////////////////////////////////////////" );
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "onFailure: ////////////////////////////////////////////////" );
                }
            });



            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(videouri);
            v_video.start();
        }

    }
}