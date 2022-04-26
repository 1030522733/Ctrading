package com.example.ctrading.mvvm.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/25 22:35
 * @Description:
 */
public class ArticleBean implements Serializable {

    /**
     * code : 0
     * msg : 请求成功
     * data : {"article":[{"articleId":1,"top":0,"type":119,"title":"Prof.","url":"https://image.zix1.jp/MusicalInstrument"},{"articleId":3,"top":0,"type":743,"title":"Mr.","url":"http://video.hnakajima.org/ClothingShoesandJewelry"},{"articleId":2,"top":1,"type":969,"title":"Mr.","url":"https://image.yunxiguo.jp/ToysGames"},{"articleId":4,"top":1,"type":628,"title":"Prof.","url":"https://auth.zhennanliang.jp/CollectiblesFineArt"},{"articleId":5,"top":1,"type":676,"title":"Miss.","url":"http://video.murakaito8.jp/BaggageTravelEquipment"}]}
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
        private List<Bean> article;

        public List<Bean> getArticle() {
            return article;
        }

        public void setArticle(List<Bean> article) {
            this.article = article;
        }

        public static class Bean implements Serializable {
            /**
             * articleId : 1
             * top : 0
             * type : 119
             * title : Prof.
             * url : https://image.zix1.jp/MusicalInstrument
             */

            private int articleId;
            private int top;
            private int type;
            private String title;
            private String url;

            public int getArticleId() {
                return articleId;
            }

            public void setArticleId(int articleId) {
                this.articleId = articleId;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
