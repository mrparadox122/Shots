package com.paradox.projectsp3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
//import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
//import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
//import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
//import com.jaredrummler.ktsh.Shell;
import com.paradox.projectsp3.databinding.ActivityPreviewVideoBinding;
import com.paradox.projectsp3.photoeditor.OnPhotoEditorListener;
import com.paradox.projectsp3.photoeditor.PhotoEditor;
import com.paradox.projectsp3.photoeditor.PhotoEditorView;
import com.paradox.projectsp3.photoeditor.SaveSettings;
import com.paradox.projectsp3.photoeditor.TextStyleBuilder;
import com.paradox.projectsp3.photoeditor.ViewType;
import com.paradox.projectsp3.utils.DimensionData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION;

import static com.paradox.projectsp3.utils.Utils.getScaledDimension;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;




public class PreviewVideoActivity extends AppCompatActivity implements OnPhotoEditorListener, PropertiesBSFragment.Properties,
        View.OnClickListener,
        StickerBSFragment.StickerListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private ActivityPreviewVideoBinding binding;
    private static final String TAG = PreviewVideoActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    private PhotoEditor mPhotoEditor;
    private String globalVideoUrl = "";
    private PropertiesBSFragment propertiesBSFragment;
    private StickerBSFragment mStickerBSFragment;
    private MediaPlayer mediaPlayer;
    private String videoPath = "";
    private String imagePath = "";
    private ArrayList<String> exeCmd;

    private String[] newCommand;
    private ProgressDialog progressDialog;
    PhotoEditorView ivImage;
    private int originalDisplayWidth;
    private int originalDisplayHeight;
    private int newCanvasWidth, newCanvasHeight;
    private int DRAW_CANVASW = 0;
    private int DRAW_CANVASH = 0;
//    FFmpeg fFmpeg;

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview_video);
        ivImage = findViewById(R.id.ivImage);

        initViews();
//        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
//        Glide.with(this).load(getIntent().getStringExtra("DATA")).into(binding.ivImage.getSource());
        Glide.with(this).load(R.drawable.trans).centerCrop().into(binding.ivImage.getSource());

        videoPath = Variables.output_filter_file;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        String metaRotation = retriever.extractMetadata(METADATA_KEY_VIDEO_ROTATION);
        int rotation = metaRotation == null ? 0 : Integer.parseInt(metaRotation);
        if (rotation == 90 || rotation == 270) {
            DRAW_CANVASH = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            DRAW_CANVASW = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } else {
            DRAW_CANVASW = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            DRAW_CANVASH = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        }
        setCanvasAspectRatio();

        binding.videoSurface.getLayoutParams().width = newCanvasWidth;
        binding.videoSurface.getLayoutParams().height = newCanvasHeight;

        binding.ivImage.getLayoutParams().width = newCanvasWidth;
        binding.ivImage.getLayoutParams().height = newCanvasHeight;

        Log.d(">>", "width>> " + newCanvasWidth + "height>> " + newCanvasHeight + " rotation >> " + rotation);
    }

    private void initViews() {
//        fFmpeg = FFmpeg.getInstance(this);
        progressDialog = new ProgressDialog(this);
        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);
        propertiesBSFragment = new PropertiesBSFragment();
        propertiesBSFragment.setPropertiesChangeListener(this);
        mPhotoEditor = new PhotoEditor.Builder(this, ivImage)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                .setDeleteView(binding.imgDelete)
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

//        mPhotoEditor.setOnPhotoEditorListener(this);

        binding.imgClose.setOnClickListener(this);
        binding.imgDone.setOnClickListener(this);
        binding.imgDraw.setOnClickListener(this);
        binding.imgText.setOnClickListener(this);
        binding.imgUndo.setOnClickListener(this);
        binding.imgSticker.setOnClickListener(this);

        binding.videoSurface.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
//                activityHomeBinding.videoSurface.getLayoutParams().height=640;
//                activityHomeBinding.videoSurface.getLayoutParams().width=720;
                Surface surface = new Surface(surfaceTexture);

                try {
                    mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setDataSource("http://daily3gp.com/vids/747.3gp");

                    Log.d("VideoPath>>", videoPath);
                    mediaPlayer.setDataSource(videoPath);
                    mediaPlayer.setSurface(surface);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });

        exeCmd = new ArrayList<>();
