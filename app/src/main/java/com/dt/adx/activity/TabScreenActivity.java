package com.dt.adx.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenAd;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenHolder;
import com.mediamain.android.adx.view.tabscreen.FoxADXTbScreen;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class TabScreenActivity extends AppCompatActivity {

    private static final String TAG = TabScreenActivity.class.getSimpleName();

    FoxADXTabScreenHolder tabScreenVideoHolder;
    private FrameLayout mContainer;
    private int slotId;
    private String userId;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tab_screnn);
        activity = this;
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAd();
            }
        });
        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foxADXTbScreen != null){
                    foxADXTbScreen.show(100);
                }
            }
        });
    }

    private FoxADXTbScreen foxADXTbScreen;
    private void getAd() {
        tabScreenVideoHolder = FoxNativeAdHelper.getADXTabScreenVideoHolder();
        tabScreenVideoHolder.loadAd(TabScreenActivity.this,slotId, userId,200,200,
                new FoxADXTabScreenHolder.LoadAdListener() {

                    @Override
                    public void onAdTimeOut() {
                        Log.d(TAG, "onAdTimeOut: ");
                        FoxBaseToastUtils.showShort("onAdTimeOut");

                    }

                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: ");
                        FoxBaseToastUtils.showShort("onError");
                    }

                    @Override
                    public void onAdLoadFailed() {
                        Log.d(TAG, "onAdLoadFailed: ");
                        FoxBaseToastUtils.showShort("onAdLoadFailed");
                    }

                    @Override
                    public void onAdLoadSuccess() {
                        Log.d(TAG, "onAdLoadSuccess: ");
                        FoxBaseToastUtils.showShort("onAdLoadSuccess");
                    }

                    @Override
                    public void onAdCacheSuccess(String id) {
                        FoxBaseToastUtils.showShort("onAdCacheSuccess");
                    }

                    @Override
                    public void onAdCacheCancel(String id) {

                    }

                    @Override
                    public void onAdCacheFail(String id) {

                    }

                    @Override
                    public void onAdCacheEnd(String id) {

                    }

                    @Override
                    public void onAdCloseClick() {
                        Log.d(TAG, "onAdCloseClick: ");
                        FoxBaseToastUtils.showShort("onAdCloseClick");
                        jumpMain();
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "onAdClick: ");
                        FoxBaseToastUtils.showShort("onAdClick");

                    }

                    @Override
                    public void onAdExposure() {
                        Log.d(TAG, "onAdExposure: ");
                        FoxBaseToastUtils.showShort("onAdExposure");
                    }

                    @Override
                    public void onAdActivityClose(String data) {
                        Log.d(TAG, "onAdActivityClose: ");
                        FoxBaseToastUtils.showShort("onAdActivityClose");

                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        Log.d(TAG, "onAdMessage: ");
                        FoxBaseToastUtils.showShort("onAdMessage");
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                        FoxBaseToastUtils.showShort("servingSuccessResponse");
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXTabScreenAd foxADXTabScreenAd) {
                        foxADXTbScreen = foxADXTabScreenAd.get();
                        FoxBaseToastUtils.showShort("onAdGetSuccess price="+foxADXTabScreenAd.getPrice());
                    }
                });
    }

    /**
     * 跳转主页
     */
    private void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        if (tabScreenVideoHolder != null) {
            tabScreenVideoHolder.destroy();
        }
        super.onDestroy();
    }
}