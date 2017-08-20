package com.wheat7.cashew.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wheat7 on 2017/8/5.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    public View mainLayout;
    private ViewDataBinding binding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutId=getLayoutId();
        try {
            binding= DataBindingUtil.inflate(inflater,layoutId, container, false);
            mainLayout=binding.getRoot();
        }catch (NoClassDefFoundError e){
            mainLayout=inflater.inflate(layoutId, container, false);
        }
        mainLayout.setClickable(true);
        initView(savedInstanceState);
        return mainLayout;
    }

    public T getBinding(){
        return (T) binding;
    }

    public  abstract int getLayoutId();
    public  abstract void initView(Bundle savedInstanceState);

}