//        try {
//            fFmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {
//                @Override
//                public void onFailure() {
//                    Log.d("binaryLoad", "onFailure");
//
//                }
//
//                @Override
//                public void onSuccess() {
//                    Log.d("binaryLoad", "onSuccess");
//                }
//
//                @Override
//                public void onStart() {
//                    Log.d("binaryLoad", "onStart");
//
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.d("binaryLoad", "onFinish");
//
//                }
//            });
//        } catch (FFmpegNotSupportedException e) {
//            e.printStackTrace();
//        }


    }

//    public void executeCommand(String[] command, final String absolutePath) {
//        try {
//            fFmpeg.execute(command, new FFmpegExecuteResponseHandler() {
//                @Override
//                public void onSuccess(String message) {
//                    Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(PreviewVideoActivity.this, VideoPreviewActivity.class);
//                    i.putExtra("DATA", absolutePath);
//                    startActivity(i);
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    Log.d("CommandExecute", "onProgress" + "  " + message);
//                    progressDialog.setMessage(message);
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.d("CommandExecute", "onFailure" + "  " + message);
//                    progressDialog.hide();
//                }
//
//                @Override
//                public void onStart() {
//                    progressDialog.setTitle("Preccesing");
//                    progressDialog.setMessage("Starting");
//                    progressDialog.show();
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            });
////            fFmpeg.execute(command, new FFcommandExecuteResponseHandler() {
////                @Override
////                public void onSuccess(String message) {
////                    Log.d("CommandExecute", "onSuccess" + "  " + message);
////                    Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
////                    Intent i = new Intent(PreviewVideoActivity.this, VideoPreviewActivity.class);
////                    i.putExtra("DATA", absolutePath);
////                    startActivity(i);
////                }
////
////                @Override
////                public void onProgress(String message) {
////                    Log.d("CommandExecute", "onProgress" + "  " + message);
////                    progressDialog.setMessage(message);
////                }
////
////                @Override
////                public void onFailure(String message) {
////                    Log.d("CommandExecute", "onFailure" + "  " + message);
////                    progressDialog.hide();
////
////                }
////
////                @Override
////                public void onStart() {
////                    progressDialog.setTitle("Preccesing");
////                    progressDialog.setMessage("Starting");
////                    progressDialog.show();
////
////                }
////
////                @Override
////                public void onFinish() {
////
////                }
////            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose:
                onBackPressed();
                break;
            case R.id.imgDone:
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                    return;
                }
                else {
                    Log.d(TAG, "onClick: //////////////////////////////////////////////////////////////////////////////////////");
                    requestPermission();

                }
                
                break;
            case R.id.imgDraw:
                setDrawingMode();
                break;
            case R.id.imgText:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(PreviewVideoActivity.this, 0);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {

                    @Override
                    public void onDone(String inputText, int colorCode, int position) {
                        final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                        styleBuilder.withTextColor(colorCode);
                        Typeface typeface = ResourcesCompat.getFont(PreviewVideoActivity.this, TextEditorDialogFragment.getDefaultFontIds(PreviewVideoActivity.this).get(position));
                        styleBuilder.withTextFont(typeface);
                        mPhotoEditor.addText(inputText, styleBuilder, position);
                    }
                });
                break;
            case R.id.imgUndo:
                Log.d("canvas>>", mPhotoEditor.undoCanvas() + "");
                mPhotoEditor.clearBrushAllViews();
                break;
            case R.id.imgSticker:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;

        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    saveImage();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivity(intent);
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PreviewVideoActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void setCanvasAspectRatio() {

        originalDisplayHeight = getDisplayHeight();
        originalDisplayWidth = getDisplayWidth();

        DimensionData displayDiamenion =
                getScaledDimension(new DimensionData((int) DRAW_CANVASW, (int) DRAW_CANVASH),
                        new DimensionData(originalDisplayWidth, originalDisplayHeight));
        newCanvasWidth = displayDiamenion.width;
        newCanvasHeight = displayDiamenion.height;

    }


    private void setDrawingMode() {
        if (mPhotoEditor.getBrushDrawableMode()) {
            mPhotoEditor.setBrushDrawingMode(false);
            binding.imgDraw.setBackgroundColor(ContextCompat.getColor(this, R.color.black_trasp));
        } else {
            mPhotoEditor.setBrushDrawingMode(true);
            binding.imgDraw.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            propertiesBSFragment.show(getSupportFragmentManager(), propertiesBSFragment.getTag());
        }
    }

    @SuppressLint("MissingPermission")
    private void saveImage() {

        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + ""
                + System.currentTimeMillis() + ".png");
        try {
            file.createNewFile();

            SaveSettings saveSettings = new SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(false)
                    .build();

            
//            mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
//                @Override
//                public void onSuccess(@NonNull String imagePath) throws Shell.ClosedException {
//                    PreviewVideoActivity.this.imagePath = imagePath;
//                    Log.d("imagePath>>", imagePath);
//                    Log.d("imagePath2>>", Uri.fromFile(new File(imagePath)).toString());
//                    binding.ivImage.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
//                    Toast.makeText(PreviewVideoActivity.this, "Saved successfully...", Toast.LENGTH_SHORT).show();
//                    String basedir = getApplicationInfo().nativeLibraryDir;
//                    String command = String.format("cd %s;./ffmpeg", basedir);
//                    Shell shell = new Shell("sh");
//                    Shell.Command.Result result = shell.run(command);
//                    System.out.println(result.stdout());
//                    System.out.println(result.stderr());
//                    applayWaterMark();
//                }
//
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(PreviewVideoActivity.this, "Saving Failed...", Toast.LENGTH_SHORT).show();
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void applayWaterMark() {

        imagePath = generatePath(Uri.fromFile(new File(imagePath)),PreviewVideoActivity.this);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        int width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        if (width > height) {
            int tempWidth = width;
            width = height;
            height = tempWidth;
        }

        

        Log.d(">>", "width>> " + width + "height>> " + height);
        retriever.release();

        File output = new File(Environment.getExternalStorageDirectory()
                + File.separator + ""
                + System.currentTimeMillis() + ".mp4");
        try {
            output.createNewFile();

            exeCmd.add("-y");
            exeCmd.add("-i");
            exeCmd.add(videoPath);
//            exeCmd.add("-framerate 30000/1001 -loop 1");
            exeCmd.add("-i");
            exeCmd.add(imagePath);
            exeCmd.add("-filter_complex");
//            exeCmd.add("-strict");
//            exeCmd.add("-2");
//            exeCmd.add("-map");
//            exeCmd.add("[1:v]scale=(iw+(iw/2)):(ih+(ih/2))[ovrl];[0:v][ovrl]overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2");
//            exeCmd.add("[1:v]scale=720:1280:1823[ovrl];[0:v][ovrl]overlay=x=0:y=0");
            exeCmd.add("[1:v]scale=" + DRAW_CANVASW + ":" + DRAW_CANVASH + "[ovrl];[0:v][ovrl]overlay=x=0:y=0");
            exeCmd.add("-c:v");
            exeCmd.add("libx264");
            exeCmd.add("-preset");
            exeCmd.add("ultrafast");
            exeCmd.add(output.getAbsolutePath());


            newCommand = new String[exeCmd.size()];
            for (int j = 0; j < exeCmd.size(); j++) {
                newCommand[j] = exeCmd.get(j);
            }


            for (int k = 0; k < newCommand.length; k++) {
                Log.d("CMD==>>", newCommand[k] + "");
            }

            newCommand = new String[]{"-i", videoPath, "-i", imagePath, "-preset", "ultrafast", "-filter_complex", "[1:v]scale=2*trunc(" + (width / 2) + "):2*trunc(" + (height/ 2) + ") [ovrl], [0:v][ovrl]overlay=0:0" , output.getAbsolutePath()};
//            executeCommand(newCommand, output.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.setBrushDrawingMode(false);
        binding.imgDraw.setBackgroundColor(ContextCompat.getColor(this, R.color.black_trasp));
        mPhotoEditor.addImage(bitmap);
    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode, final int position) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode, position);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode, int position) {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(colorCode);
                Typeface typeface = ResourcesCompat.getFont(PreviewVideoActivity.this, TextEditorDialogFragment.getDefaultFontIds(PreviewVideoActivity.this).get(position));
                styleBuilder.withTextFont(typeface);
                mPhotoEditor.editText(rootView, inputText, styleBuilder, position);
            }
        });
    }



    public String generatePath(Uri uri, Context context) {
        String filePath = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            filePath = generateFromKitkat(uri, context);
        }

        if (filePath != null) {
            return filePath;
        }

        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath == null ? uri.getPath() : filePath;
    }

    @TargetApi(19)
    private String generateFromKitkat(Uri uri, Context context) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);

            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Video.Media.DATA};
            String sel = MediaStore.Video.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();
        }
        return filePath;
    }

    private int getDisplayWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getDisplayHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setBrushColor(colorCode);
    }

    @Override
    public void onOpacityChanged(int opacity) {

    }

    @Override
    public void onBrushSizeChanged(int brushSize) {

    }


}
