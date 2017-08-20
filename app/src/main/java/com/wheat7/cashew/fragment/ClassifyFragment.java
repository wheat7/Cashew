package com.wheat7.cashew.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.InfoActivity;
import com.wheat7.cashew.adapter.BaseFragmentPageAdapter;
import com.wheat7.cashew.base.BaseFragment;
import com.wheat7.cashew.databinding.FragmentClassifyBinding;

/**
 * Created by wheat7 on 2017/8/5.
 */

public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding>{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setFrag(this);
        BaseFragmentPageAdapter baseFragmentPageAdapter = new BaseFragmentPageAdapter(getActivity().getSupportFragmentManager());
        String[] items = new String[]{"Android", "iOS", "前端", "App", "拓展资源", "瞎推荐", "休息视频"};
        for (int i = 0; i < items.length; i++) {
            baseFragmentPageAdapter.addItem(items[i], ClassifyListFragment.getClassifyListFragmentInstance(items[i]));
        }
        getBinding().viewpagerClassify.setOffscreenPageLimit(items.length);
        getBinding().viewpagerClassify.setAdapter(baseFragmentPageAdapter);
        getBinding().tabLayoutClassify.setupWithViewPager(getBinding().viewpagerClassify);
    }

    /**
     * About click
     */
    public void onAboutClick() {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        startActivity(intent);
    }

}
