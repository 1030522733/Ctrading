package com.example.ctrading.app.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


import com.blankj.utilcode.util.LogUtils;
import com.example.ctrading.app.network.FrameWorkNetworkInfo;
import com.example.ctrading.app.network.NetworkApi;
import com.example.ctrading.app.utils.MVUtils;
import com.tencent.mmkv.MMKV;

/**
 * @Author: JianTours
 * @Data: 2021/12/19 21:48
 * @Description:配置全局Application
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //MMKV初始化
        MMKV.initialize(this);
        //工具类初始化
        MVUtils.getInstance();
        //网络初始化
        NetworkApi.init(new FrameWorkNetworkInfo(this));
    }


    public static Context getContext() {
        return app;
    }
}
