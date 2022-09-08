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
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenAd;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenHolder;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenHolderImpl;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 *  请求广告             getAd()
 *  获取竞价价格          getECPM();
 *  设置竞胜价格展示广告   openAd()
 *  广告投放优化设置：广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 *  销毁广告组件          destroy();
 */
public class TabScreenActivity extends AppCompatActivity {

    private static final String TAG = TabScreenActivity.class.getSimpleName();

    FoxADXTabScreenHolderImpl tabScreenVideoHolder;
    private FoxADXTabScreenAd mFoxADXTabScreenAd;
    private int slotId;
    private String userId;
    private final boolean isCached = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_screnn);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(v -> getAd());
        findViewById(R.id.btnShow).setOnClickListener(v -> {
            //缓存模式 先缓存本地视频 再播放不会卡顿
            openAD();
        });
    }

    private void getAd() {
        tabScreenVideoHolder = (FoxADXTabScreenHolderImpl) FoxNativeAdHelper.getADXTabScreenVideoHolder();
        //默认缓存模式 可通过配置设置直接加载广告
        tabScreenVideoHolder.setCached(isCached);
        tabScreenVideoHolder.loadAd(slotId, userId,
                new FoxADXTabScreenHolder.LoadAdListener() {

                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: "+errorCode+errorBody);
                        FoxBaseToastUtils.showShort("广告获取失败"+errorCode+errorBody);
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXTabScreenAd foxADXTabScreenAd) {
                        FoxBaseToastUtils.showShort("广告获取成功");
                        Log.d(TAG, "onAdGetSuccess: ");
                        if (foxADXTabScreenAd!=null){
                            mFoxADXTabScreenAd = foxADXTabScreenAd;
                            foxADXTabScreenAd.getECPM();
                            if (!isCached){
                                //在线播放模式  在此回调之后可用
                                openAD();
                            }
                        }
                    }

                    @Override
                    public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                        FoxBaseToastUtils.showShort("广告缓存成功");
                        Log.d(TAG, "onAdCacheSuccess: ");
                        //缓存模式 先缓存本地视频 再播放不会卡顿
                        if (isCached){
                            openAD();
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
                });
    }

    private void openAD() {
        if (mFoxADXTabScreenAd!=null){
            mFoxADXTabScreenAd.setLoadAdInteractionListener(new FoxADXTabScreenAd.LoadAdInteractionListener() {
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
                public void onAdCloseClick() {
                    Log.d(TAG, "onAdCloseClick: ");
                }

                @Override
                public void onAdActivityClose(String s) {
                    Log.d(TAG, "onAdActivityClose: ");
                }

                @Override
                public void onAdMessage(MessageData messageData) {
                    Log.d(TAG, "onAdMessage: ");
                }
            });
            mFoxADXTabScreenAd.setWinPrice(FoxSDK.getSDKName(),mFoxADXTabScreenAd.getECPM(), FoxADXConstant.CURRENCY.RMB);
            mFoxADXTabScreenAd.openActivity();
        }
    }

    @Override
    protected void onDestroy() {
        if (tabScreenVideoHolder != null) {
            tabScreenVideoHolder.destroy();
        }
        super.onDestroy();
    }
}