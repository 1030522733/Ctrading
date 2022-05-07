package com.example.ctrading.mvvm.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.ctrading.R;
import com.example.ctrading.app.utils.ParseUtils;
import com.example.ctrading.mvvm.model.bean.ArticleBean;

import java.util.List;

/**
 * @Author: JianTours
 * @Data: 2022/4/25 22:35
 * @Description:
 */
public class RvArticleAdapter extends BaseQuickAdapter<ArticleBean.DataBean.Bean, BaseViewHolder> {


    public RvArticleAdapter(List<ArticleBean.DataBean.Bean> data) {
        super(R.layout.rv_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean.DataBean.Bean item) {
        if (item.getTop()==0){
            helper.setGone(R.id.tvRvArticleTop,true);
        }else {
            helper.setGone(R.id.tvRvArticleTop,false);
        }
        if (item.equals(getData().get(getData().size()-1))){
            helper.setGone(R.id.vvRvArticleMore,false);
        }else {
            helper.setGone(R.id.vvRvArticleMore,true);
        }
        helper.setText(R.id.tvRvArticleType, ParseUtils.getArticleType(item.getType()));
        helper.setText(R.id.tvRvArticleTitle,item.getTitle().toString());
    }
}
