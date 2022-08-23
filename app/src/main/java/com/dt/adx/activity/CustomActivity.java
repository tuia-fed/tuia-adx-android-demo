package com.dt.adx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXConstant;
import com.mediamain.android.adx.response.Bid;
import com.mediamain.android.adx.response.BidAdm;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.custom.FoxADXCustomAd;
import com.mediamain.android.adx.view.custom.FoxADXCustomInfoHolder;
import com.mediamain.android.adx.view.customer.FoxADXCustomerHolder;
import com.mediamain.android.adx.view.customer.FoxADXCustomerTm;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

/**
 * 请求广告     getAd()
 *     type：
 *     banner类型广告:AD_TYPE_BANNER
 *     开屏类型广告:AD_TYPE_SPLASH
 *     弹窗类型广告:AD_TYPE_TAB_SCREEN
 *     信息流类型广告:AD_TYPE_INFO_STREAM
 *     激励视频类型广告:AD_TYPE_REWARD_VIDEO
 *     icon类型广告:AD_TYPE_TEXT_ICON
 *     全屏视频类型广告:AD_TYPE_FULL_SCREEN
 *
 * 获取竞价价格          getECPM();
 * 设置竞胜价格          foxADXCustomAd.setWinPrice(FoxADXConstant.PlatFrom.FROM_TUIA,mPrice, FoxADXConstant.CURRENCY.RMB);
 * 广告展示及曝光        mFoxADXCustomAd.adExposed();
 *
 * 设置竞胜价格点击广告   mFoxADXCustomAd.adClicked();
 *                     mFoxADXCustomAd.openFoxActivity(mBid.getDurl());
 *
 * 广告竞价失败的时候也调用下把胜出价格回传 mFoxADXCustomAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 *
 * 销毁广告组件          destroy();
 */
public class CustomActivity extends AppCompatActivity {

    private static final String TAG = CustomActivity.class.getSimpleName();

    private TextView textView;
    private String userId;
    private int slotId;
    /**
     * 广告信息
     */
    private Bid mBid;
    /**
     * 广告展示信息
     */
    private BidAdm mBidAdm;
    /**
     * 竞胜价格 分/每千次
     */
    private int mPrice;

    private FoxADXCustomAd mFoxADXCustomAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        textView = (TextView) findViewById(R.id.content_text);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(v -> getAd());
        textView.setOnClickListener(v -> {
           openAd();
        });
    }

    private void openAd() {
        if (mFoxADXCustomAd != null && mBid != null
                && !TextUtils.isEmpty(mBid.getDurl())) {
            mFoxADXCustomAd.onAdClick();
            mFoxADXCustomAd.openActivity(mBid.getDurl());
        }
    }


    private void getAd() {
        FoxADXCustomInfoHolder customerInfoHolder = FoxNativeAdHelper.getCustomerInfoHolder();
        customerInfoHolder.loadAd(slotId, userId, FoxADXConstant.AD_TYPE_REWARD_VIDEO, new FoxADXCustomInfoHolder.LoadAdListener() {
            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {

            }

            @Override
            public void onError(int errorCode, String errorBody) {

            }

            @Override
            public void onAdGetSuccess(FoxADXCustomAd foxADXCustomAd) {
                mFoxADXCustomAd =  foxADXCustomAd;
                mBid = foxADXCustomAd.getBid();
                mPrice = foxADXCustomAd.getECPM();
                foxADXCustomAd.setWinPrice(FoxADXConstant.PlatFrom.FROM_TUIA,mPrice, FoxADXConstant.CURRENCY.RMB);
                foxADXCustomAd.onAdExposure();
                if (textView!=null){
                    textView.setText(mBid.toString());
                }
            }

            @Override
            public void onAdGetSuccess(Bid bid, BidAdm bidAdm) {

            }

            @Override
            public void onAdActivityClose(String msg) {

            }

            @Override
            public void onAdMessage(MessageData data) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mFoxADXCustomAd != null) {
            mFoxADXCustomAd.destroy();
        }
        super.onDestroy();
    }
}