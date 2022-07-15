# 接入指南

文档版本 | 修订日期| 修订说明
---|---|---
V1.0.0.0 | 20220715 | 广告SDK支持ADX竞价

## 自动集成方式（推荐）
#### 一.依赖引入
方式一.Gradle依赖（推荐）
```
   1.在根目录下的build.gradle文件中添加

   repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
        allprojects {
            repositories {
                maven {
                    url 'https://maven.aliyun.com/repository/public'
                }
                maven {
                    credentials {
                        username '62d0db35dbd131f866667a62'
                        password 'F4jv7KzjMyWB'
                    }
                    url 'https://packages.aliyun.com/maven/repository/2253698-release-0SXAnL/'
                }
                maven {
                    credentials {
                        username '62d0db35dbd131f866667a62'
                        password 'F4jv7KzjMyWB'
                    }
                    url 'https://packages.aliyun.com/maven/repository/2253698-snapshot-WwZwlE/'
                }
            }
        }
    }

  2.app下的build.gradle添加：(本SDK最低可运行于API Level 21，最高支持API Level 28)

  dependencies {
        //必须 
        implementation 'com.tuia.android:adx:3.3.0.0'
   }
```

#### 二.权限(sdk内部已经处理相关权限问题，如果遇到冲突咨询对应开发即可)
```
  //必须权限 请求网络权限
  <uses-permission android:name="android.permission.INTERNET" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
  //必须权限 广告投放使用
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  //必须权限 下载类广告安装需要
  <uses-permission android:name="android.permission.ACCESS_COARSE_UPDATES" />
  //必须权限 下载类广告安装需要
  <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```

#### 三.初始化
1.初始化(在用户隐私政策授权成功之后初始化，在Application初始onCreate方法中调用,必须初始化不然会导致奔溃)

```
   //appID，appKey，appSecret均在媒体后台获取，有问题咨询相关运营
   FoxConfig config = new FoxConfig.Builder()
                .setAppId(appID)
                .setVersion(app版本信息)
                .setBundle(app包名)
                .setName(应用名)
                .setAppKey(appKey))
                .setAppSecret(appSecret)
                //信息采集获取控制
                .setUserDataController(null)
                .setDebug(false)
                .build();
  FoxSDK.init(this,config);
  
```

#### 三.接入广告
1.激励视频类型广告(参考RewardVideoActivity)
```
   //获取广告对象
   nativeIVideoHolder = FoxNativeAdHelper.getADXRewardVideoHolder();
   
   //请求广告，在缓存成功onAdCacheSuccess回调后加载广告
   nativeIVideoHolder.loadAd(slotId, userId, new FoxADXRewardVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXRewardVideoAd foxADXRewardVideoAd) {
                if (foxADXRewardVideoAd!=null){
                    //获取单次广告ADX报价
                    foxADXRewardVideoAd.getPrice()
                    foxADXRewardVideoView = (FoxADXRewardVideoView) foxADXRewardVideoAd.getView();
                }
            }

            @Override
            public void onAdTimeOut() {
            }

            @Override
            public void onAdCacheSuccess(String id) {
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
            }

            @Override
            public void onAdReward(boolean isReward) {
                if (isReward){
                   //获得奖励
                }else {
                   //无奖励
                }
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
            }

            @Override
            public void onError(int errorCode, String errorBody) {
            }

            @Override
            public void onAdLoadFailed() {
            }

            @Override
            public void onAdLoadSuccess() {
            }

            @Override
            public void onAdCloseClick() {
            }

            @Override
            public void onAdClick() {
            }

            @Override
            public void onAdExposure() {
            }

            @Override
            public void onAdActivityClose(String data) {
            }

            @Override
            public void onAdMessage(MessageData data) {
            }
   });
   
   //在onAdCacheSuccess后调用广告展示(调用show()并传入竞价结果出价(price 必须))
   if (foxADXRewardVideoView != null) {
       contentView = findViewById(android.R.id.content);
       RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
       contentView.addView(foxADXRewardVideoView, params);
       foxADXRewardVideoView.showAd(RewardVideoActivity.this,null,price);
   }
   
   //注销广告组件避免内存泄漏
    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
```

