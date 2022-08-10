package com.dt.adx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.splash.FoxADXShView;
import com.mediamain.android.adx.view.splash.FoxADXSplashAd;
import com.mediamain.android.adx.view.splash.FoxADXSplashHolder;
import com.mediamain.android.adx.view.splash.FoxADXSplashHolderImpl;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 * 请求广告             getAd()
 * 获取竞价价格          getECPM();
 * 设置竞胜价格展示广告   openAd()
 * 销毁广告组件          destroy();
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName()+"====";

    private FoxADXSplashHolderImpl adxSplashHolder;
    private int slotId =  421090;
    private String userId;
    private FoxADXShView foxADXShView;
    /**
     * 竞胜价格设置
     */
    private int price =100;
    private FoxADXADBean mFoxADXADBean;
    private boolean isCached = true;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_splash);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }

        findViewById(R.id.btnRequest).setOnClickListener(v -> getAd());
        aSwitch = findViewById(R.id.switch_play);
    }


    private void getAd() {
        isCached = !aSwitch.isChecked();
        adxSplashHolder = (FoxADXSplashHolderImpl) FoxNativeAdHelper.getADXSplashHolder();
        //控制是否需要缓存广告  true 会走onAdGetSuccess()和onAdCacheSuccess()回调接口  false 只会走onAdGetSuccess()
        adxSplashHolder.setCached(isCached);
        adxSplashHolder.loadAd(slotId, userId, new FoxADXSplashHolder.LoadAdListener() {
            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
            }

            @Override
            public void onError(int code, String msg) {
                Log.d(TAG, "onError: "+code+msg);
                FoxBaseToastUtils.showShort("广告缓存失败 onError"+code+msg);
            }

            @Override
            public void onAdGetSuccess(FoxADXSplashAd foxADXSplashAd) {
                FoxBaseToastUtils.showShort("广告获取成功");
                if (foxADXSplashAd!=null){
                    Log.d(TAG, "onAdGetSuccess: "+ foxADXSplashAd.getECPM());
                    foxADXShView = (FoxADXShView) foxADXSplashAd.getView();
                    mFoxADXADBean = foxADXSplashAd.getFoxADXADBean();
                    //获取竞价价格
                    foxADXSplashAd.getECPM();
                    if (!isCached){
                        openAD();
                    }
                }
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort("广告缓存成功");
                Log.d(TAG, "onAdCacheSuccess: "+foxADXADBean.getRequestTid());
                FoxBaseToastUtils.showShort("onAdCacheSuccess ");
                mFoxADXADBean = foxADXADBean;
                if (isCached){
                    openAD();
                }
            }

            @Override
            public void onAdCacheCancel(FoxADXADBean foxADXADBean) {

            }

            @Override
            public void onAdCacheFail(FoxADXADBean foxADXADBean) {

            }

            @Override
            public void onAdCacheEnd(FoxADXADBean foxADXADBean) {

            }
        });
    }

    private void openAD() {
        if (mFoxADXADBean!=null){
            ViewGroup contentView = findViewById(android.R.id.content);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            contentView.removeAllViews();
            contentView.addView(foxADXShView, params);
            mFoxADXADBean.setPrice(price);
            foxADXShView.setAdListener(new FoxADXSplashAd.LoadAdInteractionListener() {
                @Override
                public void onAdLoadFailed() {
                    Log.d(TAG, "onAdLoadFailed: ");
                }

                @Override
                public void onAdLoadSuccess() {
                    Log.d(TAG, "onAdLoadSuccess: ");
                }

                @Override
                public void onAdClick() {
                    Log.d(TAG, "onAdClick: ");
                }

                @Override
                public void onAdExposure() {
                    Log.d(TAG, "onAdExposure: ");
                }

                @Override
                public void onAdTimeOut() {
                    Log.d(TAG, "onAdTimeOut: ");
                    jumpMain();
                }

                @Override
                public void onAdJumpClick() {
                    Log.d(TAG, "onAdJumpClick: ");
                    jumpMain();
                }

                @Override
                public void onAdActivityClose(String s) {
                    Log.d(TAG, "onAdActivityClose: ");
                    jumpMain();
                }

                @Override
                public void onAdMessage(MessageData messageData) {
                    Log.d(TAG, "onAdMessage: ");
                }

                @Override
                public void servingSuccessResponse(BidResponse bidResponse) {
                    Log.d(TAG, "servingSuccessResponse: ");
                }

                @Override
                public void onError(int i, String s) {
                    Log.d(TAG, "onError: ");
                }
            });
            foxADXShView.showAd(SplashActivity.this,mFoxADXADBean);
        }
    }

    @Override
    protected void onDestroy() {
        if (adxSplashHolder != null) {
            adxSplashHolder.destroy();
        }
        if (foxADXShView!=null){
            foxADXShView.destroy();
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
}