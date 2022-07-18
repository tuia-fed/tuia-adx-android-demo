package com.dt.adx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.banner.FoxADXBannerAd;
import com.mediamain.android.adx.view.icon.FoxADXIconAd;
import com.mediamain.android.adx.view.icon.FoxADXIconHolder;
import com.mediamain.android.adx.view.icon.FoxADXIconView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class IconActivity extends AppCompatActivity {

    private static final String TAG = IconActivity.class.getSimpleName();
    private FoxADXIconAd mFoxADXIconAd;
    private String userId;
    private int slotId;
    private int price =100;
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
        adxIconHolder = FoxNativeAdHelper.getADXIconHolder();
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adxIconHolder.loadAd(IconActivity.this, slotId, userId, 80, 80,  new FoxADXIconHolder.LoadAdListener() {
                    @Override
                    public void onAdGetSuccess(FoxADXIconAd foxADXIconAd) {
                        mFoxADXIconAd = foxADXIconAd;
                    }

                    @Override
                    public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
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
        });
        findViewById(R.id.btnShow).setOnClickListener(v -> {
            if (mFoxADXADBean!=null && mFoxADXIconAd!=null
                    && mFoxADXIconAd.getView() instanceof FoxADXIconView){
                mFoxADXIconView = (FoxADXIconView) mFoxADXIconAd.getView();
                ViewGroup contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.removeAllViews();
                contentView.addView(mFoxADXIconView, params);
                mFoxADXADBean.setPrice(price);
                mFoxADXIconView.show(mFoxADXADBean);
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