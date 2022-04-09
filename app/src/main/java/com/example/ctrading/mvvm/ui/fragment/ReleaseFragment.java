package com.example.ctrading.mvvm.ui.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseFrg;
import com.example.ctrading.app.global.EventBusTag;
import com.example.ctrading.app.utils.ParseUtils;
import com.example.ctrading.databinding.FragmentReleaseBinding;
import com.example.ctrading.mvvm.model.bean.JsonBean;
import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.viewmodel.ReleaseViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.simple.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

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

        binding.btReleaseOk.setOnClickListener(view -> {
            String address = binding.etReleaseAddress.getText().toString();
            String Type = binding.etReleaseType.getText().toString();
            String Number = binding.etReleaseNumber.getText().toString();
            String Price = binding.etReleasePrice.getText().toString();
            String organization = binding.etReleaseOrganization.getText().toString();
            String people = binding.etReleasePeople.getText().toString();
            String email = binding.etReleaseEmail.getText().toString();
            String details = binding.etReleaseDetails.getText().toString();

            if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(Type)
                    && !TextUtils.isEmpty(Number) && !TextUtils.isEmpty(organization)
                    && !TextUtils.isEmpty(people) && !TextUtils.isEmpty(email)) {

                ProjectBean.DataBean.ProjectsBean projectsBean = new ProjectBean.DataBean.ProjectsBean();
                int number = Integer.parseInt(Number);
                int type = ParseUtils.getType(Type);

                //id
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");// HH:mm:ss
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd/");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                String id = simpleDateFormat.format(date);
                String day = simpleDateFormat2.format(date);

                projectsBean.setProjectId(id);
                projectsBean.setStauts(0);
                projectsBean.setProjectType(flag);
                projectsBean.setTime(day);

                projectsBean.setAddress(address);
                projectsBean.setResourcesType(type);
                projectsBean.setNumber(number);
                projectsBean.setOrganization(organization);
                projectsBean.setPeople(people);
                projectsBean.setEmile(email);
                if (flag == 0) {
                    banClick();
                    addProject(projectsBean);
                } else {
                    if (!TextUtils.isEmpty(Price) && !TextUtils.isEmpty(details)) {
                        int price = Integer.parseInt(Price);
                        projectsBean.setPrice(price);
                        projectsBean.setDetails(details);
                        banClick();
                        addProject(projectsBean);
                    } else {
                        Toasty.normal(this.getContext(), "请正确填写信息", Toasty.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toasty.normal(this.getContext(), "请正确填写信息", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    public void addProject(ProjectBean.DataBean.ProjectsBean projectsBean) {
        mViewModel.addProject(projectsBean).observe(this, new Observer<ProjectBean>() {
            @Override
            public void onChanged(ProjectBean projectBean) {
                allowClick();
                if (projectBean.getData() != null) {
                    if (projectBean.getCode() == 0) {
                        Toasty.success(getContext(), "提交成功", Toasty.LENGTH_SHORT).show();
                        Message message = Message.obtain();
                        EventBus.getDefault().post(message, EventBusTag.RELEASE_ADD);
                    } else {
                        Toasty.error(getContext(), "提交失败", Toasty.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.normal(getContext(), "网络繁忙", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *禁止点击
     */
    private void banClick(){
        binding.btReleaseOk.setEnabled(false);
    }

    /**
     * 允许点击
     */
    private void allowClick(){
        binding.btReleaseOk.setEnabled(true);
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
