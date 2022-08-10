package com.dt.adx.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.anythink.rewardvideo.unitgroup.api.CustomRewardVideoAdapter;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoAd;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolder;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolderImpl;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

import java.util.Map;

/**
 * =================================================
 * author : duibagroup
 * date   : 2022/8/9
 * desc   :
 * =================================================
 **/
public class FoxADXRewardVideoAdapter extends CustomRewardVideoAdapter {

    FoxADXRewardVideoHolderImpl nativeIVideoHolder;
    private FoxADXRewardVideoAd mFoxADXRewardVideoAd;
    private int slotId = 423603;
    private String userId;
    private FoxADXADBean mFoxADXADBean;
    private boolean isCached = true;
    private boolean isReady = false;
    private int price =100;

    @Override
    public void show(Activity activity) {
        if (isReady && mFoxADXADBean!=null && mFoxADXRewardVideoAd!=null){
            mFoxADXRewardVideoAd.setLoadVideoAdInteractionListener(new FoxADXRewardVideoAd.LoadVideoAdInteractionListener() {
                @Override
                public void onAdLoadFailed() {
                }

                @Override
                public void onAdLoadSuccess() {
                }

                @Override
                public void onAdClick() {
                }

                @Override
                public void onAdExposure() {
                }

                @Override
                public void onAdTimeOut() {
                }

                @Override
                public void onAdJumpClick() {
                }

                @Override
                public void onAdReward(boolean isReward) {
                }

                @Override
                public void onAdCloseClick() {
                }

                @Override
                public void onAdActivityClose(String data) {
                }

                @Override
                public void onAdMessage(MessageData data) {
                }
            });
            //设置竞价胜出价格
            mFoxADXADBean.setPrice(price);
            //打开视频广告
            mFoxADXRewardVideoAd.openActivity(mFoxADXADBean);
        }
    }

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> map, Map<String, Object> map1) {
        FoxADXRewardVideoHolderImpl nativeIVideoHolder = (FoxADXRewardVideoHolderImpl) FoxNativeAdHelper.getADXRewardVideoHolder();
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
                mFoxADXADBean = foxADXRewardVideoAd.getFoxADXADBean();
                //获取竞价价格
                foxADXRewardVideoAd.getECPM();
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort("广告缓存成功");
                //缓存模式 先缓存本地视频 再播放不会卡顿
                mFoxADXADBean = foxADXADBean;
                isReady = true;
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
            public void servingSuccessResponse(BidResponse bidResponse) {
            }

            @Override
            public void onError(int errorCode, String errorBody) {
            }
        });
    }

    @Override
    public void destory() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
    }

    @Override
    public String getNetworkPlacementId() {
        return String.valueOf(slotId);
    }

    @Override
    public String getNetworkSDKVersion() {
        return "3.3.3.0";
    }

    @Override
    public String getNetworkName() {
        return "tuia";
    }

    @Override
    public boolean isAdReady() {
        return isReady;
    }
}
