package com.example.ctrading.mvvm.model.bean;

import java.io.Serializable;

/**
 * @Author: JianTours
 * @Data: 2022/4/6 22:16
 * @Description:
 */
public class UserBean implements Serializable {

    /**
     * code : 0
     * msg : 请求成功
     * data : {"phone":44,"password":"22","name":"卖家","sex":1,"address":"烦烦烦","organization":"方法"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * phone : 44
         * password : 22
         * name : 卖家
         * sex : 1
         * address : 烦烦烦
         * organization : 方法
         */

        private String phone;
        private String password;
        private String name;
        private int sex;
        private String address;
        private String organization;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }
    }
}
