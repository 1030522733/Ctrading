package com.example.ctrading.mvvm.ui.fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentMatchBinding;
import com.example.ctrading.mvvm.viewmodel.FragmentViewModel;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 21:34
 * @Description:
 */
public class MatchFragment extends BaseFrg<FragmentViewModel, FragmentMatchBinding> {

    private OrderFragment fragment1, fragment2;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match;
    }

    @Override
    protected void init() {
        initSelect(true);
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fragment1 = new OrderFragment(0);
        fTransaction.add(R.id.layoutOrder, fragment1);
        fTransaction.commit();
    }

    @Override
    protected void runFlow() {
        binding.tvMatchUnfinished.setOnClickListener(view -> {
            initSelect(true);
            FragmentTransaction fTransaction = fragmentManager.beginTransaction();
            hideAllFragment(fTransaction);
            if (fragment1 == null) {
                fragment1 = new OrderFragment(0);
                fTransaction.add(R.id.layoutOrder, fragment1);
            } else {
                fTransaction.show(fragment1);
            }
            fTransaction.commit();
        });

        binding.tvMatchAll.setOnClickListener(view -> {
            initSelect(false);
            FragmentTransaction fTransaction = fragmentManager.beginTransaction();
            hideAllFragment(fTransaction);
            if (fragment2 == null) {
                fragment2 = new OrderFragment(1);
                fTransaction.add(R.id.layoutOrder, fragment2);
            } else {
                fTransaction.show(fragment2);
            }
            fTransaction.commit();
//
//            binding.layoutNormal.setVisibility(View.GONE);
//            binding.layoutOrder.setVisibility(View.VISIBLE);
//            binding.aviMatch.setVisibility(View.VISIBLE);
//            binding.aviMatch.show();
        });
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }
    }

    /**
     * 重置颜色
     */
    private void initSelect(boolean select) {
        binding.tvMatchUnfinished.setSelected(select);
        binding.vvMatchUnfinished.setSelected(!select);
        binding.tvMatchAll.setSelected(!select);
        binding.vvMatchAll.setSelected(select);
        if (select) {
            binding.tvMatchUnfinished.setTextSize(20);
            binding.tvMatchAll.setTextSize(15);
        } else {
            binding.tvMatchUnfinished.setTextSize(15);
            binding.tvMatchAll.setTextSize(20);
        }
    }
}
