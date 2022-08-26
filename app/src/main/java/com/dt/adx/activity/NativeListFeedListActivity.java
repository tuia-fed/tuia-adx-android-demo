package com.dt.adx.activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.dt.adx.widget.ILoadMoreListener;
import com.dt.adx.widget.LoadMoreListView;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.adx.base.FoxADXConstant;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.feed.FoxADXTemInfoFeedAd;
import com.mediamain.android.adx.view.feed.FoxADXTemInfoFeedHolder;
import com.mediamain.android.adx.view.feed.interfaces.IFoxADXTemInfoFeedAd;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * 信息流Demo（ListView 模板渲染：支持开发者进行微调）
 *
 *  * 请求广告             getAd()
 *  * 获取竞价价格          getECPM();
 *  * 设置竞胜价格展示广告   openAd()
 *  * 广告竞价失败的时候也调用下把胜出价格回传 mBannerAd.setWinPrice("广告平台名称","胜出价格", FoxADXConstant.CURRENCY.RMB);
 *  * 销毁广告组件          destroy();
 */
@SuppressWarnings("ALL")
public class NativeListFeedListActivity extends Activity {
    private static final String TAG = "NativeFeedListActivity";

    private static final int AD_POSITION = 3;
    private static final int LIST_ITEM_COUNT = 20;
    private LoadMoreListView mListView;
    private EditText editAdImagewidth;
    private EditText editAdImageHeight;
    private MyAdapter myAdapter;
    private List<IFoxADXTemInfoFeedAd> mData;

