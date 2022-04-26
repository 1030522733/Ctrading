package com.example.ctrading.mvvm.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ctrading.R;
import com.example.ctrading.app.base.App;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentSquareBinding;
import com.example.ctrading.mvvm.model.bean.ArticleBean;
import com.example.ctrading.mvvm.ui.activity.WebActivity;
import com.example.ctrading.mvvm.ui.adapter.RvArticleAdapter;
import com.example.ctrading.mvvm.viewmodel.FragmentViewModel;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

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

    /**
     * RecyclerView
     */
    RvArticleAdapter rvArticleAdapter;
    List<ArticleBean.DataBean.Bean> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvArticleAdapter = new RvArticleAdapter(list);
        binding.rvArticle.setLayoutManager(linearLayoutManager);
        binding.rvArticle.setAdapter(rvArticleAdapter);
        getAll();

        binding.btGoTop.bringToFront();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void runFlow() {

        binding.btGoTop.setOnClickListener(view -> binding.svSquare.smoothScrollTo(0, 0));
        binding.btGoTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    binding.btGoTop.setBackgroundResource(R.drawable.button_bg_top_white);
                } else {
                    binding.btGoTop.setBackground(null);
                }
                return false;
            }
        });

        rvArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DataBean.Bean bean = (ArticleBean.DataBean.Bean) adapter.getItem(position);
                Intent intent = new Intent(App.getContext(), WebActivity.class);
                switch (view.getId()){
                    case R.id.layoutRvArticle:
                        intent.putExtra("url",bean.getUrl());
                        intent.putExtra("title",bean.getTitle());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAll();
    }

    private void getAll() {
        mViewModel.getAllArticle().observe(this, new Observer<ArticleBean>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArticleBean articleBean) {
                if (articleBean != null && articleBean.getCode() == 0) {
                    list.clear();
                    list.addAll(articleBean.getData().getArticle());
                    rvArticleAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
