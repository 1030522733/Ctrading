package com.example.ctrading.mvvm.ui.fragment;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentSquareBinding;
import com.example.ctrading.mvvm.viewmodel.FragmentViewModel;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.DrawableIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2021/12/23 20:33
 * @Description:广场Frg
 */
public class SquareFrgment extends BaseFrg<FragmentViewModel, FragmentSquareBinding> {

    /**
     * banner数据
     */
    private List<Integer> listBanner = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void init() {
        listBanner.add(R.drawable.img_banner1);
        listBanner.add(R.drawable.img_banner2);
        listBanner.add(R.drawable.img_banner3);
        binding.bannerSquare.setAdapter(new BannerImageAdapter<Integer>(listBanner) {
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, Integer integer, int i, int i1) {
                bannerImageHolder.imageView.setImageResource(listBanner.get(i));
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this.getContext()))
                .setIndicatorSpace(60)
        .setBannerRound(10f);
    }

    @Override
    protected void runFlow() {

    }
}
