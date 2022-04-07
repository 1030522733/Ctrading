package com.example.ctrading.mvvm.ui.fragment;

import android.content.Intent;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentMarketBinding;
import com.example.ctrading.mvvm.ui.activity.ReleaseActivity;
import com.example.ctrading.mvvm.viewmodel.FragmentViewModel;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 21:33
 * @Description:
 */
public class MarketFragment extends BaseFrg<FragmentViewModel, FragmentMarketBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void init() {
        binding.btMarketAdd.bringToFront();
    }

    @Override
    protected void runFlow() {
        binding.btMarketAdd.setOnClickListener(view -> startActivity(new Intent(getContext(), ReleaseActivity.class)));
    }
}
