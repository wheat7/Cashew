package com.wheat7.cashew.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.wheat7.cashew.R;
import com.wheat7.cashew.adapter.NoTouchNoAnimPagerAdapter;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityMainBinding;
import com.wheat7.cashew.fragment.ClassifyFragment;
import com.wheat7.cashew.fragment.DailyFragment;
import com.wheat7.cashew.fragment.GirlFragment;
import com.wheat7.cashew.utils.swipebackhelper.SwipeBackHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
        getBinding().bnve.enableAnimation(false);
        getBinding().bnve.enableShiftingMode(false);
        getBinding().bnve.enableItemShiftingMode(false);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DailyFragment());
        fragments.add(new ClassifyFragment());
        fragments.add(new GirlFragment());
        getBinding().viewpagerMain.setOffscreenPageLimit(fragments.size());
        NoTouchNoAnimPagerAdapter noTouchNoAnimPagerAdapter = new NoTouchNoAnimPagerAdapter(getSupportFragmentManager(), fragments);
        getBinding().viewpagerMain.setAdapter(noTouchNoAnimPagerAdapter);
        setFrag(R.id.b_i_day);
        getBinding().bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setFrag(item.getItemId());
                return true;
            }
        });
    }

    private void setFrag(int itemId) {
        switch (itemId) {
            case R.id.b_i_day:
                getBinding().viewpagerMain.setCurrentItem(0);
                break;
            case R.id.b_i_classify:
                getBinding().viewpagerMain.setCurrentItem(1);
                break;
            case R.id.b_i_girl:
                getBinding().viewpagerMain.setCurrentItem(2);
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
