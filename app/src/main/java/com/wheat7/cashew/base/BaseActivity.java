package com.wheat7.cashew.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.wheat7.cashew.utils.swipebackhelper.SwipeBackHelper;


/**
 * Created by wheat7 on 05/07/2017.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private View mainView;
    private ViewDataBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300)
                .setSwipeEdgePercent(0.1f);
        try {
            binding = DataBindingUtil.setContentView(this, layoutId);
            if (binding != null) {
                mainView = binding.getRoot();
            } else {
                mainView = LayoutInflater.from(this).inflate(layoutId, null);
                setContentView(mainView);
            }

        } catch (NoClassDefFoundError e) {
            mainView = LayoutInflater.from(this).inflate(layoutId, null);
            setContentView(mainView);
        }
        initView(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    public T getBinding() {
        return (T) binding;
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

}
