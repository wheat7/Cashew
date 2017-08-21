package com.wheat7.cashew.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.WebActivity;
import com.wheat7.cashew.databinding.ItemHasImageBinding;
import com.wheat7.cashew.databinding.ItemNoImageBinding;
import com.wheat7.cashew.databinding.ViewRecyclerLoadingBinding;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 14/08/2017.
 */

public class ClassifyRecyclerAdapter extends RecyclerView.Adapter<BaseDataBindingViewHolder> {

    private List<Gank> mDataList = new ArrayList<>();
    private Context mContext;
    private static final int TYPE_ITEM_IMAGE = 1;
    private static final int TYPE_ITEM_NO_IMAGE = 2;
    private static final int TYPE_FOOTER = 3;

    public ClassifyRecyclerAdapter(Context context) {
        mContext = context;
    }

    public ClassifyRecyclerAdapter(List<Gank> data, Context context) {
        mDataList = data;
        mContext = context;
    }

    public void setData(List<Gank> data) {
        mDataList = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && mDataList.size() > 0) {
            return TYPE_FOOTER;
        } else if (mDataList != null && mDataList.size() != 0) {
            if (mDataList.get(position).getImages() != null && mDataList.get(position).getImages().size() != 0) {
                return TYPE_ITEM_IMAGE;
            }
        }
        return TYPE_ITEM_NO_IMAGE;
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
//        if (newDataList != null && newDataList.size() != 0) {
        mDataList.addAll(newDataList);
        notifyDataSetChanged();
    }
//    }

    FooterViewHolder footerViewHolder;
    @Override
    public BaseDataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return footerViewHolder = new FooterViewHolder(parent, R.layout.view_recycler_loading);
            case TYPE_ITEM_IMAGE:
                return new ItemHasImageViewHolder(parent, R.layout.item_has_image);
            case TYPE_ITEM_NO_IMAGE:
                return new ItemNoImageViewHolder(parent, R.layout.item_no_image);
        }
        return new ItemNoImageViewHolder(parent, R.layout.item_no_image);
    }

    @Override
    public void onBindViewHolder(BaseDataBindingViewHolder holder, int position) {
        if (holder instanceof ItemHasImageViewHolder) {
            if (mDataList.size() != 0) {
                ((ItemHasImageViewHolder) holder).setHolderData(mDataList.get(position), position);
            }
        }

        if (holder instanceof ItemNoImageViewHolder) {
            if (mDataList.size() != 0) {
                ((ItemNoImageViewHolder) holder).setHolderData(mDataList.get(position), position);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }

    class ItemHasImageViewHolder extends BaseDataBindingViewHolder<ItemHasImageBinding> {

        public ItemHasImageViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, final int pos) {
            if (data != null) getBinding().setData(data);
            getBinding().setShowCategory(false);
            getBinding().executePendingBindings();
            getBinding().textTime.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
            getBinding().itemGank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Click:" + pos, Toast.LENGTH_LONG).show();
                }
            });
            if (data.getImages() != null && data.getImages().size() != 0) {
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.image_loading);
                Glide.with(itemView.getContext())
                        .load(data.getImages().get(0) + "?imageView2/0/w/200")
                        .apply(requestOptions)
                        .into(getBinding().imgGank);
            }
            getBinding().itemGank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), WebActivity.class);
                    intent.putExtra("URL", data.getUrl());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    static class ItemNoImageViewHolder extends BaseDataBindingViewHolder<ItemNoImageBinding> {


        public ItemNoImageViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, final int pos) {
            getBinding().setData(data);
            getBinding().setShowCategory(false);
            getBinding().executePendingBindings();
            getBinding().textTime.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
            getBinding().itemGank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), WebActivity.class);
                    intent.putExtra("URL", data.getUrl());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    static class FooterViewHolder extends BaseDataBindingViewHolder<ViewRecyclerLoadingBinding> {

        public FooterViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
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
