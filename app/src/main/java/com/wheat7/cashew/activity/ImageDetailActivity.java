package com.wheat7.cashew.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityImageDetailBinding;
import com.wheat7.cashew.model.Gank;

/**
 * Created by wheat7 on 2017/8/23.
 */

public class ImageDetailActivity extends BaseActivity<ActivityImageDetailBinding> {

    private Gank GankGirl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setAct(this);
        GankGirl = (Gank) getIntent().getSerializableExtra("GANKGIRL");
        if (GankGirl.getUrl() != null) {
            Glide.with(this).load(GankGirl.getUrl()).into(getBinding().imgDetail);
        }
    }

    /**
     * Back click
     */
    public void onIcBackClick() {
        finish();
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
        intent.putExtra(Intent.EXTRA_TEXT, GankGirl.getUrl() + " -腰果给你new了一个对象");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享妹纸"));
    }
}
