package com.example.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    WebViewActivity
 * 作者：      钟士宜
 * 创建时间    2019/7/11 11:12
 * 描述：      TODO
 */
public class WebViewActivity extends BaseActivity{

    //进度
    private ProgressBar mProgessBar;
    //网页
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    //初始化view
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {

        mProgessBar = findViewById(R.id.mProgressBar);
        mWebView = findViewById(R.id.mWebView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        L.i("url：" + url);

        //设置标题
        getSupportActionBar().setTitle(title);

        //进行加载网页的逻辑

        //支持JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);

        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);

                //说明我接受这个事件
                return true;
            }
        });
    }

    public class WebViewClient extends WebChromeClient{

        //进度变化的监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress == 100){
                mProgessBar.setVisibility(View.GONE);
            }
        }
    }
}
