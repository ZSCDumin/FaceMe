package cn.iome.fakotlin.view

import android.content.Context
import android.content.res.Configuration
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

import java.io.IOException

/**
 * Created by haoping on 17/5/3.
 * TODO
 */
class CameraView : SurfaceView, SurfaceHolder.Callback {
    private var mCamera: Camera? = null
    private var mSurfaceHolder: SurfaceHolder? = null

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mSurfaceHolder = this.holder
        mSurfaceHolder!!.addCallback(this)
    }

    constructor(context: Context, camera: Camera) : super(context) {
        this.mCamera = camera
        mSurfaceHolder = this.holder
        mSurfaceHolder!!.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "Start preview display[SURFACE-CREATED]")
        startPreviewDisplay(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mSurfaceHolder!!.surface == null) {
            return
        }
        followScreenOrientation(context, mCamera!!)
        Log.d(TAG, "Restart preview display[SURFACE-CHANGED]")
        stopPreviewDisplay()
        startPreviewDisplay(mSurfaceHolder!!)
    }

    fun setCamera(camera: Camera) {
        mCamera = camera
        val params = mCamera!!.parameters
        params.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
        params.sceneMode = Camera.Parameters.SCENE_MODE_BARCODE
    }

    private fun startPreviewDisplay(holder: SurfaceHolder) {
        checkCamera()
        try {
            mCamera!!.setPreviewDisplay(holder)
            mCamera!!.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error while START preview for camera", e)
        }

    }

    fun startPreviewDisplay() {
        checkCamera()
        try {
            mCamera!!.setPreviewDisplay(mSurfaceHolder)
            mCamera!!.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error while START preview for camera", e)
        }

    }

    fun stopPreviewDisplay() {
        checkCamera()
        try {
            mCamera!!.stopPreview()
        } catch (e: Exception) {
            Log.e(TAG, "Error while STOP preview for camera", e)
        }

    }

    private fun checkCamera() {
        if (mCamera == null) {
            throw IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set")
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "Stop preview display[SURFACE-DESTROYED]")
        stopPreviewDisplay()
    }

    fun followScreenOrientation(context: Context, camera: Camera) {
        val orientation = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(180)
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90)
        }
    }

    companion object {
        private val TAG = CameraView::class.java.simpleName
    }
}
