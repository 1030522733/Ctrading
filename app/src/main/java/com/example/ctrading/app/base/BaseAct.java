package com.example.ctrading.app.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import org.simple.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: JianTours
 * @Data: 2021/12/19 20:21
 * @Description: 基类Activity
 */
public abstract class BaseAct<VM extends BaseVM, VDB extends ViewDataBinding>
        extends AppCompatActivity {

    /**
     * 上下文
     */
    public Context mContext;
    protected VM mViewModel;
    protected VDB binding;

    /**
     * 获取activity文件，初始化binding
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     */
    protected abstract void init();


    /**
     * 具体业务
     */
    protected abstract void runFlow();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentViewId());
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        setStatusBar();
        createViewModel();
        init();
        runFlow();

        EventBus.getDefault().register(this);
    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class<BaseVM> modelClass;
            // 返回直接继承的父类（包含泛型参数）
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class<BaseVM>) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                // 如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseVM.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }

    /**
     * 沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            //View.SYSTEM_UI_FLAG_FULLSCREEN 属性会让手机状态栏自动进入隐藏状态
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            view.setSystemUiVisibility(option);
            //状态栏透明
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
