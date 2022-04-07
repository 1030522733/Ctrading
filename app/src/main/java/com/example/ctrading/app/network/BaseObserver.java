package com.example.ctrading.app.network;

import androidx.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author: JianTours
 * @Data: 2021/12/20 21:11
 * @Description:基础Observer
 */
public abstract class BaseObserver<T> implements Observer<T> {
    //开始
    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    //继续

    @Override
    public void onNext(@NonNull T t) {
        onSucceed(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {
    }

    //成功与失败
    public abstract void onSucceed(T t);

    public abstract void onFailure(Throwable e);
}
