package com.paradox.projectsp3.VideoEditorFolder;

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
import com.paradox.projectsp3.FaceFilters.FaceFilterActivity;
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.SegmentProgress.ProgressBarListener;
import com.paradox.projectsp3.SegmentProgress.SegmentedProgressBar;
import com.paradox.projectsp3.SoundActivity;
import com.paradox.projectsp3.SoundsList.SoundList_Main_A;
import com.paradox.projectsp3.Variables;
import com.paradox.projectsp3.VideoEditorFolder.widget.SampleCameraGLView;
import com.paradox.projectsp3.VideoRecording.GallerySelectedVideo_A;
import com.paradox.projectsp3.VideoRecording.VideoRecording_Activity;


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
    private String sound_url=null, sound_title=null;

    long time_in_milis = 0;
    int sec_passed = 0;
    SegmentedProgressBar video_progress;
    ImageView video_upload;
    TextView txt_next;
    boolean is_recording_timer_enable;
    int recording_time = 3;

    LinearLayout addSound123;


    protected void onCreateActivity() {
        getSupportActionBar().hide();
        recordBtn = findViewById(R.id.record);

        Timer=findViewById(R.id.timer);
        pauseBtn= findViewById(R.id.pause);
        Face=findViewById(R.id.imageView2);
        setting=findViewById(R.id.settings);
        addSound123=findViewById(R.id.addSound123);
        sound_url=getIntent().getStringExtra("sound_url");
        sound_title=getIntent().getStringExtra("sound_title");
        sound_button=findViewById(R.id.button);
        Time15=findViewById(R.id.time15s);
        Time30=findViewById(R.id.time30s);
        Time60=findViewById(R.id.time60s);
        timerLayout=findViewById(R.id.timerlayout);
        Pause_video=findViewById(R.id.pause_video);
        Play_video=findViewById(R.id.play_video);
        video_progress = findViewById(R.id.video_progress);
        video_upload = findViewById(R.id.video_upload);
        txt_next = findViewById(R.id.txt_next);

        video_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseCameraActivity.this, GallerySelectedVideo_A.class);
                startActivity(intent);
            }
        });



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
        Photo_filter=findViewById(R.id.photo_filter);

        lv=findViewById(R.id.filter_list);

        final boolean[] photo_filter = {true};

        btn_switch_camera=findViewById(R.id.btn_switch_camera);

        Photo_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photo_filter[0])
                {
                    lv.setVisibility(View.VISIBLE);
                    photo_filter[0] =false;
                }
                else
                {
                    lv.setVisibility(View.GONE);
                    photo_filter[0] =true;
                }

            }
        });


        final Boolean[] time15 = {false};
        final Boolean[] time30 = {false};
        final Boolean[] time60 = {false};


        Time15.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                Time15.setBackgroundColor(getColor(R.color.colorwhite_50));
                Time30.setBackgroundColor(getColor(R.color.app_color));
                Time60.setBackgroundColor(getColor(R.color.app_color));

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
                Time15.setBackgroundColor(getColor(R.color.app_color));
                Time30.setBackgroundColor(getColor(R.color.colorwhite_50));
                Time60.setBackgroundColor(getColor(R.color.app_color));
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
                Time15.setBackgroundColor(getColor(R.color.app_color));
                Time30.setBackgroundColor(getColor(R.color.app_color));
                Time60.setBackgroundColor(getColor(R.color.colorwhite_50));
                time15[0] =false;
                time30[0] =false;
                time60[0] =true;
            }
        });

        if(sound_title !=null)
        {
            sound_button.setText(sound_title);
        }

        addSound123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BaseCameraActivity.this, SoundList_Main_A.class);
                startActivity(intent);
                Animatoo.animateCard(BaseCameraActivity.this);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting=new Dialog(BaseCameraActivity.this);
                dialogSetting.setContentView(R.layout.camera_setting);
                dialogSetting.setCancelable(false);
                ImageView close=dialogSetting.findViewById(R.id.back);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogSetting.dismiss();
                    }
                });
                dialogSetting.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogSetting.getWindow().setGravity(Gravity.TOP);
                dialogSetting.show();
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

//                lv.setVisibility(View.GONE);
                filepath = getVideoFilePath();
                GPUCameraRecorder.start(filepath);
                btn_switch_camera.setVisibility(View.GONE);
                btn_switch_camera.setVisibility(View.GONE);
                Pause_video.setVisibility(View.VISIBLE);

                recordBtn.setVisibility(View.GONE);
                txt_next.setVisibility(View.GONE);
                video_upload.setVisibility(View.GONE);

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

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                /// stop recording in 15s ///

                if(time15[0])
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            GPUCameraRecorder.stop();
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GPUCameraRecorder.stop();
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GPUCameraRecorder.stop();
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void start_or_Stop_Recording() {


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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (GPUCameraRecorder != null) {
            GPUCameraRecorder.stop();
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
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "GPUCameraRecorder.mp4";
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