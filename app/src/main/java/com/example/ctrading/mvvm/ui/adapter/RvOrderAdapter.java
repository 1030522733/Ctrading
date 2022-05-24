package com.example.ctrading.mvvm.ui.adapter;

import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.ctrading.R;
import com.example.ctrading.app.utils.ParseUtils;
import com.example.ctrading.mvvm.model.bean.ProjectBean;

import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/5/6 22:09
 * @Description:
 */
public class RvOrderAdapter extends BaseQuickAdapter<ProjectBean.DataBean.ProjectsBean, BaseViewHolder> {
    public RvOrderAdapter( List<ProjectBean.DataBean.ProjectsBean> data) {
        super(R.layout.rv_order,data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ProjectBean.DataBean.ProjectsBean projectsBean) {
        if (projectsBean.equals(getData().get(getData().size()-1))){
            baseViewHolder.setGone(R.id.vvRvOrderMore,false);
        }else {
            baseViewHolder.setGone(R.id.vvRvOrderMore,true);
        }
        baseViewHolder.setGone(R.id.btRvOrderMatch,false);

        int[] images = {R.drawable.img_tree0,R.drawable.img_wind0,R.drawable.img_sun0
        ,R.drawable.img_water0,R.drawable.img_thing0,R.drawable.img_biogas0,R.drawable.img_it0};
        if (projectsBean.getResourcesType()<7){
            baseViewHolder.setImageResource(R.id.ivOrderType,images[projectsBean.getResourcesType()]);
        }else {
            baseViewHolder.setImageResource(R.id.ivOrderType,images[0]);
        }

        baseViewHolder.setText(R.id.tvRvOrderOrg,projectsBean.getOrganization());
        baseViewHolder.setText(R.id.tvRvOrderAddress,projectsBean.getAddress());
        baseViewHolder.setText(R.id.tvRvOrderDes,projectsBean.getDetails());
        baseViewHolder.setText(R.id.tvRvOrderType, ParseUtils.getType(projectsBean.getResourcesType()));
        baseViewHolder.setText(R.id.tvRvOrderPrice,"¥"+projectsBean.getPrice());
        baseViewHolder.setText(R.id.tvRvOrderNumber,"x"+projectsBean.getNumber());

        if (projectsBean.getStauts()==0){
            baseViewHolder.setGone(R.id.btRvOrderMatch,false);
            baseViewHolder.setText(R.id.tvOrderStatus,"交易未完成");
            baseViewHolder.setTextColor(R.id.tvOrderStatus, Color.parseColor("#000000"));
            if (projectsBean.getProjectType()==1){
                baseViewHolder.setText(R.id.tvRvOrderMoney,"预计收益: "+
                        projectsBean.getPrice()*projectsBean.getNumber()+"元");
            }else {
                baseViewHolder.setText(R.id.tvRvOrderMoney,"预计付款: "+
                        projectsBean.getPrice()*projectsBean.getNumber()+"元");
            }
        }else {
            baseViewHolder.setGone(R.id.btRvOrderMatch,true);
            baseViewHolder.setText(R.id.tvOrderStatus,"交易成功");
            baseViewHolder.setTextColor(R.id.tvOrderStatus, Color.parseColor("#ffa500"));
            if (projectsBean.getProjectType()==1){
                baseViewHolder.setText(R.id.tvRvOrderMoney,"已获得收益: "+
                        projectsBean.getPrice()*projectsBean.getNumber()+"元");
            }else {
                baseViewHolder.setText(R.id.tvRvOrderMoney,"实付款: "+
                        projectsBean.getPrice()*projectsBean.getNumber()+"元");
            }
        }
    }
}
