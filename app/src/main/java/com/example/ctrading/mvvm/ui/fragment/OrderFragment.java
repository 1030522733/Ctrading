package com.example.ctrading.mvvm.ui.fragment;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.databinding.FragmentOrderBinding;
import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.ui.adapter.RvOrderAdapter;
import com.example.ctrading.mvvm.ui.parts.SpaceItemDecoration;
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
    List<ProjectBean.DataBean.ProjectsBean> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    public OrderFragment(int flag) {
        this.flag = flag;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false);
        rvOrderAdapter = new RvOrderAdapter(list);
        rvOrderAdapter.setAnimationFirstOnly(false);
        rvOrderAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        binding.rvOrder.setLayoutManager(linearLayoutManager);
        binding.rvOrder.setHasFixedSize(true);
        binding.rvOrder.addItemDecoration(new SpaceItemDecoration(2));
        binding.rvOrder.setAdapter(rvOrderAdapter);

        getProject(flag);
    }

    @Override
    protected void runFlow() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getProject(flag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getProject(int flag){
        String phone = MmkvUtils.getString(Constant.MY_PHONE);
        mViewModel.getProject(phone).observe(this, new Observer<ProjectBean>() {
            @Override
            public void onChanged(ProjectBean projectBean) {
                if (projectBean.getCode()==0){
                    list.clear();
                    if (flag==0){
                        for (int i = 0;i<projectBean.getData().getProjects().size();i++){
                            if (projectBean.getData().getProjects().get(i).getStauts()==0){
                                list.add(projectBean.getData().getProjects().get(i));
                            }
                        }
                    }else {
                        list.addAll(projectBean.getData().getProjects());
                        LogUtils.json(list.get(0));
                    }
                    rvOrderAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
