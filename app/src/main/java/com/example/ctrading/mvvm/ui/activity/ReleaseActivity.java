package com.example.ctrading.mvvm.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.databinding.ActivityReleaseBinding;
import com.example.ctrading.mvvm.ui.adapter.ReleaseAdapter;
import com.example.ctrading.mvvm.viewmodel.ReleaseViewModel;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 21:52
 * @Description:
 */
public class ReleaseActivity extends BaseAct<ReleaseViewModel, ActivityReleaseBinding> {

    /**
     * 标题
     */
    ImageView ivBack;

    /**
     * Apapter
     */
    ReleaseAdapter releaseAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutRelease;
        TextView tvTitle = (TextView) toolbar.getViewById(R.id.tvCustom);
        ivBack = (ImageView) toolbar.getViewById(R.id.ivCustom);
        tvTitle.setText("发布需求");

        releaseAdapter = new ReleaseAdapter(getSupportFragmentManager(), this);
        binding.vpRelease.setAdapter(releaseAdapter);
        binding.vpRelease.setOffscreenPageLimit(3);
        binding.vpRelease.setCurrentItem(0);
        UIChange(0);
    }

    @Override
    protected void runFlow() {
        //滑动监听该表标题栏
        binding.tvRelease1.setOnClickListener(view -> binding.vpRelease.setCurrentItem(0));
        binding.tvRelease2.setOnClickListener(view -> binding.vpRelease.setCurrentItem(1));
        binding.tvRelease3.setOnClickListener(view -> binding.vpRelease.setCurrentItem(2));
        binding.vpRelease.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                UIChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ivBack.setOnClickListener(view -> finish());
    }

    /**
     * 选中UI改变
     */
    private void UIChange(int i) {
        binding.tvRelease1.setSelected(false);
        binding.tvRelease2.setSelected(false);
        binding.tvRelease3.setSelected(false);
        switch (i) {
            case 0:
                binding.tvRelease1.setSelected(true);
                break;
            case 1:
                binding.tvRelease2.setSelected(true);
                break;
            default:
                binding.tvRelease3.setSelected(true);
                break;
        }
    }

}
