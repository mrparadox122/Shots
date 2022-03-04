package com.paradox.projectsp3.Profile;

import static com.yalantis.ucrop.UCropFragment.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.R;


public class MyVideosScreen_Activity extends AppCompatActivity {



    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;

    public ImageView volumeControl;

    private RequestManager requestManager;

    ImageView like_image,comment_image,sound_imag,share;
    private enum VolumeState {ON, OFF};
    private VolumeState volumeState;
    TextView like_txt,comment_txt,username,desc_txt,sound_name,shr_txt;

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

        sound_imag = findViewById(R.id.imageView3);

        sound_name = findViewById(R.id.sound_name);


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

        Glide.with(this).load(R.drawable.editorlogo).into(sound_imag);

        VideoView videoView= (VideoView)findViewById(R.id.videov);
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
            videoPlayer.pause();
            animateVolumeControl();
        }
        else if(state == VolumeState.ON){
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