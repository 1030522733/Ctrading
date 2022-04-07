package com.example.ctrading.mvvm.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.lifecycle.Observer;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.databinding.ActivityLoginBinding;
import com.example.ctrading.mvvm.model.bean.UserBean;
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
            if (phone.length() == 11 && !password.isEmpty()) {
                login(phone, password);
                binding.aviLogin.show();
                banClick();
            } else {
                Toasty.normal(mContext, "请输入正确的手机号和密码", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String phone, String password) {
        mViewModel.login(phone, password).observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                binding.aviLogin.hide();
                allowClick();
                if (userBean.getData() != null) {
                    if (phone.equals(userBean.getData().getPhone()) &&
                            password.equals(userBean.getData().getPassword())) {
                        Toasty.success(mContext, "登录成功", Toasty.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    } else {
                        Toasty.error(mContext, "密码错误", Toasty.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(mContext, "用户名不存在或密码错误", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 禁止操作
     */
    private void banClick() {
        binding.etLoginPhone.setEnabled(false);
        binding.etLoginPassword.setEnabled(false);
        binding.btLogin.setEnabled(false);
        binding.btRegister.setEnabled(false);
    }

    /**
     * 允许操作
     */
    private void allowClick() {
        binding.etLoginPhone.setEnabled(true);
        binding.etLoginPassword.setEnabled(true);
        binding.btLogin.setEnabled(true);
        binding.btRegister.setEnabled(true);
    }
}
