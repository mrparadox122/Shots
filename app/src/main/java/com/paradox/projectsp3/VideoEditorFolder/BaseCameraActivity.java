package com.paradox.projectsp3.VideoEditorFolder;

import static com.yalantis.ucrop.UCropFragment.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.daasuu.gpuv.camerarecorder.CameraRecordListener;
import com.daasuu.gpuv.camerarecorder.GPUCameraRecorder;
import com.daasuu.gpuv.camerarecorder.GPUCameraRecorderBuilder;
import com.daasuu.gpuv.camerarecorder.LensFacing;
import com.paradox.projectsp3.AudioTrimmerActivity;
import com.paradox.projectsp3.FaceFilters.FaceFilterActivity;
import com.paradox.projectsp3.Functions;
import com.paradox.projectsp3.GlobalVariables;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.SegmentProgress.ProgressBarListener;
import com.paradox.projectsp3.SegmentProgress.SegmentedProgressBar;

import com.paradox.projectsp3.SoundsList.SoundActivity;
import com.paradox.projectsp3.Variables;
import com.paradox.projectsp3.VideoEditorFolder.widget.SampleCameraGLView;
import com.paradox.projectsp3.VideoRecording.GallerySelectedVideo_A;
import com.paradox.projectsp3.VideoRecording.Preview_Video_A;
import com.paradox.projectsp3.VideoRecording.VideoRecording_Activity;
import com.wonderkiln.camerakit.CameraView;


import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseCameraActivity extends AppCompatActivity {
    private SampleCameraGLView sampleGLView;
    protected GPUCameraRecorder GPUCameraRecorder;
    private String filepath;
//    private TextView addSound123;
    Dialog dialogSetting;
    CircleImageView showVideoPath;
     ImageView recordBtn,pauseBtn,Face,Edit,Timer,setting,Pause_video,Play_video,Photo_filter,btn_switch_camera;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 1280;
    protected int videoHeight = 720;
    LinearLayout timerLayout;
    TextView Time15,Time30,Time60;
    TextView sound_button;
    private MediaPlayer mp;
    private boolean toggleClick = false;
    private ListView lv;
    private String sound_url = GlobalVariables.sound_url, sound_title = GlobalVariables.sound_title;
    LinearLayout camera_options, upload_layout;
    ImageButton rotate_camera, cut_video_btn;
    long time_in_milis = 0;
    int sec_passed = 0;
    SegmentedProgressBar video_progress;
    TextView rocordtim;
    boolean is_recording_timer_enable;
    int recording_time = 3;
    boolean is_recording = false;
    int number = 0;
    LinearLayout addSound123;
    ArrayList<String> videopaths = new ArrayList<>();
    CameraView cameraView;
    Thread myThread;
    MediaPlayer audio;
    ImageButton done_btn;
    ImageButton record_image;


    protected void onCreateActivity() {
        getSupportActionBar().hide();
        recordBtn = findViewById(R.id.record);
        rocordtim = findViewById(R.id.rocordtim);
        Timer=findViewById(R.id.timer);
        pauseBtn= findViewById(R.id.pause);
        Face=findViewById(R.id.imageView2);
        setting=findViewById(R.id.settings);
        addSound123=findViewById(R.id.addSound123);
//        sound_url=getIntent().getStringExtra("sound_url");
//        sound_title=getIntent().getStringExtra("sound_title");
        sound_button=findViewById(R.id.add_sound_txtv);
        Time15=findViewById(R.id.time15s);
        Time30=findViewById(R.id.time30s);
        Time60=findViewById(R.id.time60s);
        timerLayout=findViewById(R.id.timerlayout);
        Pause_video=findViewById(R.id.pause_video);
        Play_video=findViewById(R.id.play_video);
        video_progress = findViewById(R.id.video_progress);
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();




        final boolean[] timer = {false};
            sec_passed = 0;
            video_progress = findViewById(R.id.video_progress);
            video_progress.enableAutoProgressView(Variables.recording_duration);
            video_progress.setDividerColor(Color.WHITE);
            video_progress.setDividerEnabled(true);
            video_progress.setDividerWidth(4);
            video_progress.setShader(new int[]{Color.CYAN, Color.CYAN, Color.CYAN});

            video_progress.SetListener(new ProgressBarListener() {
                @Override
                public void TimeinMill(long mills) {
                    time_in_milis = mills;
                    sec_passed = (int) (mills / 1000);
                    if (sec_passed > (Variables.recording_duration / 1000) - 1) {
                        start_or_Stop_Recording();
                    }
                    if (is_recording_timer_enable && sec_passed >= recording_time) {
                        is_recording_timer_enable = false;
                        start_or_Stop_Recording();
                    }

                }
            });

        Timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer[0] ==false) {
                    timerLayout.setVisibility(View.VISIBLE);
                    timer[0] =true;
                }
                else
                {
                    timerLayout.setVisibility(View.GONE);
                    timer[0] =false;
                }
            }
        });
        showVideoPath=findViewById(R.id.profilepic);
