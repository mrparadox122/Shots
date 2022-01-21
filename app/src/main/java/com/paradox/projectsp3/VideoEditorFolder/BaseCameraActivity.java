
package com.paradox.projectsp3.VideoEditorFolder;

import static com.paradox.projectsp3.R.color.red;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.paradox.projectsp3.HomeActivty;
import com.paradox.projectsp3.MainActivity;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.SoundActivity;
import com.paradox.projectsp3.VideoEditorFolder.widget.SampleCameraGLView;


import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BaseCameraActivity extends AppCompatActivity {

    private SampleCameraGLView sampleGLView;
    protected GPUCameraRecorder GPUCameraRecorder;
    private String filepath;
    private TextView addSound;

    private ImageView recordBtn,pauseBtn,Face,Edit,Timer;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    LinearLayout timerLayout;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    TextView Time15,Time30,Time60;
    TextView sound_button;
    private MediaPlayer mp;

    private boolean toggleClick = false;

    private ListView lv;
    private ImageView Close,Gallery;
    private String sound_url=null, sound_title=null;

    protected void onCreateActivity() {
        getSupportActionBar().hide();
        recordBtn = findViewById(R.id.record);
     Timer=findViewById(R.id.timer);
        pauseBtn= findViewById(R.id.pause);
        Face=findViewById(R.id.imageView2);
        Edit=findViewById(R.id.imageView4);
        Close=findViewById(R.id.close);
        Gallery=findViewById(R.id.gallery);
        addSound=findViewById(R.id.button);
        sound_url=getIntent().getStringExtra("sound_url");
        sound_title=getIntent().getStringExtra("sound_title");
        sound_button=findViewById(R.id.button);
        Time15=findViewById(R.id.time15s);
        Time30=findViewById(R.id.time30s);
        Time60=findViewById(R.id.time60s);
        timerLayout=findViewById(R.id.timerlayout);
        final boolean[] timer = {false};
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
        Face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BaseCameraActivity.this, FaceFilterActivity.class);

                startActivity(intent);
                Animatoo.animateSlideUp(BaseCameraActivity.this);
                finish();
            }
        });



        final Boolean[] time15 = {false};
        final Boolean[] time30 = {false};
        final Boolean[] time60 = {false};


        Time15.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Time15.setBackgroundColor(R.color.red);
                Time30.setBackgroundColor(R.color.black);
                Time60.setBackgroundColor(R.color.black);

                time15[0] =true;
                time30[0] =false;
                time60[0] =false;

            }
        });
        Time30.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Time15.setBackgroundColor(R.color.black);
                Time30.setBackgroundColor(R.color.red);
                Time60.setBackgroundColor(R.color.black);

                time15[0] =false;
                time30[0] =true;
                time60[0] =false;

            }
        });
        Time60.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Time15.setBackgroundColor(R.color.black);
                Time30.setBackgroundColor(R.color.black);
                Time60.setBackgroundColor(R.color.red);

                time15[0] =false;
                time30[0] =false;
                time60[0] =true;
            }
        });



        if(sound_title !=null)
        {
            sound_button.setText(sound_title);
        }


        addSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BaseCameraActivity.this, SoundActivity.class);
                startActivity(intent);
                Animatoo.animateCard(BaseCameraActivity.this);
                finish();

            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                Intent intent=new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),100);
            }
        });



        recordBtn.setOnClickListener(v -> {

            if(time15[0]||time30[0]| time60[0]) {

               lv.setVisibility(View.GONE);
                filepath = getVideoFilePath();
                GPUCameraRecorder.start(filepath);
                recordBtn.setVisibility(View.GONE);
                Face.setVisibility(View.VISIBLE);
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

            GPUCameraRecorder.stop();
            recordBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.GONE);
            Face.setVisibility(View.VISIBLE);
            Edit.setVisibility(View.GONE);
            Toast.makeText(this,"Recording Stopped",Toast.LENGTH_SHORT).show();



            ///stop sound file///
            if(sound_url !=null)
            {
                try {
                    mp.stop();
                }
                catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }

            }
            sound_button.setText("Add Sound");
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


        lv = findViewById(R.id.filter_list);

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
                        lv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onRecordStart() {
                        runOnUiThread(() -> {
                            lv.setVisibility(View.GONE);
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

                    @Override
                    public void onVideoFileReady() {

                    }
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