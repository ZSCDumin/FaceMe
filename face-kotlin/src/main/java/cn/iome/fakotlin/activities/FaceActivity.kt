@file:Suppress("DEPRECATION")

package cn.iome.fakotlin.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import cn.iome.fakotlin.R
import cn.iome.fakotlin.bean.RecognizeBean
import cn.iome.fakotlin.function.Consumer
import cn.iome.fakotlin.utility.FaceManager
import cn.iome.fakotlin.utility.UIUtil
import cn.iome.fakotlin.view.CameraView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by haoping on 17/4/14.
 * TODO
 */
class FaceActivity : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var mCurrentPhotoPath: String? = null
    private var face: FaceManager? = null
    private var cameraPreview: CameraView? = null
    private var statusBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.take_main)
        face = FaceManager.create()
        takeCamera()
    }

    override fun onResume() {
        super.onResume()
        getPermission()
    }

    private fun takeCamera() {
        mCamera = chooseCamera()
        Log.i(TAG, "mCamera: " + mCamera!!)
        cameraPreview = CameraView(this, mCamera!!)
        cameraPreview!!.setBackgroundResource(R.mipmap.face_recog_bg)
        val preview = findViewById(R.id.camera_preview) as RelativeLayout
        preview.addView(cameraPreview)
        statusBar = findViewById(R.id.status) as ProgressBar
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            extras.get("data") as Bitmap
        }
    }

    /**
     * onClick button
     */
    fun capture() {
        if (checkCameraHardware(this)) {
            mCamera!!.takePicture(null, null, mPicture)
        }
    }

    private val mCameraInfo = Camera.CameraInfo()

    /**
     * This rewrites
     */
    private fun chooseCamera(): Camera? {
        var i = 0
        val count = Camera.getNumberOfCameras()
        while (i < count) {
            Log.i(TAG, "chooseCamera: " + i)
            Camera.getCameraInfo(i, mCameraInfo)
            if (mCameraInfo.facing == 1) {
                val mCameraId = i
                Log.i(TAG, "chooseCamera-mCameraId " + i)
                return Camera.open(mCameraId)
            }
            i++
        }
        return null
    }

    /**
     * Check if this device has a camera
     */
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    private val mPicture = Camera.PictureCallback { data, _ ->
        val pictureFile: File?
        try {
            pictureFile = createImageFile(this@FaceActivity)
            try {
                val fos = FileOutputStream(pictureFile)
                val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
                fos.close()
                Log.i(TAG, "mCurrentPhotoPath: " + mCurrentPhotoPath!!)
                recognize()
            } catch (e: FileNotFoundException) {
                Log.d(TAG, "File not found: " + e.message)
            } catch (e: IOException) {
                Log.d(TAG, "Error accessing file: " + e.message)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun recognize() {
        statusBar!!.visibility = View.VISIBLE
        val options = HashMap<String, String>()
        options.put("max_face_num", "5")
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities")
        face!!.faceRecognizeWithPath(mCurrentPhotoPath!!, options, object : Consumer<RecognizeBean> {
            override fun accept(t: RecognizeBean) {
                statusBar!!.visibility = View.INVISIBLE
                val result = t.result
                if (result == null || result.isEmpty()) {
                    cameraPreview!!.startPreviewDisplay()
                    return
                }
                val r = t.result!![0]
                Log.i(TAG, "accept: " + t.toString())
                UIUtil.showDialog(this@FaceActivity, "人脸识别结果: ", " gender: " + r.gender + ",\n age: " + r.age + ",\n beauty: " + r.beauty + ",\n glasses: " + r.glasses, "确定", "取消", false, object : Consumer<DialogInterface> {
                    override fun accept(t: DialogInterface) {
                        cameraPreview!!.startPreviewDisplay()
                    }
                })
            }
        })
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.SIMPLIFIED_CHINESE).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun getPermission() {
        // Assume thisActivity is the current activity
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        // if not get permission
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                println("shouldShowRequestPermissionRationale: true")
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission not get", Toast.LENGTH_SHORT).show()

            } else {
                println("shouldShowRequestPermissionRationale: false")
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    println("onRequestPermissionsResult: true")

                } else {
                    println("onRequestPermissionsResult: false")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    companion object {

        private val TAG = FaceActivity::class.java.simpleName
        private val PERMISSIONS_REQUEST_CAMERA = 1
        private val REQUEST_IMAGE_CAPTURE = 2

    }
}
