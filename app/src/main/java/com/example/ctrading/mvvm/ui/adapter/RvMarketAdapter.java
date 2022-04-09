package com.example.ctrading.mvvm.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ctrading.R;
import com.example.ctrading.app.utils.ParseUtils;
import com.example.ctrading.mvvm.model.bean.ProjectBean;

import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/9 13:41
 * @Description:
 */
public class RvMarketAdapter extends BaseQuickAdapter<ProjectBean.DataBean.ProjectsBean, BaseViewHolder> {


    public RvMarketAdapter(@Nullable List<ProjectBean.DataBean.ProjectsBean> data) {
        super(R.layout.rv_market, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProjectBean.DataBean.ProjectsBean item) {
        if (item.getProjectType()==0){
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_carbon);
            helper.setText(R.id.tvRvMarketNumber1,"资源数量:");
        }else if (item.getProjectType() == 1){
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_sale);
            helper.setText(R.id.tvRvMarketNumber1,"出售数量:");
        }else {
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_buy);
            helper.setText(R.id.tvRvMarketNumber1,"购买数量:");
        }
        helper.setText(R.id.tvRvMarketAddress,item.getAddress());
        helper.setText(R.id.tvRvMarketType, ParseUtils.getType(item.getResourcesType()));
        helper.setText(R.id.tvRvMarketNunber,item.getNumber()+" 吨");
        helper.setText(R.id.tvRvMarketOrg,item.getOrganization());
    }
}
