package com.example.ctrading.mvvm.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ctrading.R;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.mvvm.ui.activity.ReleaseActivity;
import com.example.ctrading.mvvm.ui.fragment.MarketFragment;
import com.example.ctrading.mvvm.ui.fragment.MatchFragment;
import com.example.ctrading.mvvm.ui.fragment.ReleaseFragment;
import com.example.ctrading.mvvm.ui.fragment.SquareFrgment;

import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 22:26
 * @Description:
 */
public class ReleaseAdapter extends FragmentPagerAdapter {

    private ReleaseFragment releaseFragment1 = null;
    private ReleaseFragment releaseFragment2 = null;
    private ReleaseFragment releaseFragment3 = null;

    public ReleaseAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        releaseFragment1 = new ReleaseFragment(0);
        releaseFragment2 = new ReleaseFragment(1);
        releaseFragment3 = new ReleaseFragment(2);
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
                fragment = releaseFragment1;
                break;
            case 1:
                fragment = releaseFragment2;
                break;
            case 2:
                fragment = releaseFragment3;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
