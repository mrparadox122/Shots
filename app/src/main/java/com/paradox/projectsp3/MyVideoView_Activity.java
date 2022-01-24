package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;
import static com.paradox.projectsp3.Responses.ApiClient.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
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
import com.gowtham.library.utils.FileUtils;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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

    ImageView imageview,back;
    int SELECT_IMAGE_CODE =1;
    Button btn_image;
    Uri imageuri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_video_view);

        btn_video = findViewById(R.id.btn_video);
        v_video = findViewById(R.id.v_video);

        mc = new MediaController(MyVideoView_Activity.this);
        v_video.setMediaController(mc);
        btn_image = findViewById(R.id.btn_image);
        imageview = findViewById(R.id.imageview);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyVideoView_Activity.this,HomeActivty.class);
                startActivity(intent);
            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),1);
            }
        });


        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,2);
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            imageuri = data.getData();
            imageview.setImageURI(imageuri);
            btn_image.setText("Image Uploaded");
        }
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
            File file = new File(getPath(vediouri));
            File f = new File(getPath(vediouri));


//Convert bitmap to byte array
            try {
                bitmap = retriveVideoFrameFromVideo(getPath(vediouri));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            RequestBody descriptionPart = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

            RequestBody filepart = RequestBody.create(MediaType.parse("video/mp4"),file);
            RequestBody filepart1 = RequestBody.create(MediaType.parse("image/*"),f);

             MultipartBody.Part file1= MultipartBody.Part.createFormData("video",file.getName(),filepart);
             MultipartBody.Part file2= MultipartBody.Part.createFormData("thumbnail",f.getName(),filepart1);

            ApiInterface service = retrofit.create(ApiInterface.class);

            Call<Users> call = service.upload(descriptionPart,file1,file2);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Log.i("mok","S");


                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Log.i("mok","F");

                                 Log.i("mok",t.getCause()+"");
                                 Log.i("mok","T");
                                 finish();

                }
            });




//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("text/plain");
//            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("video","VID-20220103-WA0020.mp4",
//                            RequestBody.create(MediaType.parse("video/*"),
//                                    new File(vediouri.getPath())))
//                    .addFormDataPart("thumbnail","",
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    new File("")))
//                    .addFormDataPart("data", null,
//                            RequestBody.create(MediaType.parse("application/json"), jsonObject.toString().getBytes()))
//                    .build();
//            Request request = new Request.Builder()
//                    .url("http://13.127.217.99:8080/soosleApi/soosle/upload")
//                    .method("POST", body)
//                    .build();
//
//
//            ApiInterface service = retrofit.create(ApiInterface.class);
//            MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
//            Log.e(TAG, "onActivityResult: "+MEDIA_TYPE.toString());
//            File file = new File(vediouri.getPath());
//            RequestBody requestBody = RequestBody.create(MEDIA_TYPE,file);
//            Call<Users> call = service.upload(requestBody);
//            call.enqueue(new Callback<Users>() {
//                             @Override
//                             public void onResponse(Call<Users> call, Response<Users> response) {
//                                 Log.i("mok","S");
//
//
//
//
//
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<Users> call, Throwable t) {
//
//                                 Log.i("mok","F");
//                                 t.printStackTrace();
//                                 Log.i("mok",t.getCause()+"");
//                                 Log.i("mok","T");
//                                 finish();
//
//                             }
//            });

                    v_video.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Video Uploaded",Toast.LENGTH_SHORT).show();
            v_video.setVideoURI(vediouri);
            setTitle("Video Uploaded");
            v_video.start();
            btn_video.setText("Vedio Uploaded");
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

//            v_video.setVisibility(View.VISIBLE);
//            v_video.setVideoURI(videouri);
//            v_video.start();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MyVideoView_Activity.this,HomeActivty.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideDown(this);
        finish();

    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


}