2.全屏视频类型广告
```
   //获取广告对象
   nativeIVideoHolder = FoxNativeAdHelper.getADXFullScreenHolder();
   
   //请求广告，在缓存成功onAdCacheSuccess回调后加载广告
   nativeIVideoHolder.loadAd(slotId, userId, new FoxADXFullScreenVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
                if (foxADXFullScreenVideoAd!=null 
                && foxADXFullScreenVideoAd.getView() instanceof FoxADXFullScreenVideoView){
                    videoView = (FoxADXFullScreenVideoView) foxADXFullScreenVideoAd.getView();
                   //获取单次广告ADX报价
                   foxADXFullScreenVideoAd.getPrice()
                }
            }

            @Override
            public void onAdTimeOut() {
            }

            @Override
            public void onAdCacheSuccess(String id) {
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
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
            }

            @Override
            public void onError(int errorCode, String errorBody) {
            }

            @Override
            public void onAdLoadFailed() {
            }

            @Override
            public void onAdLoadSuccess() {
            }

            @Override
            public void onAdCloseClick() {
            }

            @Override
            public void onAdClick() {
            }

            @Override
            public void onAdExposure() {
            }

            @Override
            public void onAdActivityClose(String data) {
            }

            @Override
            public void onAdMessage(MessageData data) {
            }
   });
   
   //在onAdCacheSuccess后调用广告展示(调用show()并传入竞价结果出价(price 必须))
   if (videoView != null ) {
      contentView = findViewById(android.R.id.content);
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
      contentView.addView(videoView, params);
      videoView.showAd(FullScreenVideoActivity.this, null,price)；
    }
    
   //注销广告组件避免内存泄漏
    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
```
3.插屏弹窗类型广告
```
   //获取广告对象
   tabScreenVideoHolder = FoxNativeAdHelper.getADXTabScreenVideoHolder();
   
   //请求广告，在缓存成功onAdCacheSuccess回调后加载广告
   tabScreenVideoHolder.loadAd(TabScreenActivity.this,slotId, userId,200,200,
                new FoxADXTabScreenHolder.LoadAdListener() {

                    @Override
                    public void onAdTimeOut() {
                    }

                    @Override
                    public void onError(int errorCode, String errorBody) {
                    }

                    @Override
                    public void onAdLoadFailed() {
                    }

                    @Override
                    public void onAdLoadSuccess() {
                    }

                    @Override
                    public void onAdCacheSuccess(String id) {
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
                    public void onAdCloseClick() {
                    }

                    @Override
                    public void onAdClick() {
                    }

                    @Override
                    public void onAdExposure() {
                    }

                    @Override
                    public void onAdActivityClose(String data) {
                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXTabScreenAd foxADXTabScreenAd) {
                        foxADXTbScreen = foxADXTabScreenAd.get();
                    }
                });
                
   //在onAdCacheSuccess后调用广告展示(调用show()并传入竞价结果出价(price 必须))
   if (foxADXTbScreen != null){
       foxADXTbScreen.show(price);
   }
   
   //注销广告组件避免内存泄漏
   @Override
    protected void onDestroy() {
        if (tabScreenVideoHolder != null) {
            tabScreenVideoHolder.destroy();
        }
        super.onDestroy();
    }
```

