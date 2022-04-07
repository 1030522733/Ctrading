package com.example.ctrading.app.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @Author: JianTours
 * @Data: 2021/12/20 21:08
 * @Description:基础返回类
 */
public class BaseResponse {
    //返回码
    @SerializedName("res_code")
    @Expose
    public Integer responseCode;

    //错误信息
    @SerializedName("res_error")
    @Expose
    public String responseError;
}
