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
        final int env = FoxBaseSPUtils.getInstance().getInt("env_config", AutoConfig.RELEASE);
        AutoConfig.init(env);
        FoxUserDataController userDataController = FoxUserDataController.getInstance();
        userDataController.setUserAgree(true);
        FoxConfig config = new FoxConfig.Builder()
                .setAppId("89763")
                .setVersion("1.0.0")
                .setBundle("com.dt.adx")
                .setName("计步")
                .setAppKey(AutoConfig.getConfigAppKey())
                .setAppSecret(AutoConfig.getConfigAppSecret())
                .setUserDataController(userDataController)
                .setDebug(true)
                .build();
        FoxSDK.init(this,config);
    }

    public Context getContext(){
        return this;
    }

}
