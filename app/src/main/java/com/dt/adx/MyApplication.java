package com.dt.adx;

import android.app.Application;
import android.content.Context;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.base.config.FoxConfig;
import com.mediamain.android.controller.FoxUserDataController;

public class MyApplication extends Application {

    /**
     *   设置获取的oaid  为了兼容oaid版本 允许媒体传入获取的oaid
     *   FoxUserDataController.getInstance().setOaid("");
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //用户隐私政策同意之后调用 在广告初始化前主线程中初始化 请勿多次初始化  传入application的context
        FoxUserDataController userDataController = FoxUserDataController.getInstance();
        //设备信息获取相关配置  默认获取 获取相关信息在对接文档上有列出  可通过userDataController.setOaid();等方法控制相关数据获取
        //设置获取的oaid  为了兼容oaid版本 允许媒体传入获取的oaid
        //FoxUserDataController.getInstance().setOaid(oaid);
        userDataController.setUserAgree(true);
        FoxConfig config = new FoxConfig.Builder()
                //替换成app版本号  必须
                .setVersion("1.0.0")
                //替换成app包名  必须
                .setBundle("com.dt.adx")
                //替换成app名字  必须
                .setName("测试")
                //替换后台获取的媒体id 必须
                .setAppId("40892")
                //替换后台获取的媒体AppKey 必须
                .setAppKey("23NMRAjuivG7pbkbd5fWdskaCShC")
                //替换后台获取的媒体AppSecret 必须
                .setAppSecret("3W7F3kBxfN9cpZ3PMKqtvQUzcrvMti5QEiyLSKq")
                .setUserDataController(userDataController)
                .setDebug(false)
                .build();
        FoxSDK.init(this,config);
    }

    public Context getContext(){
        return this;
    }

}
