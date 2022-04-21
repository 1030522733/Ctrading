package com.example.ctrading.mvvm.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
public class MainViewModel extends BaseVM {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<UserBean> register(UserBean.DataBean dataBean) {
        MutableLiveData<UserBean> mutableLiveData = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.register(dataBean).compose(NetworkApi.applySchedulers(new BaseObserver<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                mutableLiveData.setValue(userBean);
            }

            @Override
            public void onFailure(Throwable e) {
                LogUtils.e(e.toString());
            }
        }));
        return mutableLiveData;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<UserBean> login(String phone, String password) {
        MutableLiveData<UserBean> mutableLiveData = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.login(phone, password).compose(NetworkApi.applySchedulers(new BaseObserver<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                mutableLiveData.setValue(userBean);
            }

            @Override
            public void onFailure(Throwable e) {
                LogUtils.d(e);
            }
        }));
        return mutableLiveData;
    }
}
