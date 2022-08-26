package com.dt.adx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.base.FoxADXConstant;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.banner.FoxADXBannerAd;
import com.mediamain.android.adx.view.banner.FoxADXBannerHolder;
import com.mediamain.android.adx.view.banner.FoxADXBannerView;
import com.mediamain.android.view.base.FoxSize;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 * 请求广告             getAd()
 * 获取竞价价格          getECPM();
 * 设置竞胜价格展示广告   openAd()
 * 广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 * 销毁广告组件          destroy();
 */
public class BannerActivity extends AppCompatActivity {

    private static final String TAG = BannerActivity.class.getSimpleName();

    private FrameLayout container;
    private String userId;
    private int slotId;
    private FoxADXBannerAd mBannerAd;
    private int mPrice;
    private int width = 738;
    private int height = 200;
    private  FoxADXBannerHolder adxBannerHolder;
    private FoxADXBannerView mFoxADXBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        container = (FrameLayout) findViewById(R.id.container);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        Button btnShow = (Button) findViewById(R.id.btnShow);
        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        btnShow.setOnClickListener(v -> {
            openAd();
        });
        btnRequest.setOnClickListener(v ->getAd());
    }

    private void getAd() {
        adxBannerHolder = FoxNativeAdHelper.getADXBannerHolder();
        adxBannerHolder.loadAd(BannerActivity.this, slotId, new FoxSize(width, height, width+"x"+height+"_mb"),new FoxADXBannerHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXBannerAd bannerAd) {
                FoxBaseToastUtils.showShort("广告获取成功");
                Log.d(TAG, "onAdGetSuccess: ");
                mBannerAd = bannerAd;
                mPrice = bannerAd.getECPM();
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort("广告缓存成功");
                Log.d(TAG, "onAdCacheSuccess: ");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
                FoxBaseToastUtils.showShort(FoxSDK.getContext(), "servingSuccessResponse");
            }


            @Override
            public void onError(int errorCode, String errorBody) {
                Log.d(TAG, "onError: ");
                FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onError"+errorCode+errorBody);
            }
        });
    }

    private void openAd() {
        if (mBannerAd!=null &&  mBannerAd.getView() instanceof FoxADXBannerView ){
            mFoxADXBannerView = (FoxADXBannerView) mBannerAd.getView();
            mBannerAd.setWinPrice(FoxSDK.getSDKName(),mPrice, FoxADXConstant.CURRENCY.RMB);
            mBannerAd.setLoadAdInteractionListener(new FoxADXBannerAd.LoadAdInteractionListener() {

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
            container.removeAllViews();
            container.addView(mFoxADXBannerView);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adxBannerHolder!=null){
            adxBannerHolder.destroy();
        }
        if (mFoxADXBannerView!=null){
            mFoxADXBannerView.destroy();
        }
    }

}