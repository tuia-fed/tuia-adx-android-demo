package com.dt.adx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.banner.FoxADXBannerAd;
import com.mediamain.android.adx.view.banner.FoxADXBannerHolder;
import com.mediamain.android.adx.view.banner.FoxADXBannerView;
import com.mediamain.android.view.base.FoxSize;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class BannerActivity extends AppCompatActivity {

    private static final String TAG = BannerActivity.class.getSimpleName();

    private FrameLayout container;
    private String userId;
    private int slotId;
    private FoxADXBannerAd mBannerAd;
    private int price =100;
    private  FoxADXBannerHolder adxBannerHolder;
    private  FoxADXADBean mFoxADXADBean;
    private FoxADXBannerView mFoxADXBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        container = (FrameLayout) findViewById(R.id.container);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        Button btnShow = (Button) findViewById(R.id.btnShow);
        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        adxBannerHolder = FoxNativeAdHelper.getADXBannerHolder();
        btnShow.setOnClickListener(v -> {
            if (mBannerAd!=null &&  mBannerAd.getView() instanceof FoxADXBannerView ){
                mFoxADXBannerView = (FoxADXBannerView) mBannerAd.getView();
                container.removeAllViews();
                container.addView(mFoxADXBannerView);
                if (mFoxADXADBean!=null){
                    mFoxADXADBean.setPrice(price);
                }
                mFoxADXBannerView.show(mFoxADXADBean);
            }
        });
        btnRequest.setOnClickListener(v -> {
            try {
                adxBannerHolder.loadAd(BannerActivity.this, slotId, FoxSize.LANDER_TMBr,new FoxADXBannerHolder.LoadAdListener() {
                    @Override
                    public void onAdGetSuccess(FoxADXBannerAd bannerAd) {
                        Log.d(TAG, "onAdGetSuccess: ");
                        mBannerAd = bannerAd;
                    }

                    @Override
                    public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                        Log.d(TAG, "onAdCacheSuccess: ");
                        mFoxADXADBean = foxADXADBean;
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "servingSuccessResponse");
                    }


                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onError"+errorCode+errorBody);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adxBannerHolder!=null){
            adxBannerHolder.destroy();
        }
        if (mFoxADXBannerView!=null){
            mFoxADXBannerView.destroy();
        }
    }

}