| 文档版本     | 修订日期     | 修订说明          | SDK版本    |
| -------- | -------- | ------------- | -------- |
| V1.0.0.0 | 20220715 | 广告SDK支持ADX竞价  | V3.3.0.0 |
| V1.0.0.1 | 20220717 | 增加用户隐私信息获取说明  |          |



# 1.接入准备																		

接入广告SDK前，请您联系广告平台申请您的AppId，Appkey，AppSecret，slotId等，可联系相关运营协助。

| AppId     | 媒体ID     | 必须  | 媒体管理后台申请 |
| --------- | -------- | --- | -------- |
| Appkey    | 媒体KEY    | 必须  | 媒体管理后台申请 |
| AppSecret | 媒体SECRET | 必须  | 媒体管理后台申请 |
| slotId    | 媒体广告位ID  | 必须  | 媒体管理后台申请 |

# 2.接入SDK

## 2.1 添加依赖

```groovy
1.在根目录下的build.gradle文件中添加

   repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
        maven {
            url 'https://maven.aliyun.com/repository/public'
        }
        maven {
            credentials {
                username '62d11e53cf51ade33b4ae2b6'
                password 'XoIn0VA8L3sF'
            }
            url 'https://packages.aliyun.com/maven/repository/2253698-release-0SXAnL/'
        }
    }

2.app下的build.gradle添加：(本SDK最低可运行于API Level 21)

  dependencies {
        //必须 
        implementation 'com.tuia.android:adx:3.3.0.0'
   }
```

## 2.2 添加权限

### 权限说明(sdk内部已经处理相关权限问题，如果遇到冲突咨询开发)

```xml
<!--联网权限 必须 -->
<uses-permission android:name="android.permission.INTERNET" />
<!--获取设备标识IMEI。用于标识用户 必须-->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<!--获取MAC地址，用于标识用户 必须-->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!--检测当前网络状态是2G、3G、4G还是WiFi 必须-->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!--读写存储权限 必须-->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<!--定位权限，不强制要求-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<!--建议添加“query_all_package”权限，SDK将通过此权限在AndroidR系统上判定广告对应的应用 是否在用户的app上安装，避免投放错误的广告，以此提高用户的广告体验。若添加此权限，需要在您的用户隐私文档中声明-->
<uses-permission android:name="android.permission.ACCESS_COARSE_UPDATES" />
<!--安装权限，下载类广告投放必须-->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```

SDK要求最低系统版本为API 21 ，对于适配了Android6.0以上(API >= 23)的App，建议开发者在获得了动态权限之后，调用SDK的初始化代码，否则SDK可能受影响

特别说明:请求广告数据前，请务必申请IMEI权限，否则会造成下发下载类广告的数据无法正常下发，影响广告计费追踪。所以强烈建议媒体在通过SDK请求广告前，先申请获取IMEI权限。相关权限说明，⻅下表:

| 																				权限名称															                                       | 																				用途															                             | 																				缺失导致问题															                             | 																				应用场景															         | 																				是否必须															 |
| ----------------------------------------------------------------------------- | ----------------------------------------------------------------- | --------------------------------------------------------------------- | ----------------------------------------------- | --------------------------------------- |
| 																				INTERNET															                                   | 																				网络访问权限															                         | 无法访问网络                                                                | 广告请求及展示时需要联网                                    | 是                                       |
| 																				ACCESS_NETWORK_STATE															                       | 																				访问网络状态:检测当前网络状态是2G、3G、4G还是WiFi															 | 无法获取网络状况，影响广告展示及加载效果                                                  | 内容展示及播放视频时需要                                    | 是                                       |
| 																				ACCESS_WIFI_STATE															                          | 																				获取设备信息MAC权限															                    | 																				无法获取MAC地址。影响内容推荐效果，造成核心消费指标下降															      | 																				内容展示及播放视频时需要															 | 否                                       |
| 																				READ_PHONE_STATE															                           | 获取设备信息IMEI权限                                                      | 																				无法获取设备标识IMEI。严重影响内容推荐效果，造成核心消费指标下降															 | 																				内容展示及播放视频时需要															 | 是                                       |
| 																				ACCESS_COARSE_LOCATIONACCESS_FINE_LOCATION															 | 																				定位权限															                           | 无发获取定位信息，严重影响内容推荐效果，造成核心消费指标下降                                        | 内容展示及播放视频时需要                                    | 否                                       |
| ACCESS_COARSE_UPDATES                                                         | 获取安装列表需要权限                                                        | 无法获取安装信息，严重影响内容推荐效果，造成核心消费指标下降                                        | 内容展示及播放视频时需要                                    | 是                                       |
| REQUEST_INSTALL_PACKAGES                                                      | 安装权限                                                              | 无法安装下载类型广告应用，严重影响内容传话效果，造成核心消费指标下降                                    | 下载类广告必须权限                                       | 是                                       |

															

