package com.dt.adx.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoAd;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoHolder;
import com.mediamain.android.adx.view.fullscreen.FoxADXFullScreenVideoView;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.interfaces.FoxVideoListener;

public class FullScreenVideoActivity extends AppCompatActivity {


    private static final String TAG = FullScreenVideoActivity.class.getSimpleName();

    FoxADXFullScreenVideoHolder nativeIVideoHolder;
    private int slotId;
    private String userId;
    private String mUrl;
    private int price = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_full_screen_video);
        if (getIntent() != null) {
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
            if (videoView != null ) {
                contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.addView(videoView, params);
                videoView.showAd(FullScreenVideoActivity.this, null,price);
                videoView.setVideoListener(new FoxVideoListener() {
                    @Override
                    public void onPrepared() {
                        Log.d(TAG, "onPrepared: ");
                    }

                    @Override
                    public void onCompletion() {
                        Log.d(TAG, "onCompletion: ");
                    }

                    @Override
                    public boolean onError(int what, int extra) {
                        Log.d(TAG, "onError: "+what+"-"+extra);
                        return false;
                    }

                    @Override
                    public boolean onInfo(int what, int extra) {
                        Log.d(TAG, "onInfo: "+what+"-"+extra);
                        return false;
                    }

                    @Override
                    public void onSeekComplete() {
                        Log.d(TAG, "onSeekComplete: ");
                    }

                    @Override
                    public void onVideoSizeChanged(int width, int height) {
                        Log.d(TAG, "onVideoSizeChanged: width="+width+"-height="+height);
                    }
                });
            }else {
                FoxBaseToastUtils.showShort("等待缓存成功再播放");
            }
        });
    }

    private FoxADXFullScreenVideoView videoView;
    private ViewGroup contentView;
    private void getAd() {
        nativeIVideoHolder = FoxNativeAdHelper.getADXFullScreenHolder();
        nativeIVideoHolder.loadAd(slotId, userId, new FoxADXFullScreenVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
                if (foxADXFullScreenVideoAd!=null && foxADXFullScreenVideoAd.getView() instanceof FoxADXFullScreenVideoView){
                    videoView = (FoxADXFullScreenVideoView) foxADXFullScreenVideoAd.getView();
                    FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdGetSuccess price="
                            +foxADXFullScreenVideoAd.getPrice());
                }
            }

            @Override
            public void onAdTimeOut() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdTimeOut");
            }

            @Override
            public void onAdCacheSuccess(String id) {
                Log.d(TAG, "onAdVideoCacheSuccess: onAdVideoCacheSuccess");
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdVideoCacheSuccess");
            }

            @Override
            public void onAdCacheCancel(String id) {

            }

            @Override
            public void onAdCacheFail(String id) {

            }

            @Override
            public void onAdCacheEnd(String id) {

            }

            @Override
            public void onAdJumpClick() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdJumpClick");
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"servingSuccessResponse");
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onError"+errorCode+errorBody);
            }

            @Override
            public void onAdLoadFailed() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdLoadFailed");
            }

            @Override
            public void onAdLoadSuccess() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdLoadSuccess");
            }

            @Override
            public void onAdCloseClick() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdCloseClick");
                jumpMain();
            }

            @Override
            public void onAdClick() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdClick");
            }

            @Override
            public void onAdExposure() {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdExposure");
            }

            @Override
            public void onAdActivityClose(String data) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdActivityClose");
            }

            @Override
            public void onAdMessage(MessageData data) {
                FoxBaseToastUtils.showShort(FoxSDK.getContext(),"onAdMessage");
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