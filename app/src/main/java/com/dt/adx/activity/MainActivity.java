package com.dt.adx.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.dt.adx.R;

public class MainActivity extends AppCompatActivity {

    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!lacksPermissions(this, NEEDED_PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, 0);
            }
        }
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return false-表示没有改权限  true-表示权限已开启
     */
    public boolean lacksPermissions(Context mContexts, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void click(View view){
        Intent intent =new Intent();
        switch (view.getId()){
            case R.id.btnReward:
                intent.setClass(this,RewardVideoActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",393342);
                break;
            case R.id.btnFullScreen:
                intent.setClass(this,FullScreenVideoActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",393341);
                break;
            case R.id.btnSplash:
                intent.setClass(this,SplashActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421490);
                break;
            case R.id.btnTabScreen:
                intent.setClass(this,TabScreenActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421494);
                break;
            case R.id.btnBanner:
                intent.setClass(this,BannerActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",371144);
                break;
            case R.id.btnIcon:
                intent.setClass(this,IconActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",399517);
                break;
            case R.id.btnCustom:
                intent.setClass(this,CustomActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",423558);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}