## 2.3 关于混淆																	

### 请确保您的应用打包混淆时，请在混淆配置文件添加如下配置(重要):											

```text
-dontwarn com.mediamain.android.**
-keep class com.mediamain.android.**{ *;}
```

### 如果您的应用启用了资源混淆或资源缩减，您需要保留SDK的资源，SDK的资源名都是以fox_开头的。您可以在资源混淆配置文件添加如下配置:																													

```xml
<?xml version="1.0" encoding="utf-8"?>					
<resources xmlns:tools="http://schemas.android.com/tools"
tools:keep="@layout/fox_*,@id/fox_*,@style/fox_*,							  @drawable/fox_*,@string/fox_*,@color/fox_*,
@attr/fox_*,@dimen/fox_*,@xml/fox_"/>																																
```

# 3.SDK初始化

## 3.1 SDK初始化说明

### 请在您应用的 Application 的 onCreate() 方法中调用以下代码来初始化：

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //用户隐私信息获取控制 在用户同意隐私政策后调用冰初始化
        FoxUserDataController userDataController = FoxUserDataController.getInstance();
        userDataController.setUserAgree(true);
        FoxConfig config = new FoxConfig.Builder()
                .setAppId(appId)
                .setVersion(应用版本号)
                .setBundle(应用包名)
                .setName(应用名称)
                .setAppKey(appKey)
                .setAppSecret(appSecret)
                .setUserDataController(null)
                .setDebug(false)
                .build();
        FoxSDK.init(this,config);
    }
}
```

### 初始化相关接口和配置说明：

初始化接口：

```java
/**
 * 初始化入口
 * @param application
 * @param config  配置信息
 */
public static void init(Application application, @NonNull FoxConfig config) 


```

配置类说明：

```java
/**
 * =================================================
 * desc   : 广告配置类
 * =================================================
 **/
public class FoxConfig {

    private final String appId;//必须
    private final String appKey;//必须
    private final String appSecret;//必须
    private final String bundle;//必须
    private final String version;//必须
    private final String name;//必须
    private final FoxCustomController dtUserDataController;
    private final boolean isDebug;
```

## 3.2 隐私信息控制开关

信息安全相关说明，所有数据都经加密传输：

```java
public class FoxUserDataController extends FoxCustomController {

    //用户是否同意隐私政策
    private boolean userAgree =true;
}


public abstract class FoxCustomController {

