package com.dt.adx.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoAd;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoHolder;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoHolderImpl;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.interfaces.FoxVideoListener;

public class FullScreenVideoActivity extends AppCompatActivity {


    private static final String TAG = FullScreenVideoActivity.class.getSimpleName();

    FoxADXFullScreenVideoHolderImpl nativeIVideoHolder;
    private int slotId;
    private String userId;
    private String mUrl;
    private int price = 100;
    private Activity mActivity;
    private final boolean isCached = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        mActivity =this;
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAd();
            }
        });
        findViewById(R.id.btnShow).setOnClickListener(v -> {
            openAd();
        });
    }


    private FoxADXFullScreenVideoAd adxFullScreenVideoAd;
    private FoxADXADBean mFoxADXADBean;
    private void getAd() {
        nativeIVideoHolder = (FoxADXFullScreenVideoHolderImpl) FoxNativeAdHelper.getADXFullScreenHolder();
        //默认缓存模式 可通过配置设置直接加载广告
        nativeIVideoHolder.setCached(false);
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXFullScreenVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
                Log.d(TAG, "onAdGetSuccess: ");
                if (foxADXFullScreenVideoAd != null) {
                    adxFullScreenVideoAd = foxADXFullScreenVideoAd;
                    mFoxADXADBean = foxADXFullScreenVideoAd.getFoxADXADBean();
                    //在线模式 可能因为网络原因播放卡顿
                    if (!isCached){
                        openAd();
                    }
                }
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheSuccess: ");
                mFoxADXADBean = foxADXADBean;
                //缓存模式 先缓存本地视频 再播放不会卡顿
                if (isCached){
                    openAd();
                }
            }

            @Override
            public void onAdCacheCancel(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheCancel: ");
            }

            @Override
            public void onAdCacheFail(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheFail: ");
            }

            @Override
            public void onAdCacheEnd(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheEnd: ");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                Log.d(TAG, "onError: "+errorCode+errorBody);
            }
        });
    }

    private void openAd() {
        //打开广告
        if (mFoxADXADBean!=null && adxFullScreenVideoAd!=null) {
            adxFullScreenVideoAd.setLoadVideoAdInteractionListener(new FoxADXFullScreenVideoAd.LoadVideoAdInteractionListener() {
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
                }

                @Override
                public void onAdJumpClick() {
                    Log.d(TAG, "onAdJumpClick: ");
                }

                @Override
                public void onAdCloseClick() {
                    Log.d(TAG, "onAdCloseClick: ");
                }

                @Override
                public void onAdActivityClose(String data) {
                    Log.d(TAG, "onAdActivityClose: ");
                }

                @Override
                public void onAdMessage(MessageData data) {
                    Log.d(TAG, "onAdMessage: ");
                }
            });
            //设置竞价胜出价格
            mFoxADXADBean.setPrice(price);
            //打开全屏视频广告
            adxFullScreenVideoAd.openActivity(mFoxADXADBean);
        }else {
            FoxBaseToastUtils.showShort("等待广告请求成功。。。");
        }
    }

    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
}