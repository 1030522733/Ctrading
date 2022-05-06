package com.example.ctrading.mvvm.ui.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentOrderBinding;
import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.ui.adapter.RvMarketAdapter;
import com.example.ctrading.mvvm.ui.adapter.RvOrderAdapter;
import com.example.ctrading.mvvm.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/5/6 21:29
 * @Description:
 */
public class OrderFragment extends BaseFrg<OrderViewModel, FragmentOrderBinding> {

    private int flag = 0;

    RvOrderAdapter rvOrderAdapter;
    List<String> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    public OrderFragment(int flag){
        this.flag = flag;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        list.add("d");
        list.add("d");
        list.add("d");
        list.add("d");
        list.add("d");
        list.add("d");
        list.add("d");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrderAdapter = new RvOrderAdapter(list);
        binding.rvOrder.setLayoutManager(linearLayoutManager);
        binding.rvOrder.setAdapter(rvOrderAdapter);
    }

    @Override
    protected void runFlow() {

    }
}