4.开屏类型广告
```
   //获取广告对像
   adxSplashHolder = FoxNativeAdHelper.getADXSplashHolder();
   //请求广告
   adxSplashHolder.loadAd(slotId, userId, new FoxADXSplashHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXSplashAd foxADXSplashAd) {
                if (foxADXSplashAd != null) {
                    foxADXSplashAd.setScaleType(ImageView.ScaleType.FIT_XY);
                    foxADXShView = (FoxADXShView) foxADXSplashAd.getView();
                }
            }

            @Override
            public void onAdTimeOut() {
            }

            @Override
            public void onAdCacheSuccess(String id) {
                ViewGroup contentView = findViewById(android.R.id.content);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.addView(foxADXShView, params);
                foxADXShView.showAd(SplashActivity.this,null,price);
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
            }

            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {
            }

            @Override
            public void onError(int errorCode, String errorBody) {
            }

            @Override
            public void onAdLoadFailed() {
            }

            @Override
            public void onAdLoadSuccess() {
            }

            @Override
            public void onAdCloseClick() {

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdExposure() {
            }

            @Override
            public void onAdActivityClose(String data) {
            }

            @Override
            public void onAdMessage(MessageData data) {
            }
        });
        
   //注销广告组件避免内存泄漏
    @Override
    protected void onDestroy() {
        if (nativeIVideoHolder != null) {
            nativeIVideoHolder.destroy();
        }
        super.onDestroy();
    }
   
```

5.Banner/横幅类型广告
```
    //获取广告对象
    adxBannerHolder = FoxNativeAdHelper.getADXBannerHolder();
    
    //请求广告
    adxBannerHolder.loadAd(BannerActivity.this, slotId, FoxSize.LANDER_TMBr,new FoxADXBannerHolder.LoadAdListener() {
                    @Override
                    public void onAdGetSuccess(FoxADXBannerAd bannerAd) {
                        Log.d(TAG, "onAdGetSuccess: ");
                        mBannerAd = bannerAd;
                        if (mBannerAd!=null){
                            FoxBaseToastUtils.showShort(FoxSDK.getContext(),
                                    "获取竞价信息成功 price="+mBannerAd.getPrice());
                        }
                    }

                    @Override
                    public void servingSuccessResponse(BidResponse bidResponse) {
                        Log.d(TAG, "servingSuccessResponse: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "servingSuccessResponse");
                    }


                    @Override
                    public void onError(int errorCode, String errorBody) {
                        Log.d(TAG, "onError: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onError"+errorCode+errorBody);
                    }

                    @Override
                    public void onAdLoadFailed() {
                        Log.d(TAG, "onAdLoadFailed: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdLoadFailed");
                    }

                    @Override
                    public void onAdLoadSuccess() {
                        Log.d(TAG, "onAdLoadSuccess: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdLoadSuccess");
                    }

                    @Override
                    public void onAdCloseClick() {
                        Log.d(TAG, "onAdCloseClick: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdCloseClick");
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "onAdClick: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdClick");

                    }

                    @Override
                    public void onAdExposure() {
                        Log.d(TAG, "onAdExposure: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdExposure");
                    }

                    @Override
                    public void onAdActivityClose(String data) {
                        Log.d(TAG, "onAdActivityClose: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdActivityClose");
                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        Log.d(TAG, "onAdMessage: ");
                        FoxBaseToastUtils.showShort(FoxSDK.getContext(), "onAdMessage");
                    }
                });
                
    //广告展示 (调用show()并传入竞价结果出价(price 必须))
    if (mBannerAd!=null &&  mBannerAd.getView() instanceof FoxADXBannerView ){
                FoxADXBannerView foxADXBannerView = (FoxADXBannerView) mBannerAd.getView();
                container.removeAllViews();
                container.addView(mBannerAd.getView());
                foxADXBannerView.show(price);
    }
    
    //注销广告组件避免内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adxBannerHolder!=null){
            adxBannerHolder.destroy();
        }
    }

```

