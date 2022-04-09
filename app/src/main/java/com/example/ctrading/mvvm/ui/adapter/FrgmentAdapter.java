package com.example.ctrading.mvvm.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ctrading.app.global.Constant;
import com.example.ctrading.mvvm.ui.fragment.MarketFragment;
import com.example.ctrading.mvvm.ui.fragment.MatchFragment;
import com.example.ctrading.mvvm.ui.fragment.SquareFrgment;

/**
 * @Author: JianTours
 * @Data: 2021/12/23 20:29
 * @Description:FragmentViewPager2Adapter
 */
public class FrgmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private MarketFragment marketFragment = null;
    private SquareFrgment squareFrgment = null;
    private MatchFragment matchFragment = null;

    public FrgmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        marketFragment = new MarketFragment();
        squareFrgment = new SquareFrgment();
        matchFragment = new MatchFragment();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = marketFragment;
                break;
            case 1:
                fragment = squareFrgment;
                break;
            case 2:
                fragment = matchFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
