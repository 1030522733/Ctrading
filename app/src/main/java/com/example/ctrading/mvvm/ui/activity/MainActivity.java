package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.EventBusTag;
import com.example.ctrading.app.utils.CacheUtils;
import com.example.ctrading.databinding.ActivityMainBinding;
import com.example.ctrading.mvvm.ui.adapter.FrgmentAdapter;
import com.example.ctrading.mvvm.ui.parts.LogOutPopup;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;

import org.simple.eventbus.Subscriber;

import java.io.File;

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

    /**
     *头部
     */
    private TextView tvCache;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutMain;
        ivToolbar = (ImageView) toolbar.getViewById(R.id.ivMain);
        tvToolbar = (TextView) toolbar.getViewById(R.id.tvMain);
        tvToolbar.setText(title[0]);

        MenuItem menuItem = binding.navHome.getMenu().findItem(R.id.navClear);
        tvCache = menuItem.getActionView().findViewById(R.id.tvCacheNumber);
        try {
            tvCache.setText(CacheUtils.getTotalCacheSize(this));
        }catch (Exception e){
            e.printStackTrace();
        }

        FrgmentAdapter frgAdapter = new FrgmentAdapter(getSupportFragmentManager(), this);
        binding.vpHome.setAdapter(frgAdapter);
        binding.vpHome.setOffscreenPageLimit(1);
        binding.tbHome.setupWithViewPager(binding.vpHome);
    }

    @Override
    protected void runFlow() {
        //Navigation
        ivToolbar.setOnClickListener(view -> {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        });

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

        /**
         * Navigation点击事件
         */
        binding.navHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navInfo:
                        startActivity(new Intent(mContext,InfoActivity.class));
                        break;
                    case R.id.navOrder:
                        break;
                    case R.id.navClear:
                        LogOutPopup logOutPopup = new LogOutPopup(mContext, 2);
                        new XPopup.Builder(mContext).asCustom(logOutPopup).show();
                        break;
                    case R.id.navAbout:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        break;
                    case R.id.navOut:
                        LogOutPopup logOutPopup2 = new LogOutPopup(mContext, 0);
                        new XPopup.Builder(mContext).asCustom(logOutPopup2).show();
                        break;
                }
                //关闭DrawerLayout
                binding.drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    /**
     * 自定义底部Tab
     */
    public View setTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_main, null);
        TextView textView = (TextView) view.findViewById(R.id.tvTabMain);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivTabMain);
        textView.setText(navigation[position]);
        textView.setTextColor(binding.tbHome.getTabTextColors());
        imageView.setImageResource(navigationIcons[position]);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            tvCache.setText(CacheUtils.getTotalCacheSize(this));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscriber(tag = EventBusTag.CLEAR_OK)
    public void clearOk(Message message){
        try {
            tvCache.setText(CacheUtils.getTotalCacheSize(this));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
