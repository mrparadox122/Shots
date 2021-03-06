package com.paradox.projectsp3.FaceFilters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daasuu.gpuv.camerarecorder.LensFacing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.snackbar.Snackbar;
import com.paradox.projectsp3.FaceFilters.camera.CameraSourcePreview;
import com.paradox.projectsp3.FaceFilters.camera.GraphicOverlay;
import com.paradox.projectsp3.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FaceFilterActivity extends AppCompatActivity {


    TextGraphic mTextGraphic;

    private final Thread observer = new Thread("observer") {

        {
            setDaemon(true);
        }

        public void run() {

            while( !isInterrupted() ) {
                /*
                TextGraphic mTextGraphic = new TextGraphic(mGraphicOverlay);
                mGraphicOverlay.add(mTextGraphic);*/
                //mTextGraphic.updateText(2);
            }

        };
    };

    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;
    private int typeFace = 0;
    private int typeFlash = 0;
    private boolean flashmode = false;
    private Camera camera;
    ImageButton cameraRecord,pause;

    private static final int MASK[] = {
            R.id.no_filter,
            R.id.hair,
            R.id.op,
            R.id.snap,
            R.id.glasses2,
            R.id.glasses3,
            R.id.glasses4,
            R.id.glasses5,
            R.id.mask,
            R.id.mask2,
            R.id.mask3,
            R.id.dog,
            R.id.cat2
    };

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;


    // private static final String TAG="MainActivity";
    private static final int PERMISSION_CODE=1;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private static final int DISPLAY_WIDTH=480;
    private static final int DISPLAY_HEIGHT=640;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private MediaRecorder mMediaRecorder;
    private Object Handler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_face_filter);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        //mTextGraphic = new TextGraphic(mGraphicOverlay);
        //mGraphicOverlay.add(mTextGraphic);
        cameraRecord =  findViewById(R.id.recoredbtn);
        pause = findViewById(R.id.pause);

        ///////screen record////////////
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity =metrics.densityDpi;


        //prepareRecorder();
        mProjectionManager =(MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        mMediaProjectionCallback=new MediaProjectionCallback();
        ImageButton face = (ImageButton) findViewById(R.id.face);
        face.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(findViewById(R.id.scrollView).getVisibility() == GONE){
                    findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.face)).setImageResource(R.drawable.face_select);
                }else{
                    findViewById(R.id.scrollView).setVisibility(GONE);
                    ((ImageButton) findViewById(R.id.face)).setImageResource(R.drawable.face);
                }
            }
        });

        ImageButton no_filter = (ImageButton) findViewById(R.id.no_filter);
        no_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 0;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton hair = (ImageButton) findViewById(R.id.hair);
        hair.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 1;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton op = (ImageButton) findViewById(R.id.op);
        op.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 2;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton snap = (ImageButton) findViewById(R.id.snap);
        snap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 3;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton glasses2 = (ImageButton) findViewById(R.id.glasses2);
        glasses2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 4;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton glasses3 = (ImageButton) findViewById(R.id.glasses3);
        glasses3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 5;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton glasses4 = (ImageButton) findViewById(R.id.glasses4);
        glasses4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 6;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton glasses5 = (ImageButton) findViewById(R.id.glasses5);
        glasses5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 7;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton mask = (ImageButton) findViewById(R.id.mask);
        mask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 8;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton mask2 = (ImageButton) findViewById(R.id.mask2);
        mask2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 9;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton mask3 = (ImageButton) findViewById(R.id.mask3);
        mask3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 10;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton dog = (ImageButton) findViewById(R.id.dog);
        dog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 11;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton cat2 = (ImageButton) findViewById(R.id.cat2);
        cat2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 12;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });




        cameraRecord .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pause.setVisibility(View.VISIBLE);
                recordVideo();

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopScreenSharing();
            }
        });



        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode !=PERMISSION_CODE)
