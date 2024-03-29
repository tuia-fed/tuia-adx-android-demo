package com.dt.adx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.base.FoxADXConstant;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.icon.FoxADXIconAd;
import com.mediamain.android.adx.view.icon.FoxADXIconHolder;
import com.mediamain.android.adx.view.icon.FoxADXIconView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 * 请求广告             getAd()
 * 获取竞价价格          getECPM();
 * 设置竞胜价格展示广告   openAd()
 * 广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 * 销毁广告组件          destroy();
 */
public class IconActivity extends AppCompatActivity {

    private static final String TAG = IconActivity.class.getSimpleName();
    private FoxADXIconAd mFoxADXIconAd;
    private String userId;
    private int slotId;
    /**
     * 竞胜价格设置
     */
    private int mPrice;
    private FoxADXIconHolder adxIconHolder;
    private  FoxADXADBean mFoxADXADBean;
    private FoxADXIconView mFoxADXIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        if (getIntent()!=null){
             userId = getIntent().getStringExtra("userId");
             slotId = getIntent().getIntExtra("slotId", 0);
        }
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

    private void openAd() {
        if (mFoxADXIconAd!=null && mFoxADXIconAd.getView() instanceof FoxADXIconView){
            mFoxADXIconView = (FoxADXIconView) mFoxADXIconAd.getView();
            mFoxADXIconView.setAdView(View.GONE);
            mFoxADXIconView.setCloseView(View.GONE);
            //设置竞胜价格
            mFoxADXIconAd.setWinPrice(FoxSDK.getSDKName(),mFoxADXIconAd.getECPM(), FoxADXConstant.CURRENCY.RMB);
            mFoxADXIconAd.setLoadAdInteractionListener(new FoxADXIconAd.LoadAdInteractionListener() {

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
            ViewGroup contentView = findViewById(R.id.container);
            contentView.removeAllViews();
            contentView.addView(mFoxADXIconView);
        }
    }

    private void getAd() {
        adxIconHolder = FoxNativeAdHelper.getADXIconHolder();
        adxIconHolder.loadAd(IconActivity.this, slotId, userId, 0, 0,  new FoxADXIconHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXIconAd foxADXIconAd) {
                FoxBaseToastUtils.showShort("广告获取成功");
                Log.d(TAG, "onAdGetSuccess: ");
                mFoxADXIconAd = foxADXIconAd;
                mPrice = foxADXIconAd.getECPM();
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                FoxBaseToastUtils.showShort("广告缓存成功");
                Log.d(TAG, "onAdCacheSuccess: ");
                mFoxADXADBean = foxADXADBean;
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
                FoxBaseToastUtils.showShort("servingSuccessResponse: ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                Log.d(TAG, "onError: ");
                FoxBaseToastUtils.showShort("onError: "+errorCode+errorBody);
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (adxIconHolder!=null){
            adxIconHolder.destroy();
        }
        if (mFoxADXIconView!=null){
            mFoxADXIconView.destroy();
        }
        super.onDestroy();
    }
}