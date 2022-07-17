package com.dt.adx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.splash.FoxADXShView;
import com.mediamain.android.adx.view.splash.FoxADXSplashAd;
import com.mediamain.android.adx.view.splash.FoxADXSplashHolder;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName()+"====";

    private FrameLayout mContainer;
    private FoxADXSplashHolder adxSplashHolder;
    private FoxADXSplashHolder.LoadAdListener mSplashAdListener;
    private int slotId =  421090;
    private String userId;
    private FoxADXShView foxADXShView;
    private int price =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_splash);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        adxSplashHolder = FoxNativeAdHelper.getADXSplashHolder();
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAd();
            }
        });
        registerReceiver(receiver, new IntentFilter("broadsend.action"));
    }

    private void getAd() {
        adxSplashHolder.loadAd(421090, userId, new FoxADXSplashHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXSplashAd foxADXSplashAd) {
                if (foxADXSplashAd != null) {
                    foxADXSplashAd.setScaleType(ImageView.ScaleType.FIT_XY);
                    foxADXShView = (FoxADXShView) foxADXSplashAd.getView();
//                    FoxBaseToastUtils.showShort("onAdGetSuccess price="+foxADXSplashAd.getPrice());
                }
            }

            @Override
            public void onAdTimeOut() {
                FoxBaseToastUtils.showShort("onAdTimeOut ");
                jumpMain();
            }

            @Override
            public void onAdCacheSuccess(String id) {
                FoxBaseToastUtils.showShort("onAdCacheSuccess ");
                ViewGroup contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.addView(foxADXShView, params);
                foxADXShView.showAd(SplashActivity.this,null,price);
            }

            @Override
            public void onAdCacheCancel(String id) {

            }

            @Override
            public void onAdCacheFail(String id) {
                  jumpMain();
            }

            @Override
            public void onAdCacheEnd(String id) {

            }

            @Override
            public void onAdJumpClick() {
                FoxBaseToastUtils.showShort("onAdJumpClick ");
                jumpMain();
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort("servingSuccessResponse ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort("onError "+errorCode+errorBody);
            }

            @Override
            public void onAdLoadFailed() {
                FoxBaseToastUtils.showShort("onAdLoadFailed ");
            }

            @Override
            public void onAdLoadSuccess() {
                FoxBaseToastUtils.showShort("onAdLoadSuccess ");
            }

            @Override
            public void onAdCloseClick() {
                FoxBaseToastUtils.showShort("onAdCloseClick ");

            }

            @Override
            public void onAdClick() {
                FoxBaseToastUtils.showShort("onAdClick ");

            }

            @Override
            public void onAdExposure() {
                FoxBaseToastUtils.showShort("onAdExposure ");
            }

            @Override
            public void onAdActivityClose(String data) {
                FoxBaseToastUtils.showShort("onAdActivityClose ");
                jumpMain();
            }

            @Override
            public void onAdMessage(MessageData data) {
                FoxBaseToastUtils.showShort("onAdMessage ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        if (adxSplashHolder != null) {
            adxSplashHolder.destroy();
        }
        super.onDestroy();
    }

    /**
     * 跳转主页
     */
    private void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (adxSplashHolder != null) {
                int tag = intent.getIntExtra("Tag", 0);
                if (tag == 0) {
                    return;
                }
                FoxBaseToastUtils.showShort(context, "回传的type:" + tag);
                adxSplashHolder.sendMessage(tag, "");
            }
        }
    };
}