package com.example.ctrading.app.network;

import android.app.Application;

/**
 * @Author: JianTours
 * @Data: 2021/12/19 22:29
 * @Description:
 */
public interface INetworkRequiredInfo {
    /**
    *获取app版本名
    */
    String getAppVersionName();

    /**
     *获取版本号
     */
    String getAppVersionCode();

    /**
     *是否为Debug模式
     */
    boolean isDebug();

    /**
     *获取全局上下文参数
     */
    Application getApplicationContext();
}
