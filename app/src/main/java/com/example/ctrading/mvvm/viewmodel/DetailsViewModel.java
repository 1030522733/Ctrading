package com.example.ctrading.mvvm.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ctrading.app.base.BaseVM;
import com.example.ctrading.app.network.BaseObserver;
import com.example.ctrading.app.network.NetworkApi;
import com.example.ctrading.app.network.api.ApiService;
import com.example.ctrading.mvvm.model.bean.ProjectBean;

/**
 * @Author: JianTours
 * @Data: 2022/4/13 23:38
 * @Description:
 */
public class DetailsViewModel extends BaseVM {
    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<ProjectBean> updateProject(ProjectBean.DataBean.ProjectsBean projectsBean){
        MutableLiveData<ProjectBean> mutableLiveData = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.updateProject(projectsBean).compose(NetworkApi.applySchedulers(new BaseObserver<ProjectBean>() {
            @Override
            public void onSucceed(ProjectBean projectBean) {
                mutableLiveData.setValue(projectBean);
            }

            @Override
            public void onFailure(Throwable e) {
            }
        }));
        return mutableLiveData;
    }
}
