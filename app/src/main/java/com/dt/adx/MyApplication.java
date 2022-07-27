package com.dt.adx;

import android.app.Application;
import android.content.Context;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.base.config.AutoConfig;
import com.mediamain.android.base.config.FoxConfig;
import com.mediamain.android.base.util.FoxBaseSPUtils;
import com.mediamain.android.controller.FoxUserDataController;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        final int env = FoxBaseSPUtils.getInstance().getInt("env_config", AutoConfig.RELEASE);
        AutoConfig.init(env);
        FoxUserDataController userDataController = FoxUserDataController.getInstance();
        userDataController.setUserAgree(true);
        /**
         * 设置获取的oaid  为了兼容oaid版本 允许媒体传入获取的oaid
         * 可以在广告请求前任意位置调用既可
         */
        FoxUserDataController.getInstance().setOaid("");
        FoxConfig config = new FoxConfig.Builder()
                .setAppId("40892")
                .setVersion("1.0.0")
                .setBundle("com.dt.adx")
                .setName("测试")
                .setAppKey("23NMRAjuivG7pbkbd5fWdskaCShC")
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
