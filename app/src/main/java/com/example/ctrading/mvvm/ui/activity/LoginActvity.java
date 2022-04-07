package com.example.ctrading.mvvm.ui.activity;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.example.ctrading.R;
import com.example.ctrading.app.base.App;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.databinding.ActivityLoginBinding;
import com.example.ctrading.mvvm.viewmodel.LoginViewModel;

import es.dmoral.toasty.Toasty;

/**
 * @Author: JianTours
 * @Data: 2022/4/6 21:52
 * @Description:
 */
public class LoginActvity extends BaseAct<LoginViewModel, ActivityLoginBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void runFlow() {
        binding.btLogin.setOnClickListener(view -> {
            String phone = binding.etLoginPhone.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            int number = 0;
            if (!TextUtils.isEmpty(phone)) {
                number = Integer.valueOf(phone);
            }
            if (phone.length() == 11 && !password.isEmpty()) {
                mViewModel.login(number, password);
            } else {
                Toasty.normal(mContext, "请输入正确的手机号和密码",Toasty.LENGTH_SHORT).show();
            }
        });
    }
}
