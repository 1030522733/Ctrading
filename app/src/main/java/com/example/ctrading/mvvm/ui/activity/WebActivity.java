package com.example.ctrading.mvvm.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ctrading.R;
import com.example.ctrading.app.base.BaseAct;
import com.example.ctrading.databinding.ActivityWebBinding;
import com.example.ctrading.mvvm.ui.parts.SampleTextVIew;
import com.example.ctrading.mvvm.viewmodel.MainViewModel;

/**
 * @Author: JianTours
 * @Data: 2022/4/26 22:01
 * @Description:
 */
public class WebActivity extends BaseAct<MainViewModel, ActivityWebBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        ConstraintLayout toolbar = (ConstraintLayout) binding.layoutWeb;
        ImageView ivBack = (ImageView) toolbar.getViewById(R.id.ivWebBack);
        SampleTextVIew tvWeb = (SampleTextVIew) toolbar.getViewById(R.id.tvWebTitle);
        tvWeb.setText(title);
        ivBack.setOnClickListener(view -> finish());

        //解决加载空白屏
        WebSettings webSettings = binding.web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //解决加载出现ERR_UNKNOWN_URL_SCHEME
        binding.web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        //加载网页
        binding.web.loadUrl(url);

        //进度条
        binding.web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void runFlow() {

    }
}
