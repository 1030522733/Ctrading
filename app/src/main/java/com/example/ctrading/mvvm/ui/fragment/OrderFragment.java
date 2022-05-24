package com.example.ctrading.mvvm.ui.fragment;

import android.content.Intent;

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
import com.example.ctrading.mvvm.ui.activity.ReleaseActivity;
import com.example.ctrading.mvvm.ui.adapter.RvOrderAdapter;
import com.example.ctrading.mvvm.ui.parts.LogOutPopup;
import com.example.ctrading.mvvm.ui.parts.SpaceItemDecoration;
import com.example.ctrading.mvvm.viewmodel.OrderViewModel;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

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
        rvOrderAdapter.addChildClickViewIds(R.id.btRvOrderAgain, R.id.btRvOrderDel, R.id.btRvOrderMatch);
        rvOrderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btRvOrderAgain:
                    startActivity(new Intent(this.getContext(),ReleaseActivity.class));
                    break;
                case R.id.btRvOrderDel:
                    ProjectBean.DataBean.ProjectsBean bean=
                            (ProjectBean.DataBean.ProjectsBean) adapter.getData().get(position);
                    String projectId = bean.getProjectId();
                    deleteProject(projectId,position);
//                    LogOutPopup logOutPopup = new LogOutPopup(this.getContext(), 4);
//                    new XPopup.Builder(this.getContext()).asCustom(logOutPopup).show();
                    break;
                case R.id.btRvOrderMatch:
                    break;
                default:
                    break;
            }
        });
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

    private void getProject(int flag) {
        String phone = MmkvUtils.getString(Constant.MY_PHONE);
        mViewModel.getProject(phone).observe(this, new Observer<ProjectBean>() {
            @Override
            public void onChanged(ProjectBean projectBean) {
                if (projectBean != null){
                    if (projectBean.getCode() == 0) {
                        list.clear();
                        if (flag == 0) {
                            for (int i = 0; i < projectBean.getData().getProjects().size(); i++) {
                                if (projectBean.getData().getProjects().get(i).getStauts() == 0) {
                                    list.add(projectBean.getData().getProjects().get(i));
                                }
                            }
                        } else {
                            list.addAll(projectBean.getData().getProjects());
                            LogUtils.json(list.get(0));
                        }
                        rvOrderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 删除订单
     */
    private void deleteProject(String projectId,int position) {
        mViewModel.deleteProject(projectId).observe(this, new Observer<ProjectBean>() {
            @Override
            public void onChanged(ProjectBean projectBean) {
                if (projectBean.getCode()==0){
                    Toasty.success(getContext(), "删除成功", Toasty.LENGTH_SHORT).show();
                    list.remove(position);
                    rvOrderAdapter.notifyDataSetChanged();
                }else {
                    Toasty.error(getContext(), "删除失败，未知错误", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }
}
