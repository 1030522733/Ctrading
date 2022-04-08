package com.example.ctrading.mvvm.ui.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.databinding.FragmentReleaseBinding;
import com.example.ctrading.mvvm.model.bean.JsonBean;
import com.example.ctrading.mvvm.viewmodel.ReleaseViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/7 22:57
 * @Description:
 */
public class ReleaseFragment extends BaseFrg<ReleaseViewModel, FragmentReleaseBinding> {

    /**
     * 第几个fragment标记
     */
    private int flag = 0;

    /**
     * 标题
     */
    private final int[] titleName = {R.string.tvRelease1, R.string.tvRelease2, R.string.tvRelease3};
    private final int[] titleDetails = {R.string.tvReleaseDetails1, R.string.tvReleaseDetails2, R.string.tvReleaseDetails3};
    private final int[] tvIcons = {R.mipmap.icon_release1_tv, R.mipmap.icon_release2_tv, R.mipmap.icon_release3_tv};
    private final int[] bgIcons = {R.mipmap.icon_release1, R.mipmap.icon_release2, R.mipmap.icon_release3};

    /**
     * 选择器数据
     */
    private List<String> listType = new ArrayList<>();
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public ReleaseFragment(int i) {
        this.flag = i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    protected void init() {
        initData();
        binding.tvReleaseName.setText(titleName[flag]);
        binding.tvReleaseDetails.setText(titleDetails[flag]);
        binding.ivRelease.setImageResource(bgIcons[flag]);
        binding.ivReleaseTv.setImageResource(tvIcons[flag]);
        if (flag == 0) {
            binding.layoutPrice.setVisibility(View.GONE);
            binding.layoutDetails.setVisibility(View.GONE);
        } else {
            binding.vvRelease.getLayoutParams().height = 500;
        }
    }

    @Override
    protected void runFlow() {
        binding.etReleaseAddress.setOnClickListener(view -> {
            OptionsPickerView pickerView = new OptionsPickerBuilder(this.getContext(), new OnOptionsSelectListener() {
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
                    binding.etReleaseAddress.setText(tx);
                }
            })
                    .setTitleText("城市选择")
                    .isAlphaGradient(true)
                    .build();
            pickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pickerView.show();
        });

        binding.etReleaseType.setOnClickListener(view -> {
            OptionsPickerView pvOptions = new OptionsPickerBuilder(this.getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    String flag = listType.get(options1);
                    binding.etReleaseType.setText(flag);
                }
            })
                    .setLineSpacingMultiplier(2.0f)
                    .setTitleText("项目类型")
                    .isAlphaGradient(true)
                    .build();
            pvOptions.setPicker(listType);
            pvOptions.show();
        });
    }

    /**
     * 数据解析
     */
    private void initData() {
        listType.add("树林");
        listType.add("风能");
        listType.add("太阳能");
        listType.add("水电");
        listType.add("生物质发电");
        listType.add("沼气发电");
        listType.add("其他");
        String JsonData = getJson(this.getContext(), "province.json");//获取assets目录下的json文件数据
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
