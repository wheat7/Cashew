package com.wheat7.cashew.activity;

import android.content.Intent;
import android.os.Bundle;

import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityAboutBinding;

/**
 * Created by wheat7 on 2017/8/20.
 */

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setAct(this);
    }

    /**
     * Back click
     */
    public void onIcBackClick() {
        this.finish();
    }

    /**
     * More Click
     */
    public void onShareClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享腰果");
        intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/wheat7/Cashew");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享腰果"));
    }
}
