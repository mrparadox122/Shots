package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;
import static com.paradox.projectsp3.Responses.ApiClient.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyVideoView_Activity extends AppCompatActivity {

    Button btn_video,btn_image;
    VideoView v_video;
    MediaController mc;
    ImageView imageview;
    int SELECT_IMAGE_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_video_view);

        btn_video = findViewById(R.id.btn_video);
        btn_image = findViewById(R.id.btn_image);
        v_video = findViewById(R.id.v_video);
        imageview = findViewById(R.id.imageview);

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

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"title"),SELECT_IMAGE_CODE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){


            Uri videouri = data.getData();
            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(videouri);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("description", "shots");
                jsonObject.put("user_id", "468");
                jsonObject.put("category_id","2");
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Unirest.setTimeouts(0, 0);
//            try {
//                HttpResponse<String> response = Unirest.post("http://13.127.217.99:8080/soosleApi/soosle/upload")
//                        .field("video", new File(getRealPathFromURI(this,videouri)))
//                        .field("thumbnail", new File(""))
//                        .field("data", jsonObject).asString();
//
//            } catch (UnirestException e) {
//                e.printStackTrace();
//            }
            Uri imageuri = data.getData();
            ApiInterface service = retrofit.create(ApiInterface.class);
            MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
            Log.e(TAG, "onActivityResult: "+MEDIA_TYPE.toString());
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE,getRealPathFromURI(this,videouri));
            Call<ResponseBody> call = service.postUser(requestBody,jsonObject.toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // TODO Auto-generated method stub

                        Log.i("mok","S");

                        ResponseBody rb = response.body();




                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("mok","F");
                    t.printStackTrace();
                    Log.i("mok",t.getCause()+"");
                    Log.i("mok","T");
                    finish();



                }
            });

            Toast.makeText(this, getRealPathFromURI(this,videouri), Toast.LENGTH_LONG).show();



            v_video.start();
        }
        if (requestCode == 2){
            Uri imageuri = data.getData();
            imageview.setImageURI(imageuri);
            Toast.makeText(this, String.valueOf(imageuri), Toast.LENGTH_LONG).show();
        }


    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MyVideoView_Activity.this,HomeActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }

}