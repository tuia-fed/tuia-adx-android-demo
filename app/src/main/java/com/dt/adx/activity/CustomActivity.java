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
import com.mediamain.android.adx.view.customer.FoxADXCustomerHolder;
import com.mediamain.android.adx.view.customer.FoxADXCustomerTm;
import com.mediamain.android.view.bean.MessageData;

/**
 * 请求广告             getAd()
 *
 * 获取竞价价格          getECPM();
 *
 * 广告展示及曝光        mOxCustomerTm.adExposed(mPrice);
 *
 * 设置竞胜价格点击广告   mOxCustomerTm.adClicked(mPrice);
 *                     mOxCustomerTm.openFoxActivity(mBid.getDurl());
 *
 * 销毁广告组件          destroy();
 */
public class CustomActivity extends AppCompatActivity {

    private static final String TAG = CustomActivity.class.getSimpleName();

    private FoxADXCustomerTm mOxCustomerTm;
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
     * 竞胜价格
     */
    private int mPrice;

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
        mOxCustomerTm = new FoxADXCustomerTm(this);
    }

    private void openAd() {
        if (mOxCustomerTm != null && mBid != null
                && !TextUtils.isEmpty(mBid.getDurl())) {
            mOxCustomerTm.adClicked(mPrice);
            mOxCustomerTm.openFoxActivity(mBid.getDurl());
        }
    }

    private void getAd() {
        mOxCustomerTm.setAdListener(new FoxADXCustomerHolder.LoadAdListener() {
            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                Log.d(TAG, "servingSuccessResponse: ");
                FoxBaseToastUtils.showShort(getApplicationContext(), "广告请求成功");
                textView.setText(bidResponse.toString());
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: "+i+s);
            }

            @Override
            public void onAdGetSuccess(Bid bid, BidAdm bidAdm) {
                Log.d(TAG, "onAdGetSuccess: ");
                FoxBaseToastUtils.showShort("onAdGetSuccess");
                mBid = bid;
                mBidAdm = bidAdm;
                mOxCustomerTm.adExposed(mPrice);
                if (textView!=null){
                    textView.setText(mBid.toString());
                }
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
        mOxCustomerTm.loadAd(slotId,userId,FoxADXConstant.AD_TYPE_INFO_STREAM);
    }

    @Override
    protected void onDestroy() {
        if (mOxCustomerTm != null) {
            mOxCustomerTm.destroy();
        }
        super.onDestroy();
    }
}