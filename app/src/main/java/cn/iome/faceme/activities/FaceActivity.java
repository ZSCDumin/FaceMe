package cn.iome.faceme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.iome.faceme.R;
import cn.iome.faceme.bean.FaceResultBean;
import cn.iome.faceme.bean.FaceUserBean;
import cn.iome.faceme.bean.GroupListBean;
import cn.iome.faceme.bean.GroupUserBean;
import cn.iome.faceme.bean.IdentifyResultBean;
import cn.iome.faceme.bean.QuickBean;
import cn.iome.faceme.bean.RecognizeBean;
import cn.iome.faceme.function.Consumer;
import cn.iome.faceme.utility.Constants;
import cn.iome.faceme.utility.FaceManager;

/**
 * Created by haoping on 17/4/10.
 * uid:
 */
public class FaceActivity extends AppCompatActivity {

    private static final String TAG = FaceActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private FaceManager face;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        face = FaceManager.getFace();
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakeActivity.class);
        startActivity(intent);
    }

    public void quick(View view) {
        face.quick(Constants.test1, new Consumer<QuickBean>() {
            @Override
            public void accept(QuickBean quickBean) {
                if (quickBean != null) {
                    Log.i(TAG, "accept: " + quickBean.toString());
                }
            }
        });
    }

    public void faceRecognizeWithPath(View view) {
        HashMap<String, String> options = new HashMap<>();
        options.put("max_face_num", "5");
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
        face.faceRecognizeWithPath(Constants.test1, options, new Consumer<RecognizeBean>() {
            @Override
            public void accept(RecognizeBean recognizeBean) {
                Log.i(TAG, "accept: " + recognizeBean.toString());
            }
        });
    }

    public void faceRecognizeWithBytes(View view) {
        HashMap<String, String> options = new HashMap<>();
        options.put("max_face_num", "5");
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
        face.faceRecognizeWithBytes(face.readImageFile(Constants.test1), options, new Consumer<RecognizeBean>() {
            @Override
            public void accept(RecognizeBean recognizeBean) {
                Log.i(TAG, "accept: " + recognizeBean.toString());
            }
        });
    }

    public void facesetAddUser(View view) {
        // 参数为本地图片路径
        ArrayList<String> imgPaths = new ArrayList<>();
        imgPaths.add(Constants.test1);
        imgPaths.add(Constants.test2);
        face.facesetAddUser("uid1", "first", "group1", imgPaths, new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }

    public void facesetUpdateUser(View view) {
        // 参数为本地图片路径
        ArrayList<String> imgPaths = new ArrayList<>();
        imgPaths.add(Constants.test1);
        imgPaths.add(Constants.test2);
        face.facesetUpdateUser("uid1", imgPaths, new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }

    public void facesetDeleteUser(View view) {
        face.facesetDeleteUser("uid1", new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }

    public void verifyUser(View view) {
        ArrayList<String> path = new ArrayList<>();
        path.add(Constants.test1);
        path.add(Constants.test2);
        HashMap<String, Object> options = new HashMap<>(1);
        options.put("top_num", path.size());
        face.verifyUser("uid1", path, options, new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }

    public void identifyUser(View view) {
        ArrayList<String> path = new ArrayList<>();
        path.add(Constants.test1);
        //path.add(test2);
        HashMap<String, Object> options = new HashMap<>(1);
        options.put("user_top_num", path.size());
        options.put("face_top_num", 10);
        face.identifyUser("group1", path, options, new Consumer<IdentifyResultBean>() {
            @Override
            public void accept(IdentifyResultBean identifyResultBean) {
                Log.i(TAG, "accept: " + identifyResultBean.toString());
            }
        });
    }

    public void getUser(View view) {
        face.getUser("uid1", new Consumer<FaceUserBean>() {
            @Override
            public void accept(FaceUserBean faceUserBean) {
                Log.i(TAG, "accept: " + faceUserBean.toString());
            }
        });
    }

    public void getGroupList(View view) {
        HashMap<String, Object> options = new HashMap<>(2);
        options.put("start", 0);
        options.put("num", 10);
        face.getGroupList(options, new Consumer<GroupListBean>() {
            @Override
            public void accept(GroupListBean groupListBean) {
                Log.i(TAG, "accept: " + groupListBean.toString());
            }
        });
    }

    public void getGroupUsers(View view) {
        HashMap<String, Object> options = new HashMap<>(2);
        options.put("start", 0);
        options.put("num", 10);
        face.getGroupUsers("group1", options, new Consumer<GroupUserBean>() {
            @Override
            public void accept(GroupUserBean groupUserBean) {
                Log.i(TAG, "accept: " + groupUserBean.toString());
            }
        });
    }

    public void addGroupUser(View view) {
        face.addGroupUser("group1", "uid1", new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }

    public void deleteGroupUser(View view) {
        face.deleteGroupUser("group1", "uid1", new Consumer<FaceResultBean>() {
            @Override
            public void accept(FaceResultBean faceResultBean) {
                Log.i(TAG, "accept: " + faceResultBean.toString());
            }
        });
    }


    private void getPermission() {
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // if not get permission
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("shouldShowRequestPermissionRationale: true");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission not get", Toast.LENGTH_SHORT).show();

            } else {
                System.out.println("shouldShowRequestPermissionRationale: false");
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
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
