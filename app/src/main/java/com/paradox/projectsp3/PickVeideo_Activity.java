package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import static com.paradox.projectsp3.Responses.ApiClient.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.common.api.Api;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){
            Uri vediouri = data.getData();

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
                            RequestBody.create(MediaType.parse("video/*"),
                                    new File(vediouri.getPath())))
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
//            File file = new File(vediouri.getPath());
//
//                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//
//            Call<Users> call = ApiInterface.upload(body);
//            call.enqueue(new Callback<Users>() {
//                @Override
//                public void onResponse(Call<Users> call, Response<Users> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<Users> call, Throwable t) {
//
//                }
//            });



//            Call<ResponseBody> call = ApiInterface.upload(body);
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

            ApiInterface service = retrofit.create(ApiInterface.class);
            MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
            Log.e(TAG, "onActivityResult: "+MEDIA_TYPE.toString());
            File file = new File(vediouri.getPath());
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE,file);
            Call<ResponseBody> call = service.upload(requestBody);
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

            Toast.makeText(this, getRealPathFromURI(this,vediouri), Toast.LENGTH_LONG).show();




            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(vediouri);
            v_video.start();
            btn_video.setText("Vedio Uploaded");
            btn_video.setBackgroundColor(R.color.teal_200);
        }
        if (requestCode == 1){

            Uri videouri = data.getData();



            v_video.setVisibility(View.VISIBLE);
            v_video.setVideoURI(videouri);
            v_video.start();
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