package com.wheat7.cashew.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 08/08/2017.
 */

public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private List<String> mTabs = new ArrayList<>();
    private List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();

    public BaseFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPageAdapter(FragmentManager fm, List<String> tabs, List<Fragment> fragmentList) {
        super(fm);
        mTabs = tabs;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTabs ==null ? 0 : mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  mTabs.get(position);
    }

    public void addItem(String title, Fragment fragment) {
        mFragmentList.add(fragment);
        mTabs.add(title);
    }

    public void setTabs(List<String> tabs) {
        mTabs = tabs;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        mFragmentList = fragmentList;
    }

    public void setItems(List<String> tabs, List<Fragment> fragmentList) {
        mTabs = tabs;
        mFragmentList = fragmentList;
    }




}
