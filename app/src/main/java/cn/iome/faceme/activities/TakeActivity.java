package cn.iome.faceme.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.iome.faceme.R;
import cn.iome.faceme.bean.RecognizeBean;
import cn.iome.faceme.function.Consumer;
import cn.iome.faceme.utility.FaceManager;
import cn.iome.faceme.utility.UIUtil;
import cn.iome.faceme.view.CameraView;

/**
 * Created by haoping on 17/4/14.
 * TODO
 */
public class TakeActivity extends AppCompatActivity {

    private static final String TAG = TakeActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Camera mCamera;
    private String mCurrentPhotoPath;
    private int mCameraId = -1;
    private FaceManager face;
    private CameraView cameraPreview;
    private ProgressBar statusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.take_main);
        face = FaceManager.getFace();
        takeCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPermission();

    }

    private void takeCamera() {
        mCamera = chooseCamera();
        //followScreenOrientation(this, mCamera);
        Log.i(TAG, "mCamera: " + mCamera);
        cameraPreview = new CameraView(this, mCamera);
        cameraPreview.setBackgroundResource(R.mipmap.face_recog_bg);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
        statusBar = (ProgressBar) findViewById(R.id.status);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Log.i(TAG, "onActivityResult: image" + imageBitmap.toString());
        }
    }

    /**
     * onClick button
     * @param view view
     */
    public void capture(View view) {
        if (checkCameraHardware(this)) {
            mCamera.takePicture(null, null, mPicture);
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        int numberOfCameras = Camera.getNumberOfCameras();
        Log.i(TAG, "numberOfCameras: " + numberOfCameras);

        try {
            //the frontal camera has id = "1", and the back camera id = "0"
            c = Camera.open(0); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();

    /**
     * This rewrites
     */
    private Camera chooseCamera() {
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++) {
            Log.i(TAG, "chooseCamera: " + i);
            Camera.getCameraInfo(i, mCameraInfo);
            if (mCameraInfo.facing == 1) {
                mCameraId = i;
                Log.i(TAG, "chooseCamera-mCameraId " + i);
                return Camera.open(mCameraId);
            }
        }
        return null;
    }

    /**
     * This rewrites
     */
    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }


    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * check auto focus
     */
    public static boolean isAutoFocusSupported(Camera.Parameters params) {
        List<String> modes = params.getSupportedFocusModes();
        return modes.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile;
            try {
                pictureFile = createImageFile(TakeActivity.this);
                if (pictureFile != null) {
                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                        fos.flush();
                        fos.close();
                        statusBar.setVisibility(View.VISIBLE);
                        Log.i(TAG, "mCurrentPhotoPath: " + mCurrentPhotoPath);
                        HashMap<String, String> options = new HashMap<>();
                        options.put("max_face_num", "5");
                        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
                        face.faceRecognizeWithPath(mCurrentPhotoPath, options, new Consumer<RecognizeBean>() {
                            @Override
                            public void apply(RecognizeBean recognizeBean) {
                                statusBar.setVisibility(View.INVISIBLE);
                                RecognizeBean.ResultBean r = recognizeBean.getResult().get(0);
                                Log.i(TAG, "apply: " + recognizeBean.toString());
                                UIUtil.showDialog(TakeActivity.this, "人脸识别结果: ", " gender: " + r.getGender() + ",\n age: " + r.getAge() + ",\n beauty: " + r.getBeauty() + ",\n glasses: " + r.getGlasses(), "确定", "取消", true, new Consumer<DialogInterface>() {
                                    @Override
                                    public void apply(DialogInterface dialogInterface) {
                                        cameraPreview.startPreviewDisplay();
                                    }
                                });
                            }
                        });
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d(TAG, "Error accessing file: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 创建拍照文件
     *
     * @param context context
     * @return 照片文件
     * @throws IOException IOException
     */
    public File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.SIMPLIFIED_CHINESE).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static void followScreenOrientation(Context context, Camera camera){
        final int orientation = context.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(180);
        }else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }
    }

    private void getPermission() {
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        // if not get permission
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                System.out.println("shouldShowRequestPermissionRationale: true");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission not get", Toast.LENGTH_SHORT).show();

            } else {
                System.out.println("shouldShowRequestPermissionRationale: false");
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    System.out.println("onRequestPermissionsResult: true");

                } else {
                    System.out.println("onRequestPermissionsResult: false");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