    private FoxADXTemInfoFeedHolder mFoxTempletInfoFeedHolder;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private String userId;
    private int slotId;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int mPrice =500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_feed_listview);
        mFoxTempletInfoFeedHolder = FoxNativeAdHelper.getFoxADXTemInfoFeedHolder();
        registerReceiver(receiver, new IntentFilter("broadsend.action"));
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        editAdImagewidth = (EditText) findViewById(R.id.editAdImageWidth);
        editAdImageHeight = (EditText) findViewById(R.id.editAdImageHeight);

        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mData.clear();
                    myAdapter.notifyDataSetChanged();
                    try {
                        imageWidth = Integer.valueOf(editAdImagewidth.getText().toString());
                        imageHeight = Integer.valueOf(editAdImageHeight.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mListView.setVisibility(View.VISIBLE);
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if (!TextUtils.isEmpty(mSlotId)) {
                        slotId = Integer.parseInt(mSlotId);
                    }
                    loadListAd();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        initListView();
    }

    @SuppressWarnings("RedundantCast")
    private void initListView() {
        mListView = (LoadMoreListView) findViewById(R.id.lmlv_native_feed);
        mData = new ArrayList<>();
        myAdapter = new MyAdapter(this, mData);
        mListView.setAdapter(myAdapter);
        mListView.setLoadMoreListener(new ILoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadListAd();
            }
        });
    }

    /**
     * 加载feed广告
     */
    private void loadListAd() {
        mFoxTempletInfoFeedHolder.loadAd(this, slotId, userId, FoxADXTemInfoFeedAd.ITEM_VIEW_TYPE_BOTTOM_IMG_VIDEO, new FoxADXTemInfoFeedHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(List<IFoxADXTemInfoFeedAd> foxInfoAds) {
                if (mListView != null) {
                    mListView.setLoadingFinish();
                }

                if (foxInfoAds == null || foxInfoAds.size() <= 0) {
                    return;
                }

                FoxBaseToastUtils.showShort("获取广告成功");
                for (int i = 0; i < LIST_ITEM_COUNT; i++) {
                    mData.add(null);
                }

                int count = mData.size();
                for (IFoxADXTemInfoFeedAd ad : foxInfoAds) {
                    int random = (int) (Math.random() * LIST_ITEM_COUNT) + count - LIST_ITEM_COUNT;
                    ad.setVideoSoundEnable(true);
                    ad.setMargin(0,10,0,10);
                    ad.setTextSize(16);
                    ad.setAutoPlay(false);
//                    ad.setAdBackground(FoxSDK.getContext().getDrawable(R.drawable.fox_default_image_background));
                    //支持开发者进行布局微调
                    if (imageWidth != 0 || imageHeight != 0) {
                        ad.setSize(imageWidth, imageHeight);
                        /**
                         *     RESIZE_MODE_FIT,
                         *     RESIZE_MODE_FIXED_WIDTH,
                         *     RESIZE_MODE_FIXED_HEIGHT,
                         *     RESIZE_MODE_FILL,
                         *     RESIZE_MODE_ZOOM
                         */
                        ad.setVideoSoundEnable(true);
                        ad.setMargin(0,10,0,10);
                        ad.setTextSize(18);
                        ad.setAutoPlay(false);
//                        ad.setAdBackground(FoxSDK.getContext().getDrawable(R.drawable.fox_default_image_background));
                    }
                    mData.set(random, ad);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdCacheSuccess(List<IFoxADXTemInfoFeedAd> foxInfoAds) {

            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {

            }

            @Override
            public void onError(int errorCode, String errorBody) {
                FoxBaseToastUtils.showShort("获取广告失败"+errorCode+errorBody);
            }
        });
    }

    final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mFoxTempletInfoFeedHolder != null) {
                int tag = intent.getIntExtra("Tag", 0);
                if (tag == 0) {
                    return;
                }
                FoxBaseToastUtils.showShort(context, "回传的type:" + tag);
                mFoxTempletInfoFeedHolder.sendMessage(tag, "");
            }
        }
    };


    @SuppressWarnings("CanBeFinal")
    private static class MyAdapter extends BaseAdapter {

        private static final int ITEM_VIEW_TYPE_NORMAL = 0;
        private static final int ITEM_VIEW_TYPE_AD = 1;
        /**
         * 竞胜价格
         */
        private int mPrice;

        private List<IFoxADXTemInfoFeedAd> mData;
        private Context mContext;

        public MyAdapter(Context context, List<IFoxADXTemInfoFeedAd> data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public IFoxADXTemInfoFeedAd getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //信息流广告的样式，有左图，右图，组图，大图（模板渲染支持微调）
        @Override
        public int getItemViewType(int position) {
            IFoxADXTemInfoFeedAd ad = getItem(position);
            if (null != ad && ad.getSpecType() > 0) {
                return ITEM_VIEW_TYPE_AD;
            } else {
                return ITEM_VIEW_TYPE_NORMAL;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IFoxADXTemInfoFeedAd ad = getItem(position);
            if (getItemViewType(position) == ITEM_VIEW_TYPE_AD) {
                return getTempletInfoFeedView(convertView, parent, position);
            } else {
                return getNormalView(convertView, parent, position);
            }
        }

        @SuppressWarnings("RedundantCast")
        private View getTempletInfoFeedView(View convertView, ViewGroup parent, int position) {
            AdViewHolder normalViewHolder;
            if (convertView == null) {
                normalViewHolder = new AdViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_info_feed, parent, false);
                normalViewHolder.mContainor = (FrameLayout) convertView.findViewById(R.id.fl_container);
                convertView.setTag(normalViewHolder);
            } else {
                normalViewHolder = (AdViewHolder) convertView.getTag();
            }
            IFoxADXTemInfoFeedAd tempAd = getItem(position);
            mPrice = tempAd.getECPM();
            //设置竞胜价格
            tempAd.setWinPrice(FoxSDK.getSDKName(),mPrice, FoxADXConstant.CURRENCY.RMB);
            if (tempAd!=null){
                View view = tempAd.getView();
                if (view!=null){
                    if (tempAd.getView().getParent() instanceof ViewGroup) {
                        ((ViewGroup) tempAd.getView().getParent()).removeView(tempAd.getView());
                    }
                    normalViewHolder.mContainor.removeAllViews();
                    normalViewHolder.mContainor.addView(tempAd.getView());
                }
            }
            return convertView;
        }

        /**
         * 非广告list
         *
         * @param convertView
         * @param parent
         * @param position
         * @return
         */
        @SuppressWarnings("RedundantCast")
        private View getNormalView(View convertView, ViewGroup parent, int position) {
            NormalViewHolder normalViewHolder;
            if (convertView == null) {
                normalViewHolder = new NormalViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_normal, parent, false);
                normalViewHolder.idle = (TextView) convertView.findViewById(R.id.tv_normal);
                convertView.setTag(normalViewHolder);
            } else {
                normalViewHolder = (NormalViewHolder) convertView.getTag();
            }
            normalViewHolder.idle.setText("ListView item " + position);
            return convertView;
        }

        private static class AdViewHolder {
            FrameLayout mContainor;
        }

        private static class NormalViewHolder {
            TextView idle;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
        //重要：这个资源销毁一定要加否则会造成内存泄漏
        if (null != mFoxTempletInfoFeedHolder) {
            mFoxTempletInfoFeedHolder.destroy();
        }
        mHandler.removeCallbacksAndMessages(null);
    }
}
