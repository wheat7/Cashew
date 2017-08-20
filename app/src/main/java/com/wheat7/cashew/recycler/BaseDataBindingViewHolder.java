package com.wheat7.cashew.recycler;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wheat7 on 14/08/2017.
 */

public abstract class BaseDataBindingViewHolder<T extends ViewDataBinding>  extends RecyclerView.ViewHolder {

    private ViewDataBinding viewDataBinding;

    public BaseDataBindingViewHolder(View itemView) {
        super(itemView);
    }

    public BaseDataBindingViewHolder(ViewGroup itemView) {
        super(itemView);
        this.viewDataBinding = DataBindingUtil.bind(this.itemView);
    }
    public BaseDataBindingViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
        this.viewDataBinding=DataBindingUtil.bind(itemView);
    }

    public T getBinding() {
        return (T) this.viewDataBinding;
    }

}
