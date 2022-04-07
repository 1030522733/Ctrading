package com.example.ctrading.app.network.interptor;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.example.ctrading.app.network.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: JianTours
 * @Data: 2021/12/20 21:36
 * @Description:拦截器
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private INetworkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo) {
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //构建器
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
