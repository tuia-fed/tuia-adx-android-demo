package com.dt.adx.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoAd;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolder;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.interfaces.FoxVideoListener;

public class RewardVideoActivity extends AppCompatActivity {

    private static final String TAG = RewardVideoActivity.class.getSimpleName();

    FoxADXRewardVideoHolder nativeIVideoHolder;
    private int slotId;
    private String userId;
    private Activity mActivity;
    private FoxADXADBean mFoxADXADBean;
    /**
     * 竞价价格
     * /分 
     */
    private int price =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        mActivity =this;
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(v -> {
            getAd();
        });

        findViewById(R.id.btnShow).setOnClickListener(v -> {
            if (mFoxADXADBean!=null && mFoxADXRewardVideoAd!=null){
                mFoxADXRewardVideoAd.setLoadVideoAdInteractionListener(new FoxADXRewardVideoAd.LoadVideoAdInteractionListener() {
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
                    public void onAdReward(boolean isReward) {
                        FoxBaseToastUtils.showShort(mActivity,"onAdReward");

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
                mFoxADXRewardVideoAd.openActivity(mFoxADXADBean);
            }
        });
    }


    private FoxADXRewardVideoAd mFoxADXRewardVideoAd;
    private void getAd() {
        nativeIVideoHolder = FoxNativeAdHelper.getADXRewardVideoHolder();
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXRewardVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXRewardVideoAd foxADXRewardVideoAd) {
                mFoxADXRewardVideoAd = foxADXRewardVideoAd;
                FoxBaseToastUtils.showShort(mActivity,"onAdGetSuccess price="+foxADXRewardVideoAd.getECPM());
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort(mActivity,"onAdCacheSuccess");
                mFoxADXADBean = foxADXADBean;
            }

            @Override
            public void onAdCacheCancel(String id) {
                FoxBaseToastUtils.showShort(mActivity,"onAdCacheCancel");
            }

            @Override
            public void onAdCacheFail(String id) {
                FoxBaseToastUtils.showShort(mActivity,"onAdCacheFail");
            }

            @Override
            public void onAdCacheEnd(String id) {
                FoxBaseToastUtils.showShort(mActivity,"onAdCacheEnd");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort(mActivity,"servingSuccessResponse ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                Log.d(TAG, "onError: "+errorCode+errorBody);
                FoxBaseToastUtils.showShort(mActivity,"onError "+errorCode+errorBody);
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