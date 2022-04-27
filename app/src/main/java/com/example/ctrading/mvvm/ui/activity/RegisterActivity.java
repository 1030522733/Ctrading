package com.example.ctrading.mvvm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.databinding.ActivityRegisterBinding;
import com.example.ctrading.mvvm.model.bean.JsonBean;
import com.example.ctrading.mvvm.model.bean.UserBean;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * @Author: JianTours
 * @Data: 2022/4/19 23:00
 * @Description:
 */
public class RegisterActivity extends BaseAct<MainViewModel, ActivityRegisterBinding> {

    /**
     * 数据
     */
    List<String> listSex = new ArrayList<>();
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutRegister;
        ImageView ivBack = (ImageView) toolbar.getViewById(R.id.ivCustom);
        ivBack.setOnClickListener(view -> finish());

        initData();
    }

    @Override
    protected void runFlow() {
        binding.etRegisterSex.setOnClickListener(view -> {
            OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    String flag = listSex.get(options1);
                    binding.etRegisterSex.setText(flag);
                }
            })
                    .setLineSpacingMultiplier(2.0f)
                    .setTitleText("性别")
                    .isAlphaGradient(true)
                    .build();
            pvOptions.setPicker(listSex);
            pvOptions.show();
        });

        binding.etRegisterAddress.setOnClickListener(view -> {
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
                    binding.etRegisterAddress.setText(tx);
                }
            })
                    .setTitleText("地址选择")
                    .isAlphaGradient(true)
                    .build();
            pickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pickerView.show();
        });

        binding.btRegisterOk.setOnClickListener(view -> {
            String phone = binding.etRegisterPhone.getText().toString();
            String password = binding.etRegisterPassword.getText().toString();
            String name = binding.etRegisterName.getText().toString();
            String org = binding.etRegisterOrg.getText().toString();
            String sex = binding.etRegisterSex.getText().toString();
            String address = binding.etRegisterAddress.getText().toString();
            if (phone.length() == 11 && !TextUtils.isEmpty(password)
                    && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex)
                    && !TextUtils.isEmpty(org) && !TextUtils.isEmpty(address)) {
                UserBean.DataBean dataBean = new UserBean.DataBean();
                dataBean.setPhone(phone);
                dataBean.setPassword(password);
                dataBean.setAddress(address);
                dataBean.setSex(sex);
                dataBean.setOrganization(org);
                dataBean.setName(name);
                register(dataBean);
                binding.aviRegister.show();
            } else {
                Toasty.normal(this, "请正确填写信息", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    public void register(UserBean.DataBean dataBean) {
        mViewModel.register(dataBean).observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                binding.aviRegister.hide();
                if (userBean.getCode() == 0) {
                    Toasty.success(mContext, "注册成功,已为您自动登录", Toasty.LENGTH_SHORT).show();
                    MmkvUtils.put(Constant.MY_PHONE, userBean.getData().getPhone());
                    MmkvUtils.put(Constant.MY_PASSWORD,userBean.getData().getPassword());
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                } else {
                    Toasty.error(mContext, "该账号已被注册", Toasty.LENGTH_SHORT).show();
                }
            }
        });
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
