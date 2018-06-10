package com.example.aixiaozi.xichexia.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.params.Codes;
import com.example.aixiaozi.xichexia.params.Elements;

import pub.devrel.easypermissions.EasyPermissions;

public class LaunchActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if (!EasyPermissions.hasPermissions(this, Elements.permissions)) {
            //如果没有这个权限，申请这个权限
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    Codes.PERMISSION_CODE, Elements.permissions);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                finish();
            }
        },0);
    }

    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (!b) {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions(this, new
                                String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
                        Codes.INSTALL_PACKAGES_REQUESTCODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Codes.GET_UNKNOWN_APP_SOURCES:
                checkIsAndroidO();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Codes.INSTALL_PACKAGES_REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    installApk();安装APK的逻辑
                } else {
                    if (Build.VERSION.SDK_INT >= 26) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                        startActivityForResult(intent, Codes.GET_UNKNOWN_APP_SOURCES);
                    }
                }
                break;
        }

        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

//    //成功
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> list) {
//        // Some permissions have been granted
//        // ...
//    }
//
//    //失败
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> list) {
//        // Some permissions have been denied
//        // ...
//    }
}
