package com.dt.adx.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.feed.FoxADXTemInfoFeedAd;
import com.mediamain.android.adx.view.feed.FoxADXTemInfoFeedHolder;
import com.mediamain.android.adx.view.feed.interfaces.IFoxADXTemInfoFeedAd;
import com.mediamain.android.base.exoplayer2.Player;
import com.mediamain.android.base.exoplayer2.ui.AspectRatioFrameLayout;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * 信息流Demo（RecyclerView模版渲染）
 */
@SuppressWarnings("ALL")
public class RecyclerFeedListActivity extends Activity {
    private static final String TAG = "NativeFeedListActivity";

    private static final int AD_POSITION = 3;
    private static final int LIST_ITEM_COUNT = 30;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String userId;
    private int slotId;

    private List<IFoxADXTemInfoFeedAd> mFeedList;
    private FeedRecyclerAdapter mFeedRecyclerAdapter;
    private long mPosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_feed_recy);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        initView();
    }

    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFeedList = new ArrayList<>();
        mFeedRecyclerAdapter = new FeedRecyclerAdapter(this, mFeedList);
        mRecyclerView.setAdapter(mFeedRecyclerAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                int width = mRecyclerView.getWidth();
                if (width > 0) {
                    requestAd(mPosId, width);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                int width = mRecyclerView.getWidth();
                if (width > 0) {
                    requestAd(mPosId, width);
                }
            }
        });
        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int width = mRecyclerView.getWidth();
                        if (width > 0) {
                            requestAd(mPosId, width);
                        }
                    }
                }, 500);
            }
        });
    }


    List<IFoxADXTemInfoFeedAd> mFoxInfoAds=new ArrayList<>();
    private void requestAd(long posId, int width) {
        FoxADXTemInfoFeedHolder adxInfoStreamHolder = FoxNativeAdHelper.getFoxADXTemInfoFeedHolder();
        adxInfoStreamHolder.loadAd(this, slotId, userId, FoxADXTemInfoFeedAd.ITEM_VIEW_TYPE_BOTTOM_IMG_VIDEO, new FoxADXTemInfoFeedHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(List<IFoxADXTemInfoFeedAd> foxInfoAds) {
                FoxBaseToastUtils.showShort("获取广告成功");
                if (foxInfoAds!=null && foxInfoAds.size()>0){
                    if (refreshLayout != null) {
                        refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    }
                    int loadCount = 50;// 模拟每次展示的Item刷新个数
                    for (int i = 0; i < loadCount; i++) {
                        mFeedList.add(null);
                    }
                    mFoxInfoAds.addAll(foxInfoAds);
                    int totalCount = mFeedList.size();
                    int random = (int) (Math.random() * loadCount) + totalCount - loadCount;
                    for (IFoxADXTemInfoFeedAd foxADXTemInfoFeedAd:
                            mFoxInfoAds) {
                        mFeedList.set(random, foxADXTemInfoFeedAd);
                    }
                    mFeedRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAdCacheSuccess(List<IFoxADXTemInfoFeedAd> foxInfoAds) {

            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {

            }

            @Override
            public void onError(int errorCode, String errorBody) {
                if (refreshLayout != null) {
                    refreshLayout.finishLoadMore(false);
                }
                FoxBaseToastUtils.showShort("获取广告失败"+errorCode+errorBody);
            }
        });
    }

    private static class FeedRecyclerAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private List<IFoxADXTemInfoFeedAd> mDataList;

        FeedRecyclerAdapter(Context context, List<IFoxADXTemInfoFeedAd> dataList) {
            this.mContext = context;
            this.mDataList = dataList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            switch (viewType) {
                case ItemViewType.ITEM_VIEW_TYPE_AD:
                    return new AdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.config_feed_list_item_ad_container, viewGroup, false));
                case ItemViewType.ITEM_VIEW_TYPE_NORMAL:
                default:
                    return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_normal, viewGroup, false));
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if (viewHolder instanceof NormalViewHolder) {
                NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
                normalViewHolder.textView.setText(String.format("RecyclerView item %d", position));
            } else if (viewHolder instanceof AdViewHolder) {
                AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
                IFoxADXTemInfoFeedAd tempAd = mDataList.get(position);
                if (tempAd!=null){
                    tempAd.setMargin(0,10,0,10);
                    tempAd.setVideoSoundEnable(true);
                    tempAd.setTextSize(16);
                    tempAd.setAutoPlay(false);
                    tempAd.setRepeatMode(Player.REPEAT_MODE_ONE);
                    tempAd.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    tempAd.setAdBackground(FoxSDK.getContext().getDrawable(R.drawable.recy_shape));
                    View view = tempAd.getView();
                    if (view!=null){
                        if (tempAd.getView().getParent() instanceof ViewGroup) {
                            ((ViewGroup) tempAd.getView().getParent()).removeView(tempAd.getView());
                        }
                        adViewHolder.mAdContainer.removeAllViews();
                        adViewHolder.mAdContainer.addView(tempAd.getView());
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mDataList!=null && mDataList.size()>0){
                IFoxADXTemInfoFeedAd ksFeedAd = mDataList.get(position);
                if (ksFeedAd != null) {
                    return ItemViewType.ITEM_VIEW_TYPE_AD;
                } else {
                    return ItemViewType.ITEM_VIEW_TYPE_NORMAL;
                }
            }
            return 0;
        }

        @IntDef({ItemViewType.ITEM_VIEW_TYPE_NORMAL, ItemViewType.ITEM_VIEW_TYPE_AD})
        @Retention(RetentionPolicy.SOURCE)
        @Target(ElementType.PARAMETER)
        @interface ItemViewType {
            int ITEM_VIEW_TYPE_NORMAL = 0;
            int ITEM_VIEW_TYPE_AD = 1;
        }
    }

    private static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_normal);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        FrameLayout mAdContainer;

        AdViewHolder(View convertView) {
            super(convertView);
            mAdContainer = convertView.findViewById(R.id.feed_container);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
