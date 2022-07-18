package com.dt.adx.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenAd;
import com.mediamain.android.adx.view.tabscreen.FoxADXTabScreenHolder;
import com.mediamain.android.adx.view.tabscreen.FoxADXTbScreen;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class TabScreenActivity extends AppCompatActivity {

    private static final String TAG = TabScreenActivity.class.getSimpleName();

    FoxADXTabScreenHolder tabScreenVideoHolder;
    private FoxADXTbScreen foxADXTbScreen;
    private FoxADXADBean mFoxADXADBean;
    private FrameLayout mContainer;
    private int slotId;
    private String userId;
    private Activity activity;
    private int price = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_screnn);
        activity = this;
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(v -> getAd());
        findViewById(R.id.btnShow).setOnClickListener(v -> {
            if (foxADXTbScreen != null && mFoxADXADBean!=null){
                mFoxADXADBean.setPrice(price);
                foxADXTbScreen.show(TabScreenActivity.this,mFoxADXADBean);
            }
        });
    }

    private void getAd() {
        tabScreenVideoHolder = FoxNativeAdHelper.getADXTabScreenVideoHolder();
        tabScreenVideoHolder.loadAd(TabScreenActivity.this,slotId, userId,
                new FoxADXTabScreenHolder.LoadAdListener() {

                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: ");
                        FoxBaseToastUtils.showShort("onError");
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                        FoxBaseToastUtils.showShort("servingSuccessResponse");
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXTabScreenAd foxADXTabScreenAd) {
                        if (foxADXTabScreenAd!=null){
                            foxADXTbScreen = foxADXTabScreenAd.get();
                        }
                    }

                    @Override
                    public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                        mFoxADXADBean = foxADXADBean;
                    }

                    @Override
                    public void onAdCacheCancel(FoxADXADBean foxADXADBean) {

                    }

                    @Override
                    public void onAdCacheFail(FoxADXADBean foxADXADBean) {

                    }

                    @Override
                    public void onAdCacheEnd(FoxADXADBean foxADXADBean) {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        if (tabScreenVideoHolder != null) {
            tabScreenVideoHolder.destroy();
        }

        if (foxADXTbScreen != null) {
            foxADXTbScreen.destroy();
        }
        super.onDestroy();
    }
}