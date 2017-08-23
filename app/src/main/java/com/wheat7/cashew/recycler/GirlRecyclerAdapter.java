package com.wheat7.cashew.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.ImageDetailActivity;
import com.wheat7.cashew.databinding.ItemGirlBinding;
import com.wheat7.cashew.databinding.ViewRecyclerLoadingBinding;
import com.wheat7.cashew.model.Gank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 2017/8/22.
 */

public class GirlRecyclerAdapter extends RecyclerView.Adapter<BaseDataBindingViewHolder> {

    private List<Gank> mDataList = new ArrayList<>();
    private final static int TYPE_ITEM = 1;
    private final static int TYPE_FOOTER = 2;
    private  Context mContext;

    public GirlRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && mDataList.size() > 0) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    FooterViewHolder footerViewHolder;

    @Override
    public BaseDataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return footerViewHolder = new FooterViewHolder(parent, R.layout.view_recycler_loading);
        } else if (viewType == TYPE_ITEM) {
            return new ImageItemViewHolder(parent, R.layout.item_girl);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseDataBindingViewHolder holder, int position) {
        if (holder instanceof ImageItemViewHolder) {
            if (mDataList.size() != 0) {
                ((ImageItemViewHolder) holder).setHolderData(mDataList.get(position), position);
                ((ImageItemViewHolder) holder).setHolderDataList(mDataList, position);
            }
        }
    }

    public void setDataList(List<Gank> dataList) {
        if (dataList != null && dataList.size() != 0) {
            this.mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    public int getRealCount() {
        return mDataList.size();
    }

    public void addMoreItem(List<Gank> newDataList) {
        if (newDataList != null && newDataList.size() != 0) {
            mDataList.addAll(newDataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }

    static class ImageItemViewHolder extends BaseDataBindingViewHolder<ItemGirlBinding> {

        public ImageItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, final int pos) {
            if (data.getUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(data.getUrl() + "?imageView2/0/w/200").crossFade()
                        .into(getBinding().imgGirl);
            }
            getBinding().executePendingBindings();
        }

        public void setHolderDataList(final List<Gank> dataList, final int pos) {
            getBinding().itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ImageDetailActivity.class);
                    intent.putExtra("GANKGIRL", (Serializable) dataList);
                    intent.putExtra("POS", pos);
                    getContext().startActivity(intent);
                }
            });
        }

    }

    static class FooterViewHolder extends BaseDataBindingViewHolder<ViewRecyclerLoadingBinding> {

        public FooterViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, final int pos) {
        }
    }

    public void setIsLoading() {
        footerViewHolder.getBinding().textLoading.setText("正在加载更多");
        footerViewHolder.getBinding().progressLoading.setVisibility(View.VISIBLE);
    }

    public void setOnNoLoadMore() {
        footerViewHolder.getBinding().textLoading.setText("没有更多了");
        footerViewHolder.getBinding().progressLoading.setVisibility(View.GONE);
    }

    public void setNetError() {
        footerViewHolder.getBinding().textLoading.setText("加载失败，点击重试");
        footerViewHolder.getBinding().viewLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReloadClickListener != null) mOnReloadClickListener.onClick();
            }
        });
        footerViewHolder.getBinding().progressLoading.setVisibility(View.GONE);
    }

    //网络问题重新加载时点击回调
    private OnReloadClickListener mOnReloadClickListener;

    public interface OnReloadClickListener {
        void onClick();
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener;
    }

}
