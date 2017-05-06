package cn.iome.fakotlin.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import cn.iome.fakotlin.R
import cn.iome.fakotlin.bean.*
import cn.iome.fakotlin.function.Consumer
import cn.iome.fakotlin.utility.Constants
import cn.iome.fakotlin.utility.FaceManager
import java.util.*

/**
 * Created by haoping on 17/4/10.
 * uid:
 */
class DemoActivity : AppCompatActivity() {
    private var face: FaceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPermission()
        face = FaceManager.creat()
    }

    fun takePhoto() {
        val intent = Intent(this, FaceActivity::class.java)
        startActivity(intent)
    }

    fun quick() {
        face!!.quick(Constants.test1, object : Consumer<QuickBean> {
            override fun accept(t: QuickBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun faceRecognizeWithPath() {
        val options = HashMap<String, String>()
        options.put("max_face_num", "5")
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities")
        face!!.faceRecognizeWithPath(Constants.test1, options, object : Consumer<RecognizeBean> {
            override fun accept(t: RecognizeBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun faceRecognizeWithBytes() {
        val options = HashMap<String, String>()
        options.put("max_face_num", "5")
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities")
        face!!.faceRecognizeWithBytes(face!!.readImageFile(Constants.test1), options, object : Consumer<RecognizeBean> {
            override fun accept(t: RecognizeBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun facesetAddUser() {
        // 参数为本地图片路径
        val imgPaths = ArrayList<String>()
        imgPaths.add(Constants.test1)
        imgPaths.add(Constants.test2)
        face!!.facesetAddUser("uid1", "first", "group1", imgPaths, object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun facesetUpdateUser() {
        // 参数为本地图片路径
        val imgPaths = ArrayList<String>()
        imgPaths.add(Constants.test1)
        imgPaths.add(Constants.test2)
        face!!.facesetUpdateUser("uid1", imgPaths, object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun facesetDeleteUser() {
        face!!.facesetDeleteUser("uid1", object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun verifyUser() {
        val path = ArrayList<String>()
        path.add(Constants.test1)
        path.add(Constants.test2)
        val options = HashMap<String, Any>(1)
        options.put("top_num", path.size)
        face!!.verifyUser("uid1", path, options, object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun identifyUser() {
        val path = ArrayList<String>()
        path.add(Constants.test1)
        //path.add(test2);
        val options = HashMap<String, Any>(1)
        options.put("user_top_num", path.size)
        options.put("face_top_num", 10)
        face!!.identifyUser("group1", path, options, object : Consumer<IdentifyResultBean> {
            override fun accept(t: IdentifyResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun getUser() {
        face!!.getUser("uid1", object : Consumer<FaceUserBean> {
            override fun accept(t: FaceUserBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun getGroupList() {
        val options = HashMap<String, Any>(2)
        options.put("start", 0)
        options.put("num", 10)
        face!!.getGroupList(options, object : Consumer<GroupListBean> {
            override fun accept(t: GroupListBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun getGroupUsers() {
        val options = HashMap<String, Any>(2)
        options.put("start", 0)
        options.put("num", 10)
        face!!.getGroupUsers("group1", options, object : Consumer<GroupUserBean> {
            override fun accept(t: GroupUserBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun addGroupUser() {
        face!!.addGroupUser("group1", "uid1", object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }

    fun deleteGroupUser() {
        face!!.deleteGroupUser("group1", "uid1", object : Consumer<FaceResultBean> {
            override fun accept(t: FaceResultBean) {
                Log.i(TAG, "accept: " + t.toString())
            }
        })
    }


    private fun getPermission() {
        // Assume thisActivity is the current activity
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // if not get permission
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                println("shouldShowRequestPermissionRationale: true")
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission not get", Toast.LENGTH_SHORT).show()

            } else {
                println("shouldShowRequestPermissionRationale: false")
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
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

        private val TAG = DemoActivity::class.java.simpleName
        private val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }


}
