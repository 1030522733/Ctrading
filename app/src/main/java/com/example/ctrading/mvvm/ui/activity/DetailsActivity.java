package com.example.ctrading.mvvm.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.global.EventBusTag;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.app.utils.ParseUtils;
import com.example.ctrading.databinding.ActivityDetailsBinding;
import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.ui.parts.LogOutPopup;
import com.example.ctrading.mvvm.viewmodel.DetailsViewModel;
import com.lxj.xpopup.XPopup;

import org.simple.eventbus.Subscriber;

import es.dmoral.toasty.Toasty;

/**
 * @Author: JianTours
 * @Data: 2022/4/13 23:38
 * @Description:
 */
public class DetailsActivity extends BaseAct<DetailsViewModel, ActivityDetailsBinding> {

    ProjectBean.DataBean.ProjectsBean projectsBean;

    /**
     * 是否已下单
     */
    boolean isOrder = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_details;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutDetails;
        TextView tvTitle = (TextView) toolbar.getViewById(R.id.tvCustom);
        ImageView ivBack = (ImageView) toolbar.getViewById(R.id.ivCustom);
        tvTitle.setText("订单详情");
        ivBack.setOnClickListener(view -> finish());

        Intent intent = this.getIntent();
        projectsBean = (ProjectBean.DataBean.ProjectsBean) intent.getSerializableExtra("Details");
        binding.tvDetailsId.setText(projectsBean.getProjectId());
        binding.tvDetailsTime.setText(projectsBean.getTime());
        binding.tvDetailsAddress.setText(projectsBean.getAddress());
        binding.tvDetailsType.setText(ParseUtils.getType(projectsBean.getResourcesType()));
        binding.tvDetailsNumber.setText(projectsBean.getNumber() + "吨");
        binding.tvDetailsPrice.setText(projectsBean.getPrice() + "");
        binding.tvDetailsDis.setText(projectsBean.getDetails());
    }

    @Override
    protected void runFlow() {
        binding.vvDetails.setOnClickListener(view -> {
            if (!isOrder) {
                Toasty.warning(this, "下单后才能查看负责人和联系电话哟", Toasty.LENGTH_SHORT).show();
            }
        });

        binding.btDetails.setOnClickListener(view -> {
            if (!isOrder) {
                LogOutPopup logOutPopup = new LogOutPopup(mContext,1);
                new XPopup.Builder(mContext).asCustom(logOutPopup).show();
            }
        });
    }

    /**
     * 下单信息
     */
    @Subscriber(tag = EventBusTag.ORDER_OK)
    private void orderOkMessage(Message message){
        projectsBean.setStauts(1);
        if (!TextUtils.isEmpty(projectsBean.getPhoneA())) {
            projectsBean.setPhoneB(MmkvUtils.getString(Constant.MY_PHONE));
        } else {
            projectsBean.setPhoneA(MmkvUtils.getString(Constant.MY_PHONE));
        }
        updateProject();
    }

    private void updateProject() {
        mViewModel.updateProject(projectsBean).observe(this, new Observer<ProjectBean>() {
            @SuppressLint({"NotifyDataSetChanged", "ResourceAsColor"})
            @Override
            public void onChanged(ProjectBean projectBean) {
                if (projectBean.getCode() == 0) {
                    Toasty.success(mContext, "下单成功", Toasty.LENGTH_SHORT).show();
                    isOrder = true;
                    binding.tvDetailsPeople.setText(projectsBean.getPeople());
                    String phone = "";
                    if (!TextUtils.isEmpty(projectsBean.getPhoneA())) {
                        phone = projectsBean.getPhoneA();
                    } else {
                        phone = projectsBean.getPhoneB();
                    }
                    binding.tvDetailsPhone.setText(phone);
                    binding.btDetails.setText("您已成功下单");
                    binding.btDetails.setTextColor(R.color.gray);
                    binding.btDetails.setBackgroundResource(R.drawable.button_bg_white);
                    binding.btDetails.setEnabled(false);
                } else {
                    Toasty.error(mContext, "下单失败", Toasty.LENGTH_SHORT).show();
                    isOrder = false;
                }
            }
        });
    }
}
