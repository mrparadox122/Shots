package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import static com.google.android.gms.vision.L.TAG;

public class PickVeideo_Activity extends AppCompatActivity {

    Button btn_video;
    VideoView v_video;
    MediaController mc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pick_veideo);

        btn_video = findViewById(R.id.btn_video);
        v_video = findViewById(R.id.v_video);

        mc = new MediaController(PickVeideo_Activity.this);
        v_video.setMediaController(mc);

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PickVeideo_Activity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){
            Log.e(TAG, "onActivityResult: ////////////////////////////as/fafefaws/////////////////" );
            Uri vediouri = data.getData();
            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(vediouri);
            v_video.start();
            btn_video.setText("Vnjm n");
            btn_video.setBackgroundColor(R.color.teal_200);
            Log.e(TAG, "onActivityResult: ////////////////////////////as/fafefaws/////////////////" );
        }
//        if (requestCode == 1){
//
//            Uri videouri = data.getData();
//
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("description", "shots");
//                jsonObject.put("user_id", "468");
//                jsonObject.put("category_id","2");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("text/plain");
//            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("video","VID-20220103-WA0020.mp4",
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    new File(String.valueOf(videouri))))
//                    .addFormDataPart("thumbnail","",
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    new File("")))
//                    .addFormDataPart("data", null,
//                            RequestBody.create(MediaType.parse("application/json"), jsonObject.toString().getBytes()))
//                .build();
//        Request request = new Request.Builder()
//                .url("http://13.127.217.99:8080/soosleApi/soosle/upload")
//                .method("POST", body)
//                .build();
//            Call<ResponseBody> call = ApiClient.
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Log.e(TAG, "onResponse: //////////////////////////////////////////////" );
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e(TAG, "onFailure: ////////////////////////////////////////////////" );
//                }
//            });
//
//            v_video.setVisibility(View.VISIBLE);
//            v_video.setVideoURI(videouri);
//            v_video.start();
//        }

    }

}