//        {
//            Log.e(TAG,"Unknown request code "+requestCode);
//            return;
//        }
//        if(resultCode !=RESULT_OK)
//        {
//            Toast.makeText(this,"Screen Cast Permission Denied",Toast.LENGTH_SHORT).show();
//            cameraRecord.setVisibility(GONE);
//            return;
//        }
//
//        mMediaProjection =mProjectionManager.getMediaProjection(resultCode,data);
//        mMediaProjection.registerCallback(mMediaProjectionCallback,null);
//        mVirtualDisplay=createVirtualDisplay();
//        mMediaRecorder.start();
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void recordVideo() {
        shareScreen();



        Toast.makeText(this,"Recording Started....",Toast.LENGTH_SHORT).show();
        findViewById(R.id.scrollView).setVisibility(View.GONE);



        Handler =new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaProjection=null;
                stopScreenSharing();

                Toast.makeText(FaceFilterActivity.this,"Recording Stopped...",Toast.LENGTH_SHORT).show();


            }
        },30000);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void shareScreen()

    {
        initRecorder();

        pause.setVisibility(View.VISIBLE);
        cameraRecord.setVisibility(GONE);
        if(mMediaProjection==null)
        {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(),PERMISSION_CODE);
            return;
        }
        mVirtualDisplay= createVirtualDisplay();
        mMediaRecorder.start();
    }
    private void stopScreenSharing()
    {    pause.setVisibility(GONE);

        cameraRecord.setVisibility(View.VISIBLE);
        if(mVirtualDisplay==null)
        {
            return;
        }
        Toast.makeText(FaceFilterActivity.this,"Recording Stopped",Toast.LENGTH_SHORT).show();
        mVirtualDisplay.release();
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay()
    {
        return mMediaProjection.createVirtualDisplay("FaceFilterActivity",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,mMediaRecorder.getSurface(),null,null);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCallback extends MediaProjection.Callback
    {
        @Override
        public void onStop() {
            super.onStop();

            mMediaProjection=null;
            stopScreenSharing();
            Log.i(TAG,"MediaProjection Stopped");

        }
    }



    private void prepareRecorder()
    {
        try{
            mMediaRecorder.prepare();
        }
        catch(IllegalStateException  | IOException e)
        {
            e.printStackTrace();
            finish();
        }
    }
    public String getFilePath()
    {
        final String directory=Environment.getExternalStorageDirectory() + File.separator +"DCIM";
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            Toast.makeText(this,"Failed to get External Storage",Toast.LENGTH_SHORT).show();
            return null;
        }
        final File folder=new File(directory);
        boolean success=true;
        if(!folder.exists())
        {
            success=folder.mkdir();
        }
        String filePath;
        if(success)
        {
            String videoName=("indian_"+ "soosle" + ".mp4");
            filePath=directory+File.separator+ videoName;
            Toast.makeText(this,"fgvfgvgv External Storage"+filePath,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Failed to create Recording directory",Toast.LENGTH_SHORT).show();
            return null;
        }
        return filePath;
    }



    @SuppressLint("WrongConstant")
    private void initRecorder()
    {
        if(mMediaRecorder==null)
        {
            mMediaRecorder =new MediaRecorder();
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mMediaRecorder.setAudioSource(MediaRecorder.VideoSource.SURFACE);
//            mMediaRecorder.setAudioSource(MediaRecorder.OutputFormat.MPEG_4);
//            mMediaRecorder.setAudioSource(MediaRecorder.VideoEncoder.H264);
//
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioEncoder.AMR_NB);
//            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
//            mMediaRecorder.setVideoFrameRate(30);
//            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setOutputFile(getFilePath());

        }
    }








    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }


    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        //new MultiProcessor.Builder<>(new GraphicTextTrackerFactory()).build();

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(10.0f)
                .build();
        //observer.start();
        /*
        TextGraphic mTextGraphic = new TextGraphic(mGraphicOverlay);
        mGraphicOverlay.add(mTextGraphic);
        mTextGraphic.updateText(2);*/
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
        if(mMediaProjection !=null)
        {
            mMediaProjection.stop();
            mMediaProjection=null;

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }


    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private class GraphicTextTrackerFactory implements MultiProcessor.Factory<String> {
        @Override
        public Tracker<String> create(String face) {
            return new GraphicTextTracker(mGraphicOverlay);
        }
    }

    private class GraphicTextTracker extends Tracker<String> {
        private GraphicOverlay mOverlay;
        private TextGraphic mTextGraphic ;

        GraphicTextTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mTextGraphic = new TextGraphic(overlay);
        }

        public void onUpdate() {
            mOverlay.add(mTextGraphic);
            mTextGraphic.updateText(3);
        }

        @Override
        public void onDone() {
            mOverlay.remove(mTextGraphic);
        }
    }


    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }


    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay,typeFace);
        }


        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }


        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face,typeFace);
        }


        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }


        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }
}