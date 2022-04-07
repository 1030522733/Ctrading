package com.example.ctrading.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: JianTours
 * @Data: 2021/12/21 22:41
 * @Description: 封装基类Fragment
 */
public abstract class BaseFrg<VM extends BaseVM, VDB extends ViewDataBinding> extends Fragment {

    /**
     * 上下文
     */
    public View mView;
    protected VM mViewModel;
    protected VDB binding;

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     *具体业务
     */
    protected abstract void runFlow();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        createViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        runFlow();
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
}
