package com.dt.adx;

import android.app.Application;
import android.content.Context;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.base.config.AutoConfig;
import com.mediamain.android.base.config.FoxConfig;
import com.mediamain.android.base.util.FoxBaseSPUtils;
import com.mediamain.android.controller.FoxUserDataController;

public class MyApplication extends Application {

    /**
     *   设置获取的oaid  为了兼容oaid版本 允许媒体传入获取的oaid
     *   FoxUserDataController.getInstance().setOaid("");
     */
    @Override
    public void onCreate() {
        super.onCreate();
        final int env = FoxBaseSPUtils.getInstance().getInt("env_config", AutoConfig.RELEASE);
        AutoConfig.init(env);
        //用户隐私政策同意之后调用 在广告初始化前初始化  传入application的context
        FoxUserDataController userDataController = FoxUserDataController.getInstance();
        //设备信息获取相关配置
        //设置获取的oaid  为了兼容oaid版本 允许媒体传入获取的oaid
        //FoxUserDataController.getInstance().setOaid(oaid);
        userDataController.setUserAgree(true);
        FoxConfig config = new FoxConfig.Builder()
                //替换成app版本号
                .setVersion("1.0.0")
                //替换成app包名
                .setBundle("com.dt.adx")
                //替换成app名字
                .setName("测试")
                //替换后台获取的媒体id
                .setAppId("40892")
                //替换后台获取的媒体AppKey
                .setAppKey("23NMRAjuivG7pbkbd5fWdskaCShC")
                //替换后台获取的媒体AppSecret
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
