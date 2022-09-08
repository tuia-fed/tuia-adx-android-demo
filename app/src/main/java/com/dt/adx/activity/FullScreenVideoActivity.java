package com.dt.adx.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.base.FoxADXConstant;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoAd;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoHolder;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoHolderImpl;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
/**
 * 请求广告             getAd()
 * 获取竞价价格          getECPM();
 * 设置竞胜价格展示广告   openAd()
 * 广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 * 销毁广告组件          destroy();
 */
public class FullScreenVideoActivity extends AppCompatActivity {


    private static final String TAG = FullScreenVideoActivity.class.getSimpleName();

    FoxADXFullScreenVideoHolderImpl nativeIVideoHolder;
    private int slotId;
    private String userId;
    private Activity mActivity;
    private final boolean isCached = true;
    private FoxADXFullScreenVideoAd adxFullScreenVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        mActivity =this;
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(v -> getAd());
        findViewById(R.id.btnShow).setOnClickListener(v -> openAd());
    }

    private void getAd() {
        nativeIVideoHolder = (FoxADXFullScreenVideoHolderImpl) FoxNativeAdHelper.getADXFullScreenHolder();
        //默认缓存模式 可通过配置设置直接加载广告
        nativeIVideoHolder.setCached(isCached);
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXFullScreenVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
                Log.d(TAG, "onAdGetSuccess: ");
                FoxBaseToastUtils.showShort("广告获取成功");
                if (foxADXFullScreenVideoAd != null) {
                    adxFullScreenVideoAd = foxADXFullScreenVideoAd;
                    foxADXFullScreenVideoAd.getECPM();
                    //在线模式 可能因为网络原因播放卡顿
                    if (!isCached){
                        openAd();
                    }
                }
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheSuccess: ");
                FoxBaseToastUtils.showShort("广告缓存成功");
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
                FoxBaseToastUtils.showShort("广告获取失败"+errorCode+errorBody);
            }
        });
    }

    private void openAd() {
        //打开广告
        if (adxFullScreenVideoAd!=null) {
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
            //设置竞胜价格
            adxFullScreenVideoAd.setWinPrice(FoxSDK.getSDKName(),adxFullScreenVideoAd.getECPM(), FoxADXConstant.CURRENCY.RMB);
            //打开全屏视频广告
            adxFullScreenVideoAd.openActivity();
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