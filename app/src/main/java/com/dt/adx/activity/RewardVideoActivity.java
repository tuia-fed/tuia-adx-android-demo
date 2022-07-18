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
                    public void onAdReward(boolean isReward) {
                        Log.d(TAG, "onAdReward: =="+isReward);

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
                mFoxADXRewardVideoAd.openActivity(mFoxADXADBean);
            }else {
                FoxBaseToastUtils.showShort("等待缓存成功再播放");
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
                Log.d(TAG, "onAdGetSuccess: ");
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheSuccess: ");
                mFoxADXADBean = foxADXADBean;
            }

            @Override
            public void onAdCacheCancel(String id) {
                Log.d(TAG, "onAdCacheCancel: ");
            }

            @Override
            public void onAdCacheFail(String id) {
                Log.d(TAG, "onAdCacheFail: ");
            }

            @Override
            public void onAdCacheEnd(String id) {
                Log.d(TAG, "onAdCacheEnd: ");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
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