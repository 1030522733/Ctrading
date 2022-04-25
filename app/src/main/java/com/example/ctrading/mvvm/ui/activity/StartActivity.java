package com.example.ctrading.mvvm.ui.activity;

import android.Manifest;
import android.content.Intent;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.databinding.ActivityStartBinding;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;
import com.permissionx.guolindev.PermissionX;

import es.dmoral.toasty.Toasty;
import me.wangyuwei.particleview.ParticleView;

/**
 * @Author: JianTours
 * @Data: 2022/4/6 21:02
 * @Description:
 */
public class StartActivity extends BaseAct<MainViewModel, ActivityStartBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        PermissionX.init(this).
                permissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "碳交易管理系统需要您同意以下授权才能正常使用", "同意", "拒绝");
                })
                .request(((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        binding.pvStart.startAnim();
                    } else {
                        Toasty.error(this,"您拒绝了一下权限:"+deniedList+"可能影响您的正常使用",Toasty.LENGTH_SHORT).show();
                        binding.pvStart.startAnim();
                    }
                }));
    }

    @Override
    protected void runFlow() {
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