//        Photo_filter=findViewById(R.id.photo_filter);

        lv=findViewById(R.id.filter_list);

//        final boolean[] photo_filter = {true};

        btn_switch_camera=findViewById(R.id.btn_switch_camera);

//        Photo_filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(photo_filter[0])
//                {
//                    lv.setVisibility(View.VISIBLE);
//                    photo_filter[0] =false;
//                }
//                else
//                {
//                    lv.setVisibility(View.GONE);
//                    photo_filter[0] =true;
//                }
//
//            }
//        });


        final Boolean[] time15 = {false};
        final Boolean[] time30 = {false};
        final Boolean[] time60 = {false};


        Time15.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                Time15.setTextColor(getColor(R.color.ic_launcher_background));
                Time30.setTextColor(getColor(R.color.white));
                Time60.setTextColor(getColor(R.color.white));

                time15[0] =true;
                time30[0] =false;
                time60[0] =false;

            }
        });
        Time30.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                Time15.setTextColor(getColor(R.color.white));
                Time30.setTextColor(getColor(R.color.ic_launcher_background));
                Time60.setTextColor(getColor(R.color.white));

                time15[0] =false;
                time30[0] =true;
                time60[0] =false;
            }
        });
        Time60.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Time15.setTextColor(getColor(R.color.white));
                Time30.setTextColor(getColor(R.color.white));
                Time60.setTextColor(getColor(R.color.ic_launcher_background));

                time15[0] =false;
                time30[0] =false;
                time60[0] =true;
            }
        });

        if(GlobalVariables.sound_title !=null)
        {
            sound_button.setText(GlobalVariables.sound_title);
        }

        addSound123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BaseCameraActivity.this, AudioTrimmerActivity.class);
                startActivity(intent);
                Animatoo.animateCard(BaseCameraActivity.this);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseCameraActivity.this,NewCameraSettings.class);
                startActivity(intent);
            }
        });

//        Face.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(time15[0]||time30[0]| time60[0]) {
//                Intent intent=new Intent(BaseCameraActivity.this, FaceFilterActivity.class);
//                intent.putExtra("time15",time15[0]);
//                intent.putExtra("time30",time15[0]);
//                intent.putExtra("time60",time15[0]);
//                Animatoo.animateSlideUp(BaseCameraActivity.this);
//                startActivity(intent);
//                finish();
//            }
//
//                else
//                {
//                    Toast toast=Toast.makeText(BaseCameraActivity.this," Plz Select Time",Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();
//                }

//            }
//
//        });

        Pause_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pause_video.setVisibility(View.GONE);
                Play_video.setVisibility(View.VISIBLE);


                Toast.makeText(BaseCameraActivity.this,"Paused",Toast.LENGTH_SHORT).show();

            }
        });
        Play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play_video.setVisibility(View.GONE);
                Pause_video.setVisibility(View.VISIBLE);

                Toast.makeText(BaseCameraActivity.this,"Play",Toast.LENGTH_SHORT).show();
            }
        });
//        Play_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sampleGLView.onResume();
//                Play_video.setVisibility(View.GONE);
//                Pause_video.setVisibility(View.VISIBLE);
//            }
//        });



