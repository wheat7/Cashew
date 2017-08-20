package com.wheat7.cashew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityInfoBinding;
import com.wheat7.cashew.utils.GlideCacheUtil;
import com.wheat7.cashew.viewmodel.InfoViewModel;

/**
 * Created by wheat7 on 2017/8/19.
 */

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setAct(this);
        getBinding().setVm(new InfoViewModel());
        getBinding().getVm().mImageCacheSize = GlideCacheUtil.getInstance().getCacheSize(this);
        getBinding().getVm().notifyPropertyChanged(BR.imageCacheSize);
    }

    /**
     * Back click
     */
    public void onIcBackClick() {
        this.finish();
    }

    /**
     * Feedback click
     */
    public void onFeedBackClick() {
        Intent intent  = new Intent(this, WebActivity.class);
        intent.putExtra("URL","https://github.com/wheat7/VRPlayer/issues");
        startActivity(intent);
    }

    /**
     * Feedback click
     */
    public void onCheckUpdateClick() {
        Intent intent  = new Intent(this, WebActivity.class);
        intent.putExtra("URL","https://github.com/wheat7/VRPlayer");
        startActivity(intent);
    }

    /**
     * Clear cache click
     */
    public void onClearCacheClick() {
        GlideCacheUtil.getInstance().clearImageAllCache(this);
        Toast.makeText(this, "清除了" + getBinding().getVm().mImageCacheSize + "的缓存", Toast.LENGTH_LONG).show();
        getBinding().getVm().mImageCacheSize = GlideCacheUtil.getInstance().getCacheSize(this);
        getBinding().getVm().notifyPropertyChanged(BR.imageCacheSize);
    }

    public void onAboutClick() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
