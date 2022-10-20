# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/chenjingmian/Documents/soft/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#保留debug信息
-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable

# 指定代码的压缩级别0-7
-optimizationpasses 7

-dontpreverify

-flattenpackagehierarchy
-allowaccessmodification
-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable
-dontskipnonpubliclibraryclassmembers
-ignorewarnings
#kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class **.R$* {*;}
-keepclassmembers enum * { *;}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# 保持这些类不被混淆
-keep public class * extends android.view
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.pm
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-dontwarn com.google.**
-keep class android.security {*; }

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 不混淆注解
-keepattributes *Annotation*
-keep class * implements java.lang.annotation.Annotation {*;}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 保持 Serializable 不被混淆
-keep class * implements java.io.Serializable {*; }


# v4混淆配置
-dontwarn android.support.v4.**
-keep class android.support.4.** {*; }

# v7混淆配置
-dontwarn android.support.v7.**
-keep class android.support.7.** {*; }

# 下拉刷新库
-dontwarn com.handmark.pulltorefresh.**
-keep class com.handmark.pulltorefresh.** {*; }

# apache
-dontwarn org.apache.**
-keep class org.apache.** {*; }

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.** {*;}

# 小米推送混淆
#-keep class com.starbaba.stepaward.push.receiver.XMPushMessageReceiver {*;}
#-keep class com.xmiles.stepaward.push.receiver.XMPushMessageReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**

#保持华为推送不被混淆

-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-keep class com.huawei.hms.**{*;}

# EventBust 3.0混淆配置
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# 友盟统计
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ARouter
-dontwarn com.alibaba.android.arouter.**
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 个推
-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep class org.json.** { *; }

# FastJson 混淆
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#友盟social混淆begin
-dontusemixedcaseclassnames
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.twitter.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.mediamain.android.**
-keep class com.mediamain.android.**{ *;}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature
#友盟social混淆end

-keep class pl.droidsonroids.gif.** { *; }
-dontwarn okio.**

-keep class wendu.**{*;}

##阿里百川
#-keepattributes Signature
#-keep class sun.misc.Unsafe { *; }
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#-keep class com.ut.** {*;}
#-dontwarn com.ut.**
#-keep class com.ta.** {*;}
#-dontwarn com.ta.**
#-keep class mtopsdk.** {*;}
#-dontwarn mtopsdk.**
#-keep class org.json.** {*;}
#-keep class com.ali.auth.**  {*;}
#
##京东
#-keep class com.kepler.**{*;}
#-dontwarn com.kepler.**
#-keep class com.jingdong.jdma.**{*;}
# -dontwarn com.jingdong.jdma.**
#-keep class com.jingdong.crash.**{*;}
#-dontwarn com.jingdong.crash.**


# js接口不被混淆
-keep class com.starbaba.web.webinterface {*; }
-keep class * extends com.starbaba.web.delegate.BaseWebDelegate {*; }
-keep class com.starbaba.base.net.**{ *; }

# 业务bean
-keep class com.starbaba.base.bean.**{ *; }
-keep class com.starbaba.link.main.bean.**{ *; }
-keep class com.starbaba.base.**{ *; }

-keep class android.view.animation.**{ *; }

#支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

-dontwarn com.xianwan.sdklibrary.**
-keep class com.xianwan.sdklibrary.*.** { *; }

-keep public class com.starbaba.link.main.R$*{
    public static final int *;
}

#广点通
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}

#数美
-keep class com.ishumei.dfp.SMSDK { *; }

#集成IM的一堆混淆
# udesk
-keep class udesk.** {*;}
-keep class cn.udesk.**{*; }
# 七牛
-keep class okhttp3.** {*;}
-keep class okio.** {*;}
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings
# smack
-keep class org.jxmpp.** {*;}
-keep class de.measite.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.xmlpull.** {*;}
-dontwarn org.xbill.**
-keep class org.xbill.** {*;}

# eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# freso
-keep class com.facebook.** {*; }
-keep class com.facebook.imagepipeline.** {*; }
-keep class com.facebook.animated.gif.** {*; }
-keep class com.facebook.drawee.** {*; }
-keep class com.facebook.drawee.backends.pipeline.** {*; }
-keep class com.facebook.imagepipeline.** {*; }
-keep class bolts.** {*; }
-keep class me.relex.photodraweeview.** {*; }

-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**


 # bugly
-keep class com.tencent.bugly.** {*; }

 # agora
