package com.dt.adx;

import android.app.Application;
import android.content.Context;
import com.mediamain.android.FoxSDK;
import com.mediamain.android.base.config.AutoConfig;
import com.mediamain.android.base.config.FoxConfig;
import com.mediamain.android.base.util.FoxBaseLogUtils;
import com.mediamain.android.base.util.FoxBaseSPUtils;
import com.mediamain.android.controller.FoxUserDataController;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 默认test环境
        final int env = FoxBaseSPUtils.getInstance().getInt("env_config", AutoConfig.RELEASE);
        AutoConfig.init(env);
        FoxUserDataController instance = FoxUserDataController.getInstance();
        instance.setAndroidid(false);
        FoxConfig config = new FoxConfig.Builder()
                .setAppId("89967")
                .setVersion("3.3.0")
                .setBundle("com.dt.adx")
                .setName("精灵")
                .setAppKey(AutoConfig.getConfigAppKey())
                .setAppSecret(AutoConfig.getConfigAppSecret())
                .setUserDataController(instance)
                .setDebug(true)
                .build();
        FoxSDK.init(this,config);
        if (AutoConfig.TEST == env) {
            FoxBaseLogUtils.getConfig().setLogSwitch(true);
        } else {
            FoxBaseLogUtils.getConfig().setLogSwitch(false);
        }
    }

    public Context getContext(){
        return this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
