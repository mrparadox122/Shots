package com.paradox.projectsp3.VideoRecording;

import static com.yalantis.ucrop.UCropFragment.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daasuu.gpuv.composer.GPUMp4Composer;
import com.daasuu.gpuv.egl.filter.GlFilterGroup;
import com.daasuu.gpuv.player.GPUPlayerView;
import com.daasuu.gpuv.player.PlayerScaleType;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.paradox.projectsp3.Filters.Filter_Adapter;
import com.paradox.projectsp3.Functions;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Variables;
import com.paradox.projectsp3.VideoEditorFolder.FilterType;
import com.paradox.projectsp3.VideoTrimmerActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Preview_Video_A extends AppCompatActivity implements Player.EventListener {



    private static final int REQUEST_ID_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 100;
    private static final int VIDEO_TRIM = 101;


    String video_url;
    GPUPlayerView gpuPlayerView;
    public static int select_postion = 0;
    final List<FilterType> filterTypes = FilterType.createFilterList();
    Filter_Adapter adapter;
    RecyclerView recylerview;

    String draft_file;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_videooo);
        select_postion = 0;
        Intent intent = getIntent();
        if (intent != null) {
            draft_file = intent.getStringExtra("draft_file");
        }


        if (Variables.video_flipped) {
            flipVideoHorizontal();

        } else {

            video_url = Variables.outputfile2;
            findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (select_postion == 0) {
                        try {
                            Functions.copyFile(new File(Variables.outputfile2),
                                    new File(Variables.output_filter_file));
                            gotopostScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(Variables.tag, e.toString());
                            save_Video(Variables.outputfile2, Variables.output_filter_file);
                        }
                    } else
                        save_Video(Variables.outputfile2, Variables.output_filter_file);
                }
            });

            set_Player(video_url);


        }


        findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        recylerview = findViewById(R.id.recylerview);


        adapter = new Filter_Adapter(this, filterTypes, new Filter_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, FilterType item) {
                select_postion = postion;
                gpuPlayerView.setGlFilter(FilterType.createGlFilter(filterTypes.get(postion), getApplicationContext()));
                adapter.notifyDataSetChanged();
            }
        });
        recylerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recylerview.setAdapter(adapter);
    }

    private void flipVideoHorizontal() {
        Functions.show_determinent_loader(this, false, false);
        new GPUMp4Composer(Variables.outputfile2, Variables.outputflipped2)
                .flipHorizontal(true)
                .listener(new GPUMp4Composer.Listener() {
                    @Override
                    public void onProgress(double progress) {

                        Log.d("resp", "" + (int) (progress * 100));
                        Functions.show_loading_progress((int) (progress * 100));
                    }

                    @Override
                    public void onCompleted() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Functions.cancel_determinent_loader();
                                Toast.makeText(Preview_Video_A.this, "Flipped video", Toast.LENGTH_SHORT).show();

                                set_Player(Variables.outputflipped2);

                                findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (select_postion == 0) {
                                            try {
                                                Functions.copyFile(new File(Variables.outputflipped2),
                                                        new File(Variables.output_filter_file));
                                                gotopostScreen();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                Log.d(Variables.tag, e.toString());
                                                save_Video(Variables.outputflipped2, Variables.output_filter_file);
                                            }
                                        } else
                                            save_Video(Variables.outputflipped2, Variables.output_filter_file);
                                    }
                                });
                            }
                        });


                    }

                    @Override
                    public void onCanceled() {
                        Log.d("resp", "onCanceled");
                    }

                    @Override
                    public void onFailed(Exception exception) {

                        Log.d("resp", exception.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Functions.cancel_determinent_loader();

                                    Toast.makeText(Preview_Video_A.this, "Try Again", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {

                                }
                            }
                        });

                    }
                })
                .start();


    }


    // this function will set the player to the current video
    SimpleExoPlayer player;

    public void set_Player(String path) {

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(context);
        player = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "TikTok"));

        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(path));

        player.prepare(videoSource);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.addListener(this);
        player.setPlayWhenReady(true);

        gpuPlayerView = new GPUPlayerView(this);

        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                Log.d(Variables.tag, width + " " + height);
                if (width > height) {
                    gpuPlayerView.setPlayerScaleType(PlayerScaleType.RESIZE_FIT_WIDTH);
                } else
                    gpuPlayerView.setPlayerScaleType(PlayerScaleType.RESIZE_NONE);
            }
        });

        gpuPlayerView.setSimpleExoPlayer(player);
        gpuPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ((MovieWrapperView) findViewById(R.id.layout_movie_wrapper)).addView(gpuPlayerView);

        gpuPlayerView.onResume();

    }

    // this is lifecyle of the Activity which is importent for play,pause video or relaese the player
    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(false);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (player != null) {
            player.setPlayWhenReady(true);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (player != null) {
            player.setPlayWhenReady(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.removeListener(Preview_Video_A.this);
            player.release();
            player = null;
        }
    }

    // this function will add the filter to video and save that same video for post the video in post video screen
    public void save_Video(String srcMp4Path, final String destMp4Path) {

        Functions.show_determinent_loader(this, false, false);
        new GPUMp4Composer(srcMp4Path, destMp4Path)
                .filter(new GlFilterGroup(FilterType.createGlFilter(filterTypes.get(select_postion), getApplicationContext())))
                .listener(new GPUMp4Composer.Listener() {
                    @Override
                    public void onProgress(double progress) {

                        Log.d("resp", "" + (int) (progress * 100));
                        Functions.show_loading_progress((int) (progress * 100));

                    }

                    @Override
                    public void onCompleted() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Functions.cancel_determinent_loader();
                                gotopostScreen();

                            }
                        });

                    }

                    @Override
                    public void onCanceled() {
                        Log.d("resp", "onCanceled");
                    }

                    @Override
                    public void onFailed(Exception exception) {

                        Log.d("resp", exception.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Functions.cancel_determinent_loader();

                                    Log.e(TAG, exception.toString() );
                                    Toast.makeText(Preview_Video_A.this, "Try Again", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {

                                    Log.e(TAG, e.toString() );
                                }
                            }
                        });

                    }
                })
                .start();


    }


    public void gotopostScreen() {
        String path = Variables.output_filter_file;
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                startActivityForResult(new Intent(Preview_Video_A.this,
                                VideoTrimmerActivity.class).putExtra("EXTRA_PATH", path),
                        VIDEO_TRIM);
                overridePendingTransition(0, 0);
            } else {
                Toast.makeText(Preview_Video_A.this, "Please select proper video", Toast.LENGTH_LONG);
            }
        }
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }


    // Bottom all the function and the Call back listener of the Expo player

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }


    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }


    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

    }


}