-keep class io.agora.**{*;}
#集成IM的一堆混淆 -end


#神策
-dontwarn com.sensorsdata.analytics.android.**
-keep class com.sensorsdata.analytics.android.** {
*;
}
-keep class **.R$* {
    <fields>;
}
-keepnames class * implements android.view.View$OnClickListener
-keep public class * extends android.content.ContentProvider
-keepnames class * extends android.view.View

-keep class * extends android.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}

# 如果使用了 DataBinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.** {*;}

#友盟分享
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.twitter.** { *; }
-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature

-keep class com.sensorsdata.analytics.android.** { *; }

#android x
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# FastJson 混淆
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

# 小米 start
-keep class com.xiaomi.ad.**{*;}
-keep class com.miui.zeus.**{*;}
# 小米 end

# 广点通 start
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}
-keep class MTT.ThirdAppInfoNew {
    *;
}
-keep class com.tencent.** {
    *;
}
# 广点通 end

# 穿山甲 start
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
# 穿山甲 end

#百度广告 start
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.*.** { *; }
#百度广告 end

#数美
-keep class com.ishumei.dfp.SMSDK { *; }

#js交互
-keep class com.polestar.core.adcore.web.SceneSdkBaseWebInterface {*; }

#mobvista
#-keepattributes Signature
#-keepattributes *Annotation*
#-keep class com.mintegral.** {*; }
#-keep interface com.mintegral.** {*; }
#-keep interface androidx.** { *; }
#-keep class androidx.** { *; }
#-keep public class * extends androidx.** { *; }
#-dontwarn com.mintegral.**
#-keep class **.R$* { public static final int mintegral*; }
#-keep class com.alphab.** {*; }
#-keep interface com.alphab.** {*; }

##推啊
#-keep class com.tuia.ad_base.** { *; }
#-keep class com.tuia.ad.** { *; }

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#gson
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keep class sun.misc.Unsafe { *; }

#androidUtil
-keep class com.blankj.utilcode.**{*; }

#fqcn.of.javascript.interface.for.Webview
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}

#json
-keep class org.json.** { *; }
#小说
-dontwarn com.iBookStar.**
-keep class com.iBookStar.**{*;}

-keep class com.polestar.core.adcore.core.launch.HandleDoLaunch { *; }

-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}


#推啊的另一个
#-dontwarn com.lechuan.midunovel.view**
#-keep class com.lechuan.midunovel.view.** { *; }

#TONGDUN
-dontwarn com.android.internal.**
-dontwarn cn.tongdun.android.**
-keep class cn.tongdun.android.**{*;}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class com.starbaba.luckyremove.module.withdraw.WithDrawWebView { *; }

#广点通更新后

-dontwarn com.androidquery.**
-keep class com.androidquery.** { *;}
-dontwarn tv.danmaku.**
-keep class tv.danmaku.** { *;}
# 如果使用了tbs版本的sdk需要进行以下配置
-keep class com.tencent.smtt.** { *; }
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
# 如果使用了微信OpenSDK，需要添加如下配置
-keep class com.tencent.mm.opensdk.** {
 *;
}
-keep class com.tencent.wxop.** {
 *;
}
-keep class com.tencent.mm.sdk.** {
 *;
}
-keep class com.tmsdk.** {
  *;
}
-keep class tmsdk.** {
  *;
}
-keep class btmsdkobf.** {
  *;
}
-keep class com.tencent.** {
  *;
}
-keep class com.tmsecure.** {
  *;
}
# aoid标示
-keep class com.bun.miitmdid.core.** {*;}

-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.starbaba.link.R$*{
public static final int *;
}

-keep class com.pgl.sys.ces.* {*;}
-dontwarn com.ss.android.socialbase.downloader.impls.**
-dontwarn com.ss.android.crash.log.**

-keep class org.cocos2dx.** {
 *;
}

# 3.6.4.1
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.ss.sys.ces.* {*;}
-keep class com.qq.e.** { public protected *; }
-keep class android.support.v4.app.NotificationCompat**{ public *; }

#防沉迷
-keep class com.xmiles.antiaddictionsdk.login.dialogs.BaseAntiDialog{public protected *;}
-keep public class com.xmiles.antiaddictionsdk.net.decode.GameAccountLoginRequest { *; }
-keep public class com.xmiles.antiaddictionsdk.net.decode.GameAccountLoginResponse { *; }
-keep public class com.xmiles.antiaddictionsdk.net.decode.GameAccountRegisterRequest { *; }