package com.wheat7.cashew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.wheat7.cashew.R;
import com.wheat7.cashew.adapter.ImagePagerAdapter;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityImageDetailBinding;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.viewmodel.ImageDetailViewModel;

import java.util.List;

/**
 * Created by wheat7 on 2017/8/23.
 */

public class ImageDetailActivity extends BaseActivity<ActivityImageDetailBinding> {

    private List<Gank> mDataList;
    private ImagePagerAdapter mImagePagerAdapter;
    private int currentPos;


    @Override
    public int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setAct(this);
        getBinding().setVm(new ImageDetailViewModel());
        mDataList = (List<Gank>) getIntent().getSerializableExtra("GANKGIRL");
        mImagePagerAdapter = new ImagePagerAdapter(mDataList);
        getBinding().viewpagerImg.setAdapter(mImagePagerAdapter);
        currentPos = getIntent().getIntExtra("POS", 0);
        getBinding().viewpagerImg.setCurrentItem(currentPos);
        getBinding().getVm().total.set(mImagePagerAdapter.getCount());
        getBinding().getVm().pos.set(currentPos + 1);
        getBinding().viewpagerImg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getBinding().getVm().pos.set(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        intent.putExtra(Intent.EXTRA_TEXT, mDataList.get(currentPos).getUrl() + " -腰果给你new了一个对象");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享妹纸"));
    }
}
