package com.dt.adx.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoAd;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoHolder;
import com.mediamain.android.adx.view.rewardvideo.FoxADXRewardVideoView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.interfaces.FoxVideoListener;

public class RewardVideoActivity extends AppCompatActivity {

    private static final String TAG = RewardVideoActivity.class.getSimpleName();

    FoxADXRewardVideoHolder nativeIVideoHolder;
    private int slotId;
    private String userId;
    private ViewGroup contentView;
    /**
     * 竞价价格
     * /分 
     */
    private int price =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reward_video);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        findViewById(R.id.btnRequest).setOnClickListener(v -> {
            getAd();
        });

        findViewById(R.id.btnShow).setOnClickListener(v -> {
            if (foxADXRewardVideoView != null) {
                contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.addView(foxADXRewardVideoView, params);
                foxADXRewardVideoView.showAd(RewardVideoActivity.this,null,price);
                foxADXRewardVideoView.setVideoListener(new FoxVideoListener() {
                    @Override
                    public void onPrepared() {

                    }

                    @Override
                    public void onCompletion() {

                    }

                    @Override
                    public boolean onError(int what, int extra) {
                        return false;
                    }

                    @Override
                    public boolean onInfo(int what, int extra) {
                        return false;
                    }

                    @Override
                    public void onSeekComplete() {

                    }

                    @Override
                    public void onVideoSizeChanged(int width, int height) {

                    }
                });
            }
        });
    }
    FoxADXRewardVideoView foxADXRewardVideoView;
    private void getAd() {
        nativeIVideoHolder = FoxNativeAdHelper.getADXRewardVideoHolder();
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXRewardVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXRewardVideoAd foxADXRewardVideoAd) {
                if (foxADXRewardVideoAd!=null){
                    foxADXRewardVideoView = (FoxADXRewardVideoView) foxADXRewardVideoAd.getView();
                    FoxBaseToastUtils.showShort("成功获取广告 报价="+foxADXRewardVideoAd.getPrice());
                }
            }

            @Override
            public void onAdTimeOut() {
            }

            @Override
            public void onAdCacheSuccess(String id) {
                FoxBaseToastUtils.showShort("onAdCacheSuccess ");
            }

            @Override
            public void onAdCacheCancel(String id) {
                FoxBaseToastUtils.showShort("onAdCacheCancel ");
            }

            @Override
            public void onAdCacheFail(String id) {
                FoxBaseToastUtils.showShort("onAdCacheFail ");
            }

            @Override
            public void onAdCacheEnd(String id) {
                FoxBaseToastUtils.showShort("onAdCacheEnd ");
            }

            @Override
            public void onAdJumpClick() {
                FoxBaseToastUtils.showShort("onAdJumpClick ");
            }

            @Override
            public void onAdReward(boolean isReward) {
                if (isReward){
                    FoxBaseToastUtils.showShort("onAdReward 获得奖励");
                }else {
                    FoxBaseToastUtils.showShort("onAdReward 无奖励");
                }
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort("servingSuccessResponse ");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort("onError "+errorCode+errorBody);
            }

            @Override
            public void onAdLoadFailed() {
                FoxBaseToastUtils.showShort("onAdLoadFailed ");
            }

            @Override
            public void onAdLoadSuccess() {
                FoxBaseToastUtils.showShort("onAdLoadSuccess ");
            }

            @Override
            public void onAdCloseClick() {
                FoxBaseToastUtils.showShort("onAdCloseClick ");
                jumpMain();
            }

            @Override
            public void onAdClick() {
                FoxBaseToastUtils.showShort("onAdClick ");
            }

            @Override
            public void onAdExposure() {
                FoxBaseToastUtils.showShort("onAdExposure ");
            }

            @Override
            public void onAdActivityClose(String data) {
                FoxBaseToastUtils.showShort("onAdActivityClose ");
            }

            @Override
            public void onAdMessage(MessageData data) {
                FoxBaseToastUtils.showShort("onAdMessage ");
            }
        });
    }

    /**
     * 跳转主页
     */
    private void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
}