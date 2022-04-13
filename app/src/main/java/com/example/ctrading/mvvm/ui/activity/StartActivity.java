package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
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

        binding.pvStart.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                if (MmkvUtils.getBoolean(Constant.IS_LOGIN)){
                    startActivity(new Intent(StartActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(StartActivity.this,LoginActvity.class));
                }
                finish();
            }
        });
    }
}
