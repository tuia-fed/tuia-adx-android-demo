package com.dt.adx.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.dt.adx.R;
import com.mediamain.android.FoxSDK;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("NonConstantResourceId")
    public void click(View view){
        Intent intent =new Intent();
        switch (view.getId()){
            case R.id.btnReward:
                intent.setClass(this,RewardVideoActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421089);
                break;
            case R.id.btnFullScreen:
                intent.setClass(this,FullScreenVideoActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",419856);
                break;
            case R.id.btnSplash:
                intent.setClass(this,SplashActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421090);
                break;
            case R.id.btnBanner:
                intent.setClass(this,BannerActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421090);
                break;
            case R.id.btnTabScreen:
                intent.setClass(this,TabScreenActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",419855);
                break;
            case R.id.btnIcon:
                intent.setClass(this,IconActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",421090);
                break;
            case R.id.btnCustom:
                intent.setClass(this,CustomActivity.class);
                intent.putExtra("userId","test-1");
                intent.putExtra("slotId",419307);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}