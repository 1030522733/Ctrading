package com.example.ctrading.mvvm.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/9 10:58
 * @Description:
 */
public class ProjectBean implements Serializable {

    /**
     * code : 0
     * msg : 请求成功
     * data : {"projects":[{"projectId":"1","stauts":900,"projectType":309,"resourcesType":664,"phoneA":"1","phoneB":"755-8103-5442","address":"616 Shennan Ave, Futian District","number":956,"price":517,"organization":"jFSsFiGHKU","people":"fTiIkrBd13","emile":"2DulahWfGc","details":"UQw72plis1","time":"2019-06-25"},{"projectId":"2","stauts":252,"projectType":295,"resourcesType":994,"phoneA":"1","phoneB":"74-613-0706","address":"13 1-1 Honjocho, Yamatokoriyama","number":485,"price":847,"organization":"iyTZUVuEfe","people":"97987p0Az6","emile":"YR49ighH5b","details":"Zf835gpmXM","time":"2016-11-01"}]}
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
        private List<ProjectsBean> projects;

        public List<ProjectsBean> getProjects() {
            return projects;
        }

        public void setProjects(List<ProjectsBean> projects) {
            this.projects = projects;
        }

        public static class ProjectsBean implements Serializable {
            /**
             * projectId : 1
             * stauts : 900
             * projectType : 309
             * resourcesType : 664
             * phoneA : 1
             * phoneB : 755-8103-5442
             * address : 616 Shennan Ave, Futian District
             * number : 956
             * price : 517
             * organization : jFSsFiGHKU
             * people : fTiIkrBd13
             * emile : 2DulahWfGc
             * details : UQw72plis1
             * time : 2019-06-25
             */

            private String projectId;
            private int stauts;
            private int projectType;
            private int resourcesType;
            private String phoneA;
            private String phoneB;
            private String address;
            private int number;
            private int price;
            private String organization;
            private String people;
            private String emile;
            private String details;
            private String time;

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public int getStauts() {
                return stauts;
            }

            public void setStauts(int stauts) {
                this.stauts = stauts;
            }

            public int getProjectType() {
                return projectType;
            }

            public void setProjectType(int projectType) {
                this.projectType = projectType;
            }

            public int getResourcesType() {
                return resourcesType;
            }

            public void setResourcesType(int resourcesType) {
                this.resourcesType = resourcesType;
            }

            public String getPhoneA() {
                return phoneA;
            }

            public void setPhoneA(String phoneA) {
                this.phoneA = phoneA;
            }

            public String getPhoneB() {
                return phoneB;
            }

            public void setPhoneB(String phoneB) {
                this.phoneB = phoneB;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getOrganization() {
                return organization;
            }

            public void setOrganization(String organization) {
                this.organization = organization;
            }

            public String getPeople() {
                return people;
            }

            public void setPeople(String people) {
                this.people = people;
            }

            public String getEmile() {
                return emile;
            }

            public void setEmile(String emile) {
                this.emile = emile;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
