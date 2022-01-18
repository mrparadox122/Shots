package com.paradox.projectsp3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class PickImage_Activity extends AppCompatActivity {

    ImageView imageview;
    int SELECT_IMAGE_CODE =2;
    Button btn_image,btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pick_image);

        btn_image = findViewById(R.id.btn_image);
        imageview = findViewById(R.id.imageview);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickImage_Activity.this,PickVeideo_Activity.class);
                startActivity(intent);
            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PickImage_Activity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==2){
            Uri imageuri = data.getData();
            imageview.setImageURI(imageuri);
            btn_image.setText("Image Uploaded");
            btn_image.setBackgroundColor(R.color.teal_200);
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