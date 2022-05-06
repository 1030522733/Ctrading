package com.example.ctrading.mvvm.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ctrading.R;

import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/5/6 22:09
 * @Description:
 */
public class RvOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RvOrderAdapter( List<String> data) {
        super(R.layout.rv_order,data);
    }

    @Override
    protected void convert( BaseViewHolder helper, String item) {

    }
}
