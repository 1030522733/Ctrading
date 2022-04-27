package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.databinding.ActivityInfoBinding;
import com.example.ctrading.mvvm.model.bean.JsonBean;
import com.example.ctrading.mvvm.model.bean.UserBean;
import com.example.ctrading.mvvm.ui.parts.LogOutPopup;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * @Author: JianTours
 * @Data: 2022/4/27 13:42
 * @Description:
 */
public class InfoActivity extends BaseAct<MainViewModel, ActivityInfoBinding> {

    /**
     * 是否正处于编辑状态
     */
    private boolean isEdit = false;

    /**
     * 数据请求完成
     */
    private boolean isComplete = false;

    /**
     * 数据
     */
    List<String> listSex = new ArrayList<>();
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutInfo;
        ImageView ivBack = (ImageView) toolbar.getViewById(R.id.ivCustom);
        TextView textView = (TextView) toolbar.getViewById(R.id.tvCustom);
        textView.setText("个人资料");
        ivBack.setOnClickListener(view -> {
            if (isEdit){
                LogOutPopup logOutPopup = new LogOutPopup(mContext, 3);
                new XPopup.Builder(mContext).asCustom(logOutPopup).show();
            }else {
                finish();
            }
        });

        initData();
        banEdit();
        getUser(MmkvUtils.getString(Constant.MY_PHONE));
    }

    @Override
    protected void runFlow() {
        binding.btInfo.setOnClickListener(view -> {
            if (isComplete) {
                if (isEdit) {
                    UserBean.DataBean user = new UserBean.DataBean();
                    String name = binding.tvInfoName.getText().toString();
                    String sex = binding.tvInfoSex.getText().toString();
                    String org = binding.tvInfoOrg.getText().toString();
                    String address = binding.tvInfoAddress.getText().toString();
                    user.setPhone(MmkvUtils.getString(Constant.MY_PHONE));
                    user.setPassword(MmkvUtils.getString(Constant.MY_PASSWORD));
                    user.setName(name);
                    user.setSex(sex);
                    user.setOrganization(org);
                    user.setAddress(address);
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(org)) {
                        updataUser(user);
                        isEdit = false;
                        banEdit();
                    }else {
                        Toasty.normal(mContext,"请完整填写信息",Toasty.LENGTH_SHORT).show();
                    }
                } else {
                    isEdit = true;
                    allowEdit();
                }
            } else {
                Toasty.error(mContext, "数据尚未加载完成", Toasty.LENGTH_SHORT).show();
            }
        });

        binding.tvInfoSex.setOnClickListener(view -> {
            OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    String flag = listSex.get(options1);
                    binding.tvInfoSex.setText(flag);
                }
            })
                    .setLineSpacingMultiplier(2.0f)
                    .setTitleText("性别")
                    .isAlphaGradient(true)
                    .build();
            pvOptions.setPicker(listSex);
            pvOptions.show();
        });

        binding.tvInfoAddress.setOnClickListener(view -> {
            OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String opt1tx = options1Items.size() > 0 ?
                            options1Items.get(options1).getPickerViewText() : "";
                    String opt2tx = options2Items.size() > 0
                            && options2Items.get(options1).size() > 0 ?
                            options2Items.get(options1).get(options2) : "";
                    String opt3tx = options2Items.size() > 0
                            && options3Items.get(options1).size() > 0
                            && options3Items.get(options1).get(options2).size() > 0 ?
                            options3Items.get(options1).get(options2).get(options3) : "";
                    String tx = opt1tx + opt2tx + opt3tx;
                    binding.tvInfoAddress.setText(tx);
                }
            })
                    .setTitleText("地址选择")
                    .isAlphaGradient(true)
                    .build();
            pickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pickerView.show();
        });
    }

    public void getUser(String phone) {
        mViewModel.getUser(phone).observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                if (userBean.getCode() == 0 && userBean.getData() != null) {
                    binding.tvInfoPhone.setText(userBean.getData().getPhone());
                    binding.tvInfoName.setText(userBean.getData().getName());
                    binding.tvInfoSex.setText(userBean.getData().getSex());
                    binding.tvInfoOrg.setText(userBean.getData().getOrganization());
                    binding.tvInfoAddress.setText(userBean.getData().getAddress());
                    isComplete = true;
                } else {
                    Toasty.error(mContext, "个人信息查询失败，请退出页面重试");
                }
            }
        });
    }

    public void updataUser(UserBean.DataBean user) {
        mViewModel.updateUser(user).observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                if (userBean.getCode() == 0 && userBean.getData() != null) {
                    binding.tvInfoPhone.setText(userBean.getData().getPhone());
                    binding.tvInfoName.setText(userBean.getData().getName());
                    binding.tvInfoSex.setText(userBean.getData().getSex());
                    binding.tvInfoOrg.setText(userBean.getData().getOrganization());
                    binding.tvInfoAddress.setText(userBean.getData().getAddress());
                    Toasty.success(mContext, "信息修改成功", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.error(mContext, "信息修改失败，请重试", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 允许编辑
     */
    private void allowEdit() {
        binding.tvInfoName.setFocusable(true);
        binding.tvInfoName.setFocusableInTouchMode(true);
        binding.tvInfoSex.setEnabled(true);
        binding.tvInfoOrg.setFocusable(true);
        binding.tvInfoOrg.setFocusableInTouchMode(true);
        binding.tvInfoAddress.setEnabled(true);
        binding.btInfo.setText("保存");
    }

    /**
     * 禁止编辑
     */
    private void banEdit() {
        binding.tvInfoName.setFocusable(false);
        binding.tvInfoName.setFocusableInTouchMode(false);
        binding.tvInfoSex.setEnabled(false);
        binding.tvInfoOrg.setFocusable(false);
        binding.tvInfoOrg.setFocusableInTouchMode(false);
        binding.tvInfoAddress.setEnabled(false);
        binding.btInfo.setText("编辑资料");
    }

    /**
     * 数据解析
     */
    private void initData() {
        listSex.add("男");
        listSex.add("女");
        String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
