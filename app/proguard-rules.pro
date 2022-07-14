# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\AndroidTool\android-midunovel-windows/tools/proguard/proguard-android.txt
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
 #-------------------------------------------基本不用动区域--------------------------------------------
 #---------------------------------基本指令区----------------------------------
-ignorewarnings
##指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 # 指定不去忽略包可见的库类的成员
 -dontskipnonpubliclibraryclassmembers
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-allowaccessmodification
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
#-repackageclasses ''
-dontusemixedcaseclassnames
-ignorewarnings

-printmapping proguardMapping.txt
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod,MethodParameters
  #-------------------------------------------基本不用动区域START--------------------------------------------
  #---------------------------------基本指令区----------------------------------

#-dontoptimize
#保持类不被混淆
-keep class android.support.annotation.** {*;}
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

#保持 enum 类不被混淆
-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
}
#
-keepclasseswithmembernames class * {
     public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
}

# for DexGuard only
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class *.R

-keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}



-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context,com.lechuan.midunovel.view.FoxSize);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers  class com.lechuan.midunovel.view.FoxSize {
    public static final FoxSize *;
}
#保持 TMNa 方法不被混淆
-keepclasseswithmembernames class * {
    TMNa <methods>;
}
#---------------------------------SDK指令区END----------------------------------

#---------------------------------三方SDK指令区START----------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#---------------------------------webview------------------------------------

#---------------------------------Gif------------------------------------
-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}
-keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}
#---------------------------------Gif------------------------------------


#---------------------------------Glide------------------------------------
-keep public class * implements com.mediamain.android.base.glide.module.GlideModule
-keep public class * extends com.mediamain.android.base.glide.module.AppGlideModule
-keep public enum com.mediamain.android.base.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#---------------------------------Glide------------------------------------

#---------------------------------okhttp------------------------------------
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}
#---------------------------------okhttp------------------------------------

#---------------------------------gson------------------------------------
#gson
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
#---------------------------------gson------------------------------------

#---------------------------------okdownload------------------------------------
# okdownload:okhttp
-keepnames class com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection


# okdownload:sqlite
-keep class com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite {
         public com.liulishuo.okdownload.core.breakpoint.DownloadStore createRemitSelf();
         public com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite(android.content.Context);
 }
# Findbugs
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
#---------------------------------okdownload------------------------------------

#---------------------------------oaid-miitmdid------------------------------------
#OAID http://www.msa-alliance.cn/col.jsp?id=120
-keep class com.bun.miitmdid.core.** {*;}
#-------------------------------------------基本不用动区域END--------------------------------------------
-dontwarn com.lechuan.midu.demo.**
-keep class com.lechuan.midu.demo.**{*;}
-keep class com.lechuan.midu.demo.widget.**{*;}
-keep interface com.lechuan.midu.demo.widget.**{*;}

-dontwarn com.mediamain.android.**
-keep class com.mediamain.android.**{ *;}
-keep interface com.mediamain.android.**{ *;}

#-dontwarn com.mediamain.android.base.**
#-keep class com.mediamain.android.base.**{ *;}
#-keep interface com.mediamain.android.base.**{ *;}
#-dontwarn com.mediamain.android.hotfix.**
#-keep class com.mediamain.android.hotfix.**{ *;}
#-keep interface com.mediamain.android.hotfix.**{ *;}
#-dontwarn com.mediamain.android.nativead.**
#-keep class com.mediamain.android.nativead.**{ *;}
#-keep interface com.mediamain.android.nativead.**{ *;}
#-dontwarn com.mediamain.android.view.**
#-keep class com.mediamain.android.view.**{ *;}
#-keep interface com.mediamain.android.view.**{ *;}
##---------------------------------SDK指令区START----------------------------------
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-verbose
#-ignorewarnings
#-dontoptimize
#-dontpreverify
#-keepattributes *Annotation*
##---------------------------------SDK指令区END----------------------------------

-dontwarn
-keepattributes Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*
-keeppackagenames
-ignorewarnings
-dontwarn android.support.v4.**,**CompatHoneycomb,com.tenpay.android.**
-optimizations !class/unboxing/enum,!code/simplification/arithmetic
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keepattributes *Annotation*
