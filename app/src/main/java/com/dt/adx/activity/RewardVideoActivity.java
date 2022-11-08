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
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoAd;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolder;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolderImpl;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 *  请求广告             getAd()
 *  获取竞价价格          getECPM();
 *  设置竞胜价格展示广告   openAd()
 *  广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 *  销毁广告组件          destroy();
 */
public class RewardVideoActivity extends AppCompatActivity {

    private static final String TAG = RewardVideoActivity.class.getSimpleName();

    FoxADXRewardVideoHolderImpl nativeIVideoHolder;
    private FoxADXRewardVideoAd mFoxADXRewardVideoAd;
    private int slotId;
    private String userId;
    private Activity mActivity;
    /**
     * 竞胜价格设置
     */
    private int price;
    private final boolean isCached = true;

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
            openAD();
        });
    }

    private void openAD() {
        if (mFoxADXRewardVideoAd!=null){
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
                    if (isReward){
                        FoxBaseToastUtils.showShort("获得奖励");
                    } else {
                        FoxBaseToastUtils.showShort("没有奖励");
                    }
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
            mFoxADXRewardVideoAd.setDefaultJump(true);
            mFoxADXRewardVideoAd.setWinPrice(FoxSDK.getSDKName(),mFoxADXRewardVideoAd.getECPM(), FoxADXConstant.CURRENCY.RMB);
            //打开视频广告
            mFoxADXRewardVideoAd.openActivity();
        }else {
            FoxBaseToastUtils.showShort("等待广告请求成功。。。");
        }
    }

    private void getAd() {
        nativeIVideoHolder = (FoxADXRewardVideoHolderImpl) FoxNativeAdHelper.getADXRewardVideoHolder();
        //默认缓存模式 可通过配置设置直接加载广告
        nativeIVideoHolder.setCached(isCached);
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXRewardVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXRewardVideoAd foxADXRewardVideoAd) {
                if (foxADXRewardVideoAd == null || foxADXRewardVideoAd.getFoxADXADBean() == null){
                    FoxBaseToastUtils.showShort("获取广告失败");
                    return;
                }
                mFoxADXRewardVideoAd = foxADXRewardVideoAd;
                //获取竞价价格
                price = foxADXRewardVideoAd.getECPM();
                Log.d(TAG, "onAdGetSuccess: ");
                FoxBaseToastUtils.showShort("获取广告成功");
                //在线模式 可能因为网络原因播放卡顿
                if (!isCached){
                    openAD();
                }
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                Log.d(TAG, "onAdCacheSuccess: ");
                FoxBaseToastUtils.showShort("广告缓存成功");
                //缓存模式 先缓存本地视频 再播放不会卡顿
                if (isCached){
                    openAD();
                }
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