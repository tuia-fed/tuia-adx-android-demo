package com.dt.adx.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.anythink.banner.unitgroup.api.CustomBannerAdapter;
import com.anythink.core.api.ATBiddingListener;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.banner.FoxADXBannerAd;
import com.mediamain.android.adx.view.banner.FoxADXBannerHolder;
import com.mediamain.android.adx.view.banner.FoxADXBannerView;
import com.mediamain.android.view.base.FoxSize;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import java.util.Map;

/**
 * =================================================
 * author : duibagroup
 * date   : 2022/8/9
 * desc   :
 * =================================================
 **/
public class FoxADXBannerAdapter extends CustomBannerAdapter {

    private String userId;
    private int slotId;
    private FoxADXBannerAd mBannerAd;
    /**
     * 竞胜价格设置
     */
    private int price =100;
    private  FoxADXBannerHolder adxBannerHolder;
    private  FoxADXADBean mFoxADXADBean;
    private FoxADXBannerView mFoxADXBannerView;
    private boolean isReady;

    @Override
    public boolean startBiddingRequest(Context context, Map<String, Object> serverExtra, Map<String, Object> localExtra, ATBiddingListener biddingListener) {
        return super.startBiddingRequest(context, serverExtra, localExtra, biddingListener);
    }

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> map, Map<String, Object> map1) {
        adxBannerHolder = FoxNativeAdHelper.getADXBannerHolder();
        adxBannerHolder.loadAd((Activity) context, slotId, new FoxSize(738, 200, "738x200_mb"),new FoxADXBannerHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXBannerAd bannerAd) {
                FoxBaseToastUtils.showShort("广告获取成功");
                mBannerAd = bannerAd;
                bannerAd.getECPM();
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort("广告缓存成功");
                mFoxADXADBean = foxADXADBean;
                isReady = true;
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(), "servingSuccessResponse");
            }


            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onError"+errorCode+errorBody);
            }
        });
    }

    @Override
    public void destory() {
        if (adxBannerHolder!=null){
            adxBannerHolder.destroy();
        }
        if (mFoxADXBannerView!=null){
            mFoxADXBannerView.destroy();
        }
    }

    @Override
    public String getNetworkPlacementId() {
        return String.valueOf(slotId);
    }

    @Override
    public String getNetworkSDKVersion() {
        return "3.3.0.0";
    }

    @Override
    public String getNetworkName() {
        return "tuia";
    }

    @Override
    public boolean isAdReady() {
        return isReady;
    }

    @Override
    public View getBannerView() {
        mFoxADXBannerView = (FoxADXBannerView) mBannerAd.getView();
        if (mFoxADXADBean!=null){
            mFoxADXADBean.setPrice(price);
        }
        mFoxADXBannerView.show(mFoxADXADBean);
        return mFoxADXBannerView;
    }

}
