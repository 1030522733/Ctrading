package com.example.ctrading.app.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @Author: JianTours
 * @Data: 2021/12/19 20:22
 * @Description: 基类VIewModel
 */
public abstract class BaseVM extends AndroidViewModel {
    public BaseVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
