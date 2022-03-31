package com.paradox.projectsp3.VideoRecording;

import static android.content.ContentValues.TAG;
import static com.paradox.projectsp3.Responses.ApiClient.retrofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paradox.projectsp3.Functions;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.MyVideoView_Activity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.ServiceCallback;
import com.paradox.projectsp3.Responses.Upload_Service;
import com.paradox.projectsp3.Responses.Users;
import com.paradox.projectsp3.Variables;
import com.paradox.projectsp3.VideoTrimmerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_Video_A extends AppCompatActivity implements ServiceCallback, View.OnClickListener {


    ImageView video_thumbnail;
    String video_path;
    ServiceCallback serviceCallback;
    EditText description_edit;
    String draft_file;

    TextView privcy_type_txt;

    Switch comment_switch;

    private Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video2);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        VideoTrimmerActivity videoTrimmerActivity = new VideoTrimmerActivity();
        if(intent!=null){
            draft_file=intent.getStringExtra("draft_file");
        }

        video_path = videoTrimmerActivity.dstFile;
        video_thumbnail = findViewById(R.id.video_thumbnail);
        description_edit=findViewById(R.id.description_edit);

        if (video_path!=null){
            Glide
                    .with(context)
                    .asBitmap()
                    .load(Uri.fromFile(new File(video_path)))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(video_thumbnail);

        }

        privcy_type_txt=findViewById(R.id.privcy_type_txt);
        comment_switch=findViewById(R.id.comment_switch);

        comment_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });

        findViewById(R.id.Goback).setOnClickListener(this);
        findViewById(R.id.privacy_type_layout).setOnClickListener(this);
        findViewById(R.id.post_bt).setOnClickListener(this);
        findViewById(R.id.save_draft_btn).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.Goback:
                onBackPressed();
                break;

            case R.id.privacy_type_layout:
                privacy_dialog();
                break;

            case R.id.save_draft_btn:
                save_file_in_draft();
                break;

            case R.id.post_bt:
                start_Service();
                break;
        }



    }


    private void privacy_dialog() {
        final CharSequence[] options = new CharSequence[]{"Public","Friend","Private"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);

        builder.setTitle(null);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                privcy_type_txt.setText(options[item]);
            }
        });
        builder.show();
    }
    // this will start the service for uploading the video into database
    public void start_Service(){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.e(TAG, "onActivityResult: "+ GlobalVariables.getId() );

                    if (GlobalVariables.getId() != null && GlobalVariables.getId()!= "") {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("description", "shots");
                            jsonObject.put("user_id", GlobalVariables.getId());
                            jsonObject.put("category_id","2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestBody descriptionPart = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                        RequestBody filepart = RequestBody.create(MediaType.parse("video/mp4"),Variables.output_filter_file);
                        RequestBody filepart1 = RequestBody.create(MediaType.parse("image/*"),Variables.output_filter_file);

                        MultipartBody.Part file1= MultipartBody.Part.createFormData("video",Variables.output_filter_file,filepart);
                        MultipartBody.Part file2= MultipartBody.Part.createFormData("thumbnail",Variables.output_filter_file+".jpg",filepart1);

                        ApiInterface service = retrofit.create(ApiInterface.class);

                        Call<Users> call = service.upload(descriptionPart,file1,file2);
                        call.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                Log.i("mok","S");
                                Toast.makeText(getApplicationContext(),"Video Uploaded",Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {
                                Log.i("mok","F");

                                Log.i("mok",t.getCause()+"");
                                Log.i("mok","T");
                                Toast.makeText(getApplicationContext(), t+t.getCause().toString()+t.getMessage(), Toast.LENGTH_SHORT).show();
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


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "login to upload", Toast.LENGTH_SHORT).show();
                    }
                    sendBroadcast(new Intent("uploadVideo"));
                    startActivity(new Intent(Post_Video_A.this, HomeActivty.class));
                }
            },1000);


        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


    // when the video is uploading successfully it will restart the appliaction
    @Override
    public void showResponce(final String responce) {

        if(mConnection!=null)
            unbindService(mConnection);
        Toast.makeText(this, responce, Toast.LENGTH_SHORT).show();

    }


    // this is importance for binding the service to the activity
    Upload_Service mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            Upload_Service.LocalBinder binder = (Upload_Service.LocalBinder) service;
            mService = binder.getService();

            mService.setCallbacks(Post_Video_A.this);

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void save_file_in_draft(){
        File source = new File(video_path);
        File destination = new File(Variables.draft_app_folder+Functions.getRandomString()+".mp4");
        try
        {
            if(source.exists()){
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(destination);
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();

                Toast.makeText(Post_Video_A.this, "File saved in Draft", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Post_Video_A.this, HomeActivty.class));

            }else{
                Toast.makeText(Post_Video_A.this, "File failed to saved in Draft", Toast.LENGTH_SHORT).show();

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}