package com.wheat7.cashew.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityWebBinding;

/**
 * Created by wheat7 on 2017/8/19.
 */

public class WebActivity extends BaseActivity<ActivityWebBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void onBackPressed() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setAct(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        getBinding().webView.getSettings().setJavaScriptEnabled(true);
        getBinding().webView.loadUrl(url);
        getBinding().webView.getSettings().setSupportZoom(true);
        getBinding().webView.getSettings().setDisplayZoomControls(false);
        getBinding().webView.getSettings().setUseWideViewPort(true);
        getBinding().webView.getSettings().setLoadWithOverviewMode(true);
        getBinding().webView.getSettings().setAppCacheEnabled(true);
        setSupportActionBar(getBinding().toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getBinding().webTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getBinding().webProgress.setProgress(newProgress);
            }
        };

        getBinding().webView.setWebChromeClient(chromeClient);
        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                getBinding().tvClose.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                getBinding().webProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getBinding().webProgress.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Back click
     */
    public void onIcBackClick() {
        onBackPressed();
    }

    /**
     * Close  click
     */

    public void onCloseClick() {
        this.finish();
    }

    /**
     * More Click
     */
    public void onIcMoreClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, getBinding().webView.getUrl() + " -来自腰果分享的干货");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享"));
    }
}
