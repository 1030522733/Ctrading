package com.example.ctrading.mvvm.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ctrading.BuildConfig;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.databinding.ActivityAboutBinding;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;

/**
 * @Author: JianTours
 * @Data: 2022/4/21 23:03
 * @Description:
 */
public class AboutActivity extends BaseAct<MainViewModel, ActivityAboutBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutAbout;
        TextView tvTitle = (TextView) toolbar.getViewById(R.id.tvCustom);
        ImageView ivBack = (ImageView) toolbar.getViewById(R.id.ivCustom);
        tvTitle.setText("关于我们");
        ivBack.setOnClickListener(view -> finish());

        binding.tvAboutName.setText("碳交易管理平台App");
        binding.tvAboutVersion.setText("版本号:"+BuildConfig.VERSION_NAME);
    }

    @Override
    protected void runFlow() {

    }
}
