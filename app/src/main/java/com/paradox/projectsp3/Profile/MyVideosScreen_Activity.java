package com.paradox.projectsp3.Profile;

import static com.yalantis.ucrop.UCropFragment.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MyVideosScreen_Activity extends AppCompatActivity {



    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;

    public ImageView volumeControl;

    private RequestManager requestManager;

    ImageView like_image,comment_image,sound_image,share;
    private enum VolumeState {ON, OFF};
    private VolumeState volumeState;
    TextView like_txt,comment_txt,username,desc_txt,sound_name,shr_txt;
    Boolean checklike = true;
    int likesno;
    int shareno;
    VideoView videoView= (VideoView)findViewById(R.id.videov);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos_screen);
        getSupportActionBar().hide();


//        videoSurfaceView = (PlayerView) findViewById(R.id.player_view);

        like_image = findViewById(R.id.like_image);
        comment_image = findViewById(R.id.comment_image);

        share = findViewById(R.id.share);
        volumeControl = findViewById(R.id.imageView12);
        shr_txt = findViewById(R.id.shr_txt);
        like_txt = findViewById(R.id.like_txt);
        username = findViewById(R.id.username);
        videoSurfaceView = new PlayerView(this);

        comment_txt = findViewById(R.id.comment_txt);
        desc_txt = findViewById(R.id.desc_txt);

        sound_image = findViewById(R.id.sound_image);

        sound_name = findViewById(R.id.sound_name);



        like_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checklike)
                {
                    JSONObject dislike = new JSONObject();
                    try {
                        dislike.put("video_id", GlobalVariables.getVideoid());
                        dislike.put("flag", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit.Builder retrofit = new Retrofit.Builder()
                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit2 = retrofit.build();


                    //get client
                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                    Call<ResponseBody> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                            if (response.isSuccessful()){
                                likesno+=1;
                                like_txt.setText(String.valueOf(likesno));
                            }


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                        }
                    });
                    like_image.setImageResource(R.drawable.ic_icon_material_favorite_red);
                    checklike=false;
                }
                else if (!checklike)
                {
                    JSONObject dislike = new JSONObject();
                    try {
                        dislike.put("video_id", GlobalVariables.getVideoid());
                        dislike.put("flag", "4");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit.Builder retrofit = new Retrofit.Builder()
                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit2 = retrofit.build();

                    //get client
                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                    Call<ResponseBody> call_like = apiInterface.getStringScalar(dislike.toString());
                    call_like.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                likesno-=1;
                                Toast.makeText(getApplicationContext(), "//"+"disliked"+response, Toast.LENGTH_SHORT).show();
                                like_txt.setText(String.valueOf(likesno));
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                        }
                    });


                    like_image.setImageResource(R.drawable.ic_icon_material_favorite);
                    checklike=true;
                }
            }
        });


//        comment_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                Context context1= itemView.getContext();
//                Dialog dialog=new Dialog(context1);
//                dialog.setContentView(R.layout.activity_comment);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.show();
//            }
//
//        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context sharecontext= itemView.getContext();
//                Dialog dialog=new Dialog(sharecontext);
//                dialog.setContentView(R.layout.activity_share_bottom_sheet);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.show();

                JSONObject dislike = new JSONObject();
                try {
                    dislike.put("video_id", GlobalVariables.getVideoid());
                    dislike.put("flag", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Retrofit.Builder retrofit = new Retrofit.Builder()
                        .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit2 = retrofit.build();


                //get client
                ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                Call<ResponseBody> call_like = apiInterface.getStringScalar(dislike.toString());
                call_like.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()){
                            shareno+=1;
                            shr_txt.setText(String.valueOf(shareno));
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();

                    }
                });

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("video/mp4");
                String body = String.valueOf("");
                String sub = "Shots";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });


//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                // new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                new AdaptiveTrackSelection.Factory();
//        TrackSelector trackSelector =
//                new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        //videoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//        videoPlayer = new SimpleExoPlayer.Builder(this).build();
//
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
//        videoSurfaceView.setUseController(false);
//
//        setVolumeControl(VolumeState.ON);
        Uri vuri = Uri.parse(GlobalVariables.url);
//        try {
//
//
//            //DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_vid");
//
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
//                    this, Util.getUserAgent(this, "RecyclerView VideoPlayer"));
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(vuri, dataSourceFactory, extractorsFactory, null, null);
//            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(vuri);
//            videoSurfaceView.setPlayer(videoPlayer);
//            videoPlayer.prepare(videoSource);
//            videoPlayer.setPlayWhenReady(true);
//
//        }catch (Exception e){
//            Log.e(TAG, "onCreate: "+e.toString() );
//        }

        likesno = Integer.parseInt(GlobalVariables.getLikes());
        shareno = Integer.parseInt(GlobalVariables.getShares());
        Glide.with(this).load(R.drawable.editorlogo).into(sound_image);


//        Uri uri = Uri.parse(TEST_URL);
        videoView.setVideoURI(vuri);
        videoView.requestFocus();
        videoView.start();

        like_txt.setText(GlobalVariables.getLikes());
        comment_txt.setText(GlobalVariables.getComments());
        desc_txt.setText(GlobalVariables.getDescription());
        shr_txt.setText(GlobalVariables.getShares());
    }



    private void setVolumeControl(VolumeState state){

        volumeState = state;
        if(state == VolumeState.OFF){
            videoView.pause();
            videoPlayer.pause();
            animateVolumeControl();
        }
        else if(state == VolumeState.ON){
            videoView.resume();
            videoPlayer.play();
            animateVolumeControl();
        }
    }

    private void animateVolumeControl(){
        if(volumeControl != null){
            volumeControl.bringToFront();
            if(volumeState == VolumeState.OFF){
                requestManager.load(R.drawable.pause_video)
                        .into(volumeControl);
            }
            else if(volumeState == VolumeState.ON){
                requestManager.load(R.drawable.video_play)
                        .into(volumeControl);
            }
            volumeControl.animate().cancel();

            volumeControl.setAlpha(1f);

            volumeControl.animate()
                    .alpha(0f)
                    .setDuration(600).setStartDelay(1000);
        }

    }


}