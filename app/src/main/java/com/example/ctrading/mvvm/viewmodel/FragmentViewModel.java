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
import com.example.ctrading.mvvm.model.bean.ProjectBean;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 20:32
 * @Description:
 */
public class FragmentViewModel extends BaseVM{
    public FragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<ProjectBean> getProjectAll(){
        MutableLiveData<ProjectBean> mutableLiveData = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.queryProjectAll().compose(NetworkApi.applySchedulers(new BaseObserver<ProjectBean>() {
            @Override
            public void onSucceed(ProjectBean projectBean) {
                mutableLiveData.setValue(projectBean);
            }

            @Override
            public void onFailure(Throwable e) {
                mutableLiveData.setValue(null);
            }
        }));
        return mutableLiveData;
    }
}