    private boolean ip = true;	          //	设备IP地址
    private boolean ipv6 = true;	      //	设备IPv6地址
    private boolean ua = true;            //    浏览器User-agent
    private boolean carrier = true;       //	运营商ID （参照附录5.1）
    private boolean connectiontype = true;   //	网络类型 （参照附录5.2）
    private boolean devicetype = true;       //	设备类型 （参照附录5.3）
    private boolean imsi = true;         //    imsi
    private boolean androidid= true;     //     for andriod	安卓ID(请根据长度判断等于32时为md5加密后的值)
    private boolean imei = true;          //    for android	明文imei或者md5加密imei (请根据长度判断等于32时为md5加密后的值)
    private boolean oaid = true;          //    for android	oaid
    private boolean oaid_md5 = true;      //    or android	md5加密后的值
    private boolean mac = true;           //    明文mac地址或者md5加密mac (请根据长度判断等于32时为md5加密后的值)
    private boolean idfa = true;          //    for ios	idfa(请根据长度判断等于32时为md5加密后的值)
    private boolean make = true;          //	设备生产商例：HuaWei
    private boolean model = true;         //	设备型号例：Meta 8
    private boolean appstore_ver = true	; //	厂商应用商店版本号(vovi、小米、华为、oppo 等厂商应 用商店)
    private boolean hmscore = true;       //	华为 HMS Core 版本号
    private boolean boot_mark = true;     //	系统启动标识（阿里）
    private boolean update_mark = true;   //	系统更新标识（阿里）
    private boolean lan = true;           //	当前设备语言例:zh-CH
    private boolean os = true;            //	设备操作系统 (Android,iOS,WP,Others) 四种
    private boolean osv = true;           //	操作系统版本
    private boolean ppi = true;           //	设备屏幕像素密度:286
    private boolean orientation = true;   //	横竖屏(-1:未知，1:横屏，0:竖屏)
    private boolean geo = true;           //	设备位置属性
    private boolean app_list = true;           //用户安装列表
    private boolean netWorkType = true;         //网络类型
}
```

# 4.加载广告

## 4.1 激励视频(参考RewardVideoActivity)

```java
//获取广告对象
nativeIVideoHolder = FoxNativeAdHelper.getADXRewardVideoHolder();

//请求广告，在缓存成功onAdCacheSuccess回调后加载广告
nativeIVideoHolder.loadAd(slotId, userId, new FoxADXRewardVideoHolder.LoadAdListener() {
            @Override
            public void onAdGetSuccess(FoxADXRewardVideoAd foxADXRewardVideoAd) {
                mFoxADXRewardVideoAd = foxADXRewardVideoAd;
                //获取竞价价格
                foxADXRewardVideoAd.getECPM());
            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                mFoxADXADBean = foxADXADBean;
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
            public void servingSuccessResponse(BidResponse bidResponse{
            }

            @Override
            public void onError(int errorCode, String errorBody) {
                
            }
        });

if (mFoxADXADBean!=null && mFoxADXRewardVideoAd!=null){
                mFoxADXRewardVideoAd.setLoadVideoAdInteractionListener(new FoxADXRewardVideoAd.LoadVideoAdInteractionListener() {
                    @Override
                    public void onAdLoadFailed() {
                        
                    }

                    @Override
                    public void onAdLoadSuccess() {
                  
                    }

                    @Override
                    public void onAdClick() {
                    

                    }

                    @Override
                    public void onAdExposure() {
                      

                    }

                    @Override
                    public void onAdTimeOut() {
                     

                    }

                    @Override
                    public void onAdJumpClick() {
                    

                    }

                    @Override
                    public void onAdReward(boolean isReward) {
                     

                    }

                    @Override
                    public void onAdCloseClick() {
                    

                    }

                    @Override
                    public void onAdActivityClose(String data) {
                    
                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                   
                    }
                });
                //设置竞价胜出价格
                mFoxADXADBean.setPrice(price);
                //打开全屏视频广告
                mFoxADXRewardVideoAd.openActivity(mFoxADXADBean);
            }

//注意销毁资源 避免内存泄漏
@Override
protected void onDestroy() {
   if (nativeIVideoHolder != null) {
       nativeIVideoHolder.destroy();
   }
   super.onDestroy();
}

```

## 4.2 全屏视频



```java
 //获取广告对象
nativeIVideoHolder = FoxNativeAdHelper.getADXFullScreenHolder();

//请求广告，在缓存成功onAdCacheSuccess回调后加载广告
nativeIVideoHolder.loadAd(slotId, userId, new FoxADXFullScreenVideoHolder.LoadAdListener() {
    @Override
    public void onAdGetSuccess(FoxADXFullScreenVideoAd foxADXFullScreenVideoAd) {
        if (foxADXFullScreenVideoAd != null) {
            adxFullScreenVideoAd = foxADXFullScreenVideoAd;
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

    @Override
    public void servingSuccessResponse(BidResponse bidResponse) {
        
    }

    @Override
    public void onError(int errorCode, String errorBody) {
   
    }
});

//在onAdCacheSuccess后调用广告展示(调用show()并传入竞价结果出价(price 必须))
if (mFoxADXADBean != null && adxFullScreenVideoAd!=null) {
                adxFullScreenVideoAd.setLoadVideoAdInteractionListener(new FoxADXFullScreenVideoAd.LoadVideoAdInteractionListener() {
                    @Override
                    public void onAdLoadFailed() {
                        
                    }

                    @Override
                    public void onAdLoadSuccess() {
          
                    }

                    @Override
                    public void onAdClick() {
                      
                    }

                    @Override
                    public void onAdExposure() {
                        
                    }

                    @Override
                    public void onAdTimeOut() {
                      
                    }

                    @Override
                    public void onAdJumpClick() {
                        
                    }

                    @Override
                    public void onAdCloseClick() {
                      
                    }

                    @Override
                    public void onAdActivityClose(String data) {
                       
                    }

                    @Override
                    public void onAdMessage(MessageData data) {
                        
                    }
                });
                //设置竞价胜出价格
                mFoxADXADBean.setPrice(price);
                //打开全屏视频广告
                adxFullScreenVideoAd.openActivity(mFoxADXADBean);
            }else {
                FoxBaseToastUtils.showShort("等待缓存成功再播放");
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

## 4.3 开屏

```java
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

## 4.4 插屏

```java
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

## 4.5 BANNER/横幅

```java
//获取广告对象
adxBannerHolder = FoxNativeAdHelper.getADXBannerHolder();

//请求广告  可传入FoxSize大小自定义 参考LANDER_TMBr
adxBannerHolder.loadAd(BannerActivity.this, slotId, FoxSize.LANDER_TMBr,new FoxADXBannerHolder.LoadAdListener() {
                @Override
                public void onAdGetSuccess(FoxADXBannerAd bannerAd) {
                    mBannerAd = bannerAd;
                    if (mBannerAd!=null){
                        mBannerAd.getPrice()
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

## 4.6 浮标/ICON

```java
//获取广告对象
   adxIconHolder = FoxNativeAdHelper.getADXIconHolder();
    //请求广告 可自己设定展示icon的宽高 
    //width 宽， height 高
    //p_width 动画平移动宽默认跟宽一样， p_heighr动画平移动高 默认跟高一样 仅在多图轮博展示
   adxIconHolder.loadAd(IconActivity.this, slotId, userId, width, height, p_width, p_heighr, new FoxADXIconHolder.LoadAdListener() {
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

                    @Override
                    public void onAdGetSuccess(FoxADXIconAd adxIconAd) {
                        foxADXIconAd = adxIconAd;
             
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

## 4.7 自渲染类型(信息可使用此类型自渲染实现)

```java
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

# 5.回调接口说明

## 广告相关回调接口说明

```text
//广告加载失败
void onAdLoadFailed();

//广告加载成功
void onAdLoadSuccess();

//广告点击
void onAdClick();

//广告曝光
void onAdExposure();

//倒计时结束
void onAdTimeOut();

//点击跳过广告
void onAdJumpClick();

//奖励发放 true 代表完成视频任务有奖励 false 无
void onAdReward(boolean isReward);

//广告关闭按钮点击
void onAdCloseClick();

//活动页关闭
void onAdActivityClose(String data);

//活动页面信息回传 保留 暂无使用
void onAdMessage(MessageData data)


```

## 视频相关回调接口说明

```text
//视频播放器加载成功
void onPrepared();
//播放结束
void onCompletion();
//出错 有一定容错处理暂不需要特殊处理
boolean onError(int what, int extra);
//尺寸变化
void onVideoSizeChanged(int width, int height);
```

# 6.FAQ



