package com.example.ctrading.app.utils;

/**
 * @Author: JianTours
 * @Data: 2022/4/9 14:25
 * @Description:
 */
public class ParseUtils {

    /**
     * 分辨资源类别
     */
    public static int getType(String type) {
        if (type.equals("树林")) {
            return 0;
        } else if (type.equals("风能")) {
            return 1;
        } else if (type.equals("太阳能")) {
            return 2;
        } else if (type.equals("水电")) {
            return 3;
        } else if (type.equals("生物质发电")) {
            return 4;
        } else if (type.equals("沼气发电")) {
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * 返回资源字符串
     */
    public static String getType(int type) {
        if (type == 0) {
            return "树林";
        } else if (type == 1) {
            return "风能";
        } else if (type == 2) {
            return "太阳能";
        } else if (type == 3) {
            return "水电";
        } else if (type == 4) {
            return "生物质发电";
        } else if (type == 5) {
            return "沼气发电";
        } else {
            return "其他";
        }
    }

    /**
     * 返回文章类型
     */
    public static String getArticleType(int type) {
        if (type == 0) {
            return "热点速递";
        } else if (type == 1) {
            return "地方政策";
        } else if (type == 2) {
            return "减排技术";
        }else if (type==3){
            return "科普";
        } else {
            return "其他";
        }
    }
}
