package com.example.ctrading.mvvm.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.LogUtils;
import com.example.ctrading.app.base.BaseVM;
import com.example.ctrading.app.network.BaseObserver;
import com.example.ctrading.app.network.NetworkApi;
import com.example.ctrading.app.network.api.ApiService;
import com.example.ctrading.mvvm.model.bean.UserBean;

/**
 * @Author: JianTours
 * @Data: 2022/4/6 21:52
 * @Description:
 */
public class LoginViewModel extends BaseVM{
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void login(int phone,String password){
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.login(phone,password).compose(NetworkApi.applySchedulers(new BaseObserver<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                LogUtils.json(userBean);
            }

            @Override
            public void onFailure(Throwable e) {
                LogUtils.d(e);
            }
        }));
    }
}