//cameyghhhj
        recordBtn.setOnClickListener(v -> {

            if(time15[0]||time30[0]| time60[0]) {

                timerLayout.setVisibility(View.GONE);
                rocordtim.setVisibility(View.VISIBLE);
//                lv.setVisibility(View.GONE);
                filepath = getVideoFilePath();
                GPUCameraRecorder.start(filepath);
                btn_switch_camera.setVisibility(View.GONE);
                btn_switch_camera.setVisibility(View.GONE);
                Pause_video.setVisibility(View.VISIBLE);

                recordBtn.setVisibility(View.GONE);


//                Face.setVisibility(View.VISIBLE);
                //Edit.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
                Glide.with(this).load(R.drawable.recording_video).into(pauseBtn);


///// play sound ///////////////

                if (sound_url != null) {
                    try {
                        mp = new MediaPlayer();
                        mp.setDataSource(sound_url);
                        mp.prepare();
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                GPUCameraRecorder.stop();
                                rocordtim.setVisibility(View.INVISIBLE);
                                myThread.interrupt();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                /// stop recording in 15s ///

                if(time15[0])
                {
                    initCountDownTimer(15000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            rocordtim.setVisibility(View.INVISIBLE);
                            recordBtn.setVisibility(View.VISIBLE);
                            pauseBtn.setVisibility(View.GONE);
                            Toast.makeText(BaseCameraActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                            ///stop sound file///
                            if (sound_url != null) {
                                try {
                                    mp.stop();
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                            }
//                            sound_button.setText("Add Sound");
                        }

                    }, 15000);
                }
                if(time30[0])
                {

                    initCountDownTimer(30000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            myThread.interrupt();
                            rocordtim.setVisibility(View.INVISIBLE);
                            recordBtn.setVisibility(View.VISIBLE);
                            pauseBtn.setVisibility(View.GONE);
                            Toast.makeText(BaseCameraActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                            ///stop sound file///
                            if (sound_url != null) {
                                try {
                                    mp.stop();
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }

                            }
                            sound_button.setText("Add Sound");
                        }

                    }, 30000);
                }

                if(time60[0])
                {

                    initCountDownTimer(60000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GPUCameraRecorder.stop();
                            rocordtim.setVisibility(View.INVISIBLE);
                            recordBtn.setVisibility(View.VISIBLE);
                            pauseBtn.setVisibility(View.GONE);
                            Face.setVisibility(View.VISIBLE);
//                            Edit.setVisibility(View.VISIBLE);
                            Toast.makeText(BaseCameraActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                            ///stop sound file///
                            if (sound_url != null) {
                                try {


                                    mp.stop();
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }

                            }
                            sound_button.setText("Add Sound");
                        }

                    }, 60000);
                }

            }
            else
            {
                timerLayout.setVisibility(View.VISIBLE);
                timer[0] =true;
                Toast toast=Toast.makeText(BaseCameraActivity.this," Plz Select Time",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }

        });
        pauseBtn.setOnClickListener(v -> {
            btn_switch_camera.setVisibility(View.VISIBLE);
            Pause_video.setVisibility(View.GONE);
            Play_video.setVisibility(View.GONE);

            GPUCameraRecorder.stop();
            myThread.interrupt();
            recordBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.GONE);
//            Face.setVisibility(View.VISIBLE);
//                            Edit.setVisibility(View.VISIBLE);
            Toast.makeText(BaseCameraActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();
//            sound_button.setText("Add Sound");
        });
        findViewById(R.id.btn_flash).setOnClickListener(v -> {
            if (GPUCameraRecorder != null && GPUCameraRecorder.isFlashSupport()) {
                GPUCameraRecorder.switchFlashMode();
                GPUCameraRecorder.changeAutoFocus();
            }
        });

        findViewById(R.id.btn_switch_camera).setOnClickListener(v -> {
            releaseCamera();
            if (lensFacing == LensFacing.BACK) {
                lensFacing = LensFacing.FRONT;
            } else {
                lensFacing = LensFacing.BACK;
            }
            toggleClick = true;
        });

//        findViewById(R.id.btn_image_capture).setOnClickListener(v -> {
//            captureBitmap(bitmap -> {
//                new Handler().post(() -> {
//                    String imagePath = getImageFilePath();
//                    saveAsPngImage(bitmap, imagePath);
//                    exportPngToGallery(getApplicationContext(), imagePath);
//                });
//            });
//        });



        final List<FilterType> filterTypes = FilterType.createFilterList();
        lv.setAdapter(new FilterAdapter(this, R.layout.row_white_text, filterTypes).whiteMode());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (GPUCameraRecorder != null) {
                    GPUCameraRecorder.setFilter(FilterType.createGlFilter(filterTypes.get(position), getApplicationContext()));
                }
            }
        });

        ///////////////////////
        ///////////////////////
        if (GlobalVariables.sound_url != null){
            Toast.makeText(this, String.valueOf(GlobalVariables.sound_url), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, String.valueOf(GlobalVariables.sound_title), Toast.LENGTH_SHORT).show();
        }


        Log.e(TAG, String.valueOf(GlobalVariables.sound_url) );
        Log.e(TAG, String.valueOf(GlobalVariables.sound_title) );

        ////////////////////////
        ////////////////////////





        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    public void initCountDownTimer(int time) {
        new CountDownTimer(time, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, String.valueOf(millisUntilFinished / 1000));
                rocordtim.setText("00:"+ String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
//                textView.setText("done!");
            }
        }.start();

        myThread = new Thread(new Runnable(){
            public void run(){
            }
        });
         myThread.start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
    }

    public void start_or_Stop_Recording() {
        if (!is_recording && sec_passed < (Variables.recording_duration / 1000) - 1) {
            number = number + 1;
            is_recording = true;
            File file = new File(Variables.app_folder + "myvideo" + (number) + ".mp4");
            videopaths.add(Variables.app_folder + "myvideo" + (number) + ".mp4");
            cameraView.captureVideo(file);
            if (audio != null)
                audio.start();
            done_btn.setBackgroundResource(R.drawable.ic_not_done);
            done_btn.setEnabled(false);
            video_progress.resume();
            record_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_recoding_yes));
            cut_video_btn.setVisibility(View.GONE);
            camera_options.setVisibility(View.GONE);
