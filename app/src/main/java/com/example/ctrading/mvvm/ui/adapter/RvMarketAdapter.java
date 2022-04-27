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
        if (item.equals(mData.get(mData.size()-1))){
            helper.setGone(R.id.vvRvMarketMore,true);
        }else {
            helper.setGone(R.id.vvRvMarketMore,false);
        }
        if (item.getProjectType()==0){
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_carbon);
            helper.setText(R.id.tvRvMarketNumber1,"资源数量:");
        }else if (item.getProjectType() == 1){
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_sale);
            helper.setText(R.id.tvRvMarketNumber1,"出售数量:");
            helper.setText(R.id.tvRvMarketPrice1,"出售价格");
        }else {
            helper.setImageResource(R.id.ivRvMarket,R.mipmap.icon_buy);
            helper.setText(R.id.tvRvMarketNumber1,"购买数量:");
            helper.setText(R.id.tvRvMarketPrice1,"购买价格");
        }
        helper.setText(R.id.tvRvMarketAddress,item.getAddress());
        helper.setText(R.id.tvRvMarketType, ParseUtils.getType(item.getResourcesType()));
        helper.setText(R.id.tvRvMarketNumber,item.getNumber()+" 吨");
        helper.setText(R.id.tvRvMarketPrice,item.getPrice()+"元/吨");
        helper.setText(R.id.tvRvMarketOrg,item.getOrganization());
    }
}
