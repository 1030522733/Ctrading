package com.example.ctrading.app.network;

import android.app.Application;

import com.example.ctrading.BuildConfig;

/**
 * @Author: JianTours
 * @Data: 2021/12/21 20:10
 * @Description:网络访问信息
 */
public class FrameWorkNetworkInfo implements INetworkRequiredInfo{

    private final Application application;

    public FrameWorkNetworkInfo(Application application){
        this.application = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return BuildConfig.BUILD_TYPE;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return application;
    }
}
