package com.example.ctrading.app.network.api;


import com.example.ctrading.mvvm.model.bean.ProjectBean;
import com.example.ctrading.mvvm.model.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author: JianTours
 * @Data: 2021/12/21 19:57
 * @Description:所有网络接口
 */
public interface ApiService {
    //登录
    @GET("/user/query")
    Observable<UserBean> login(@Query("phone")String phone,@Query("password")String password);

    //添加订单
    @POST("/project/add")
    Observable<ProjectBean> addProject(@Body ProjectBean.DataBean.ProjectsBean projectsBean);
}