6.Icon/浮标类型广告
```
   //获取广告对象
   adxIconHolder = FoxNativeAdHelper.getADXIconHolder();
   
   //请求广告
   adxIconHolder.loadAd(IconActivity.this, slotId, userId, 80, 80, 80, 80, 1, new FoxADXIconHolder.LoadAdListener() {
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

                    @Override
                    public void onAdLoadFailed() {
                        Log.d(TAG, "onAdLoadFailed: ");
                        FoxBaseToastUtils.showShort("onAdLoadFailed: ");
                    }

                    @Override
                    public void onAdLoadSuccess() {
                        Log.d(TAG, "onAdLoadSuccess: ");
                        FoxBaseToastUtils.showShort("onAdLoadSuccess: ");
                    }

                    @Override
                    public void onAdCloseClick() {
                        Log.d(TAG, "onAdCloseClick: ");
                        FoxBaseToastUtils.showShort("onAdCloseClick: ");
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "onAdClick: ");
                        FoxBaseToastUtils.showShort("onAdClick: ");
                    }

                    @Override
                    public void onAdExposure() {
                        Log.d(TAG, "onAdExposure: ");
                        FoxBaseToastUtils.showShort("onAdExposure: ");

                    }

                    @Override
                    public void onAdActivityClose(String data) {
                        Log.d(TAG, "onAdActivityClose: ");
                        FoxBaseToastUtils.showShort("onAdActivityClose: ");

                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        Log.d(TAG, "onAdMessage: ");
                        FoxBaseToastUtils.showShort("onAdMessage: ");
                    }

                    @Override
                    public void onAdGetSuccess(FoxADXIconAd adxIconAd) {
                        foxADXIconAd = adxIconAd;
                        FoxBaseToastUtils.showShort("onAdGetSuccess: price="+adxIconAd.getPrice());
                    }
   });

   //展示广告 调用广告展示(调用show()并传入竞价结果出价(price 必须))
   if (foxADXIconAd!=null){
       FoxADXIconView foxADXIconView = (FoxADXIconView) foxADXIconAd.getView();
       foxADXIconView.show(price);
       ViewGroup contentView = findViewById(android.R.id.content);
       RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
       contentView.addView(foxADXIconView, params);
   }
   
   //注销广告组件避免内存泄漏
   @Override
   protected void onDestroy() {
        if (adxIconHolder!=null){
            adxIconHolder.destroy();
        }
        super.onDestroy();
    }
```

7.媒体自渲染信息流类型广告
```
//获取广告对象
   mOxCustomerTm = new FoxADXCustomerTm(this);
   //请求广告
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
     
   //广告点击调用，传入竞价结果价格    必须
   if (mOxCustomerTm != null && mBid != null && !TextUtils.isEmpty(mBid.getDurl())) {
      mOxCustomerTm.adClicked(price);
      mOxCustomerTm.openFoxActivity(mBid.getDurl());
   }
   
   //广告曝光调用，传入竞价结果价格  必须
   if (mOxCustomerTm != null) {
      mOxCustomerTm.adExposed(price);
   }
   
   //注销广告组件避免内存泄漏
   @Override
    protected void onDestroy() {
        if (mOxCustomerTm != null) {
            mOxCustomerTm.destroy();
        }
        super.onDestroy();
    }
```

8.自定义类型广告
```
   //获取广告对象
   mOxCustomerTm = new FoxADXCustomerTm(this);
   //请求广告
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
     
   //广告点击调用，传入竞价结果价格    必须
   if (mOxCustomerTm != null && mBid != null && !TextUtils.isEmpty(mBid.getDurl())) {
      mOxCustomerTm.adClicked(price);
      mOxCustomerTm.openFoxActivity(mBid.getDurl());
   }
   
   //广告曝光调用，传入竞价结果价格  必须
   if (mOxCustomerTm != null) {
      mOxCustomerTm.adExposed(price);
   }
   
   //注销广告组件避免内存泄漏
   @Override
    protected void onDestroy() {
        if (mOxCustomerTm != null) {
            mOxCustomerTm.destroy();
        }
        super.onDestroy();
    }
```


#### 四.注解

参考demo使用,替换对应申请的appId,appKey,slotId,userId，对应字段释义如下，字段均必填
| 名称 | 类型 | 备注 |
| :---------------------: | :---------------------: | :----------------------: |
| userId | String | 媒体的用户ID |
| appId  | Long   | 推啊媒体ID，通过TUIA媒体平台注册获得 |
| appKey | String | 媒体公钥，通过TUIA媒体平台注册获得 |
| slotId | Long   | 广告位ID，通过TUIA媒体平台注册获得 |

#### 五.混淆

``` 
-dontwarn com.mediamain.android.**
-keep class com.mediamain.android.**{ *;}
```

#### 六.验收
```
验收环节：
    提供测试包给我们测试验收，验收进度问题反馈，上线
```



