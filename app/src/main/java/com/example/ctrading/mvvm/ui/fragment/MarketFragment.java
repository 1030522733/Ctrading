package com.example.ctrading.mvvm.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentMarketBinding;
import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.ui.activity.DetailsActivity;
import com.example.ctrading.mvvm.ui.activity.ReleaseActivity;
import com.example.ctrading.mvvm.ui.adapter.RvMarketAdapter;
import com.example.ctrading.mvvm.ui.parts.SpaceItemDecoration;
import com.example.ctrading.mvvm.viewmodel.FragmentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 21:33
 * @Description:
 */
public class MarketFragment extends BaseFrg<FragmentViewModel, FragmentMarketBinding> {

    /**
     * RecyclerView
     */
    RvMarketAdapter rvMarketAdapter;
    List<ProjectBean.DataBean.ProjectsBean> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void init() {
        binding.btMarketAdd.bringToFront();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rvMarketAdapter = new RvMarketAdapter(list);
        rvMarketAdapter.setAnimationFirstOnly(false);
        rvMarketAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        binding.rvMarket.setLayoutManager(linearLayoutManager);
        binding.rvMarket.setHasFixedSize(true);
        binding.rvMarket.addItemDecoration(new SpaceItemDecoration(2));
        binding.rvMarket.setAdapter(rvMarketAdapter);
        getProjectAll();
    }

    @Override
    protected void runFlow() {
        binding.btMarketAdd.setOnClickListener(view -> startActivity(new Intent(getContext(), ReleaseActivity.class)));
        
        rvMarketAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProjectBean.DataBean.ProjectsBean projectBean
                    = (ProjectBean.DataBean.ProjectsBean) adapter.getData().get(position);
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Details", projectBean);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProjectAll();
    }

    private void getProjectAll() {
        mViewModel.getProjectAll().observe(this, new Observer<ProjectBean>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ProjectBean projectBean) {
                if (projectBean!=null && projectBean.getCode()==0){
                    list.clear();
                    list.addAll(projectBean.getData().getProjects());
                    rvMarketAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
