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

public class CustomActivity extends AppCompatActivity implements FoxADXCustomerHolder.LoadAdListener {

    private FoxADXCustomerTm mOxCustomerTm;
    private TextView textView;
    private String userId;
    private int slotId;
    private Bid mBid;
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
        mOxCustomerTm = new FoxADXCustomerTm(this);
        mOxCustomerTm.setAdListener(new FoxADXCustomerHolder.LoadAdListener() {
            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onAdGetSuccess(Bid bid, BidAdm bidAdm) {

            }

            @Override
            public void onAdActivityClose(String s) {

            }

            @Override
            public void onAdMessage(MessageData messageData) {

            }
        });
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(v -> mOxCustomerTm.loadAd(slotId, userId, FoxADXConstant.AD_TYPE_REWARD_VIDEO));
        textView.setOnClickListener(v -> {
            if (mOxCustomerTm != null && mBid != null
                    && !TextUtils.isEmpty(mBid.getDurl())) {
                mOxCustomerTm.adClicked(mPrice);
                mOxCustomerTm.openFoxActivity(mBid.getDurl());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mOxCustomerTm != null) {
            mOxCustomerTm.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void servingSuccessResponse(BidResponse bidResponse) {
        FoxBaseToastUtils.showShort(getApplicationContext(), "广告请求成功");
        Log.d("========", "onReceiveAd:" + bidResponse.toString());
        FoxBaseToastUtils.showShort("servingSuccessResponse");
        textView.setText(bidResponse.toString());
    }

    @Override
    public void onError(int errorCode, String errorBody) {
        Log.d("========", "onFailedToReceiveAd");
        FoxBaseToastUtils.showShort("onError"+errorCode+errorBody);
    }

    @Override
    public void onAdGetSuccess(Bid bid, BidAdm bidAdm) {
        FoxBaseToastUtils.showShort("onAdGetSuccess");
        mBid = bid;
        mOxCustomerTm.adExposed(mPrice);
        if (textView!=null){
            textView.setText(mBid.toString());
        }
    }

    @Override
    public void onAdActivityClose(String data) {
        FoxBaseToastUtils.showShort("onAdActivityClose");

        Log.d("========", "onAdActivityClose" + data);
        FoxBaseToastUtils.showShort(getApplicationContext(), "活动页面关闭 发奖信息：" + data);
    }


    @Override
    public void onAdMessage(MessageData data) {
        FoxBaseToastUtils.showShort("onAdMessage");

    }
}