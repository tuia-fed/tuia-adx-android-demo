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
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.interfaces.FoxVideoListener;

public class FullScreenVideoActivity extends AppCompatActivity {


    private static final String TAG = FullScreenVideoActivity.class.getSimpleName();

    FoxADXFullScreenVideoHolder nativeIVideoHolder;
    private int slotId;
    private String userId;
    private String mUrl;
    private int price = 100;
    private Activity mActivity;

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
            if (mFoxADXADBean != null && adxFullScreenVideoAd!=null) {
                adxFullScreenVideoAd.setLoadVideoAdInteractionListener(new FoxADXFullScreenVideoAd.LoadVideoAdInteractionListener() {
                    @Override
                    public void onAdLoadFailed() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdLoadFailed");
                    }

                    @Override
                    public void onAdLoadSuccess() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdLoadSuccess");
                    }

                    @Override
                    public void onAdClick() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdClick");
                    }

                    @Override
                    public void onAdExposure() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdExposure");
                    }

                    @Override
                    public void onAdTimeOut() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdTimeOut");
                    }

                    @Override
                    public void onAdJumpClick() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdJumpClick");
                    }

                    @Override
                    public void onAdCloseClick() {
                        FoxBaseToastUtils.showShort(mActivity,"onAdCloseClick");
                    }

                    @Override
                    public void onAdActivityClose(String data) {
                        FoxBaseToastUtils.showShort(mActivity,"onAdActivityClose");
                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        FoxBaseToastUtils.showShort(mActivity,"onAdMessage");
                    }
                });
                //设置竞价胜出价格
                mFoxADXADBean.setPrice(price);
                //打开全屏视频广告
                adxFullScreenVideoAd.openActivity(mFoxADXADBean);
            }else {
                FoxBaseToastUtils.showShort("等待缓存成功再播放");
            }
        });
    }


    private FoxADXFullScreenVideoAd adxFullScreenVideoAd;
    private FoxADXADBean mFoxADXADBean;
    private void getAd() {
        nativeIVideoHolder = FoxNativeAdHelper.getADXFullScreenHolder();
        nativeIVideoHolder.loadAd(slotId, userId, true, new FoxADXFullScreenVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdGetSuccess");
                if (foxADXFullScreenVideoAd != null) {
                    adxFullScreenVideoAd = foxADXFullScreenVideoAd;
                }
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort(mActivity, "onAdCacheSuccess");
                mFoxADXADBean = foxADXADBean;
            }

            @Override
            public void onAdCacheCancel(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort(mActivity, "onAdCacheCancel");
            }

            @Override
            public void onAdCacheFail(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort(mActivity, "onAdCacheFail");
            }

            @Override
            public void onAdCacheEnd(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort(mActivity, "onAdCacheEnd");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort(mActivity, "servingSuccessResponse");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort(mActivity, "onError" + errorCode + errorBody);
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
}