//            add_sound_txt.setClickable(false);
            rotate_camera.setVisibility(View.GONE);
            upload_layout.setVisibility(View.GONE);

        } else if (is_recording) {
            is_recording = false;
            video_progress.pause();
            video_progress.addDivider();
            if (audio != null)
                audio.pause();
            cameraView.stopVideo();
//            check_done_btn_enable();
            cut_video_btn.setVisibility(View.VISIBLE);
            record_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_recoding_no));
            camera_options.setVisibility(View.VISIBLE);
            upload_layout.setVisibility(View.VISIBLE);
        } else if (sec_passed > (Variables.recording_duration / 1000)) {
            Functions.show_Alert(this, "Alert", "Video only can be a " + (int) Variables.recording_duration / 1000 + " S");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (GPUCameraRecorder != null) {
            GPUCameraRecorder.stop();
            rocordtim.setVisibility(View.INVISIBLE);
            GPUCameraRecorder.release();
            GPUCameraRecorder = null;
        }

        Intent intent=new Intent(BaseCameraActivity.this, HomeActivty.class);
        startActivity(intent);
        Animatoo.animateSlideDown(BaseCameraActivity.this);
        finish();
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (GPUCameraRecorder != null) {
            GPUCameraRecorder.stop();
            myThread.interrupt();
            GPUCameraRecorder.release();
            GPUCameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }


    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.wrap_view);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleCameraGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (GPUCameraRecorder == null) return;
                GPUCameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        GPUCameraRecorder = new GPUCameraRecorderBuilder(this, sampleGLView)
                //.recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
//                        runOnUiThread(() -> {
//                            findViewById(R.id.btn_flash).setEnabled(flashSupport);
//                        });
                    }

                    @Override
                    public void onRecordComplete() {
                        exportMp4ToGallery(getApplicationContext(), filepath);
                        Log.e(TAG, "onRecordComplete: "+filepath );
                        GlobalVariables.mp4Path = filepath;
                        Variables.outputfile2 = filepath;
                        GPUCameraRecorder.stop();
                        myThread.interrupt();
                        rocordtim.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(BaseCameraActivity.this, Preview_Video_A.class);
                        startActivity(intent);
//                        lv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onRecordStart() {
                        runOnUiThread(() -> {
//                            lv.setVisibility(View.GONE);
                        });
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("GPUCameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(() -> {
                                setUpCamera();
                            });
                        }
                        toggleClick = false;
                    }

                    public void onVideoFileReady() {

                    }

//                    @Override
//                    public void onVideoFileReady() {
//
//                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .build();


    }

//    private void changeFilter(Filters filters) {
//        GPUCameraRecorder.setFilter(Filters.getFilterInstance(filters, getApplicationContext()));
//    }


    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {
        sampleGLView.queueEvent(() -> {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
            Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);

            runOnUiThread(() -> {
                bitmapReadyCallbacks.onBitmapReady(snapshotBitmap);
            });
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveAsPngImage(Bitmap bitmap, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void exportMp4ToGallery(Context context, String filePath) {
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + filePath)));
    }
    @SuppressLint("SimpleDateFormat")
    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "Gbggggg.mp4";
    }
    public static File getAndroidMoviesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }
    private static void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "GPUCameraRecorder.png";
    }
    public static File getAndroidImageFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }
    public void  checkPermission()
    {
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M)
        {
            return;
        }
        if(ActivityCompat.checkSelfPermission(BaseCameraActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(BaseCameraActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);

        }
        else
        {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 101:
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {

        }
    }
}