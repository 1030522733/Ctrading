package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.ctrading.R;
import com.example.ctrading.app.base.App;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.databinding.ActivityMainBinding;
import com.example.ctrading.mvvm.ui.adapter.FrgmentAdapter;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 20:02
 * @Description:
 */
public class MainActivity extends BaseAct<MainViewModel, ActivityMainBinding> {


    /**
     * title:标题栏文字
     * titleIcons:标题栏图标
     * navigation:导航栏文字
     * navigationIcons:导航栏图标
     */
    private final int[] title = {R.string.market, R.string.Square, R.string.Matching};
    private final int[] navigation = {R.string.market, R.string.Square, R.string.Matching};
    private final int[] navigationIcons = {R.drawable.select_market, R.drawable.select_square,
            R.drawable.select_match};
    /**
     * 标题栏
     */
    private ImageView ivToolbar;
    private TextView tvToolbar;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutMain;
        ivToolbar = (ImageView) toolbar.getViewById(R.id.ivLoginPassword);
        tvToolbar = (TextView) toolbar.getViewById(R.id.tvMain);
        tvToolbar.setText(title[0]);

        FrgmentAdapter frgAdapter = new FrgmentAdapter(getSupportFragmentManager(), this);
        binding.vpHome.setAdapter(frgAdapter);
        binding.vpHome.setOffscreenPageLimit(1);
        binding.tbHome.setupWithViewPager(binding.vpHome);
    }

    @Override
    protected void runFlow() {
        //滑动监听该表标题栏
        binding.vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvToolbar.setText(title[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //底部UI改变
        for (int i = 0; i < binding.tbHome.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tbHome.getTabAt(i);
            assert tab != null;
            tab.setCustomView(setTabView(this, i));
        }
    }

    /**
     * 自定义底部Tab
     */
    public View setTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_main,null);
        TextView textView = (TextView) view.findViewById(R.id.tvTabMain);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivTabMain);
        textView.setText(navigation[position]);
        textView.setTextColor(binding.tbHome.getTabTextColors());
        imageView.setImageResource(navigationIcons[position]);
        return view;
    }
}
