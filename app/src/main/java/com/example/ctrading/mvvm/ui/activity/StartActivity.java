package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MVUtils;
import com.example.ctrading.databinding.ActivityStartBinding;
import com.example.ctrading.mvvm.viewmodel.StartViewModel;

import me.wangyuwei.particleview.ParticleView;

/**
 * @Author: JianTours
 * @Data: 2022/4/6 21:02
 * @Description:
 */
public class StartActivity extends BaseAct<StartViewModel, ActivityStartBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void runFlow() {
        binding.pvStart.startAnim();

        SharedPreferences sp = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        binding.pvStart.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                if (sp.getBoolean("isLogin",false)){
                    startActivity(new Intent(StartActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(StartActivity.this,LoginActvity.class));
                }
                finish();
            }
        });
    }
}
