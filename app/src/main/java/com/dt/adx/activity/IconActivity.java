package com.dt.adx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.banner.FoxADXBannerAd;
import com.mediamain.android.adx.view.icon.FoxADXIconAd;
import com.mediamain.android.adx.view.icon.FoxADXIconHolder;
import com.mediamain.android.adx.view.icon.FoxADXIconView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class IconActivity extends AppCompatActivity {

    private static final String TAG = IconActivity.class.getSimpleName();
    private FoxADXIconAd foxADXIconAd;
    private FrameLayout container;
    private String userId;
    private int slotId;
    private int price =100;
    private FoxADXIconHolder adxIconHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        if (getIntent()!=null){
             userId = getIntent().getStringExtra("userId");
             slotId = getIntent().getIntExtra("slotId", 0);
        }
        adxIconHolder = FoxNativeAdHelper.getADXIconHolder();
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adxIconHolder.loadAd(IconActivity.this, slotId, userId, 80, 80, 80, 80, 1, new FoxADXIconHolder.LoadAdListener() {
                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                        FoxBaseToastUtils.showShort("servingSuccessResponse: ");
                    }

                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: ");
                        FoxBaseToastUtils.showShort("onError: "+errorCode+errorBody);
                    }

                    @Override
                    public void onAdLoadFailed() {
                        Log.d(TAG, "onAdLoadFailed: ");
                        FoxBaseToastUtils.showShort("onAdLoadFailed: ");
                    }

                    @Override
                    public void onAdLoadSuccess() {
                        Log.d(TAG, "onAdLoadSuccess: ");
                        FoxBaseToastUtils.showShort("onAdLoadSuccess: ");
                    }

                    @Override
                    public void onAdCloseClick() {
                        Log.d(TAG, "onAdCloseClick: ");
                        FoxBaseToastUtils.showShort("onAdCloseClick: ");
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "onAdClick: ");
                        FoxBaseToastUtils.showShort("onAdClick: ");
                    }

                    @Override
                    public void onAdExposure() {
                        Log.d(TAG, "onAdExposure: ");
                        FoxBaseToastUtils.showShort("onAdExposure: ");

                    }

                    @Override
                    public void onAdActivityClose(String data) {
                        Log.d(TAG, "onAdActivityClose: ");
                        FoxBaseToastUtils.showShort("onAdActivityClose: ");

                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        Log.d(TAG, "onAdMessage: ");
                        FoxBaseToastUtils.showShort("onAdMessage: ");
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXIconAd adxIconAd) {
                        foxADXIconAd = adxIconAd;
                        FoxBaseToastUtils.showShort("onAdGetSuccess: price="+adxIconAd.getPrice());
                    }
                });
            }
        });
        findViewById(R.id.btnShow).setOnClickListener(v -> {
            if (foxADXIconAd!=null){
                FoxADXIconView foxADXIconView = (FoxADXIconView) foxADXIconAd.getView();
                foxADXIconView.show(100);
                ViewGroup contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.addView(foxADXIconView, params);
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (adxIconHolder!=null){
            adxIconHolder.destroy();
        }
        super.onDestroy();
    }
}