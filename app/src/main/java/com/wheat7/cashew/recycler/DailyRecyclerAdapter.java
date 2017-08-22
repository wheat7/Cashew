package com.wheat7.cashew.recycler;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.WebActivity;
import com.wheat7.cashew.databinding.ItemHasImageBinding;
import com.wheat7.cashew.databinding.ItemNoImageBinding;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 17/08/2017.
 */

public class DailyRecyclerAdapter extends RecyclerView.Adapter<BaseDataBindingViewHolder> {

    private List<Gank> mDataList = new ArrayList<>();
    private static final int TYPE_ITEM_IMAGE = 1;
    private static final int TYPE_ITEM_NO_IMAGE = 2;

    @Override
    public int getItemViewType(int position) {
        if (mDataList != null && mDataList.size() != 0) {
            if (mDataList.get(position).getImages() != null && mDataList.get(position).getImages().size() != 0) {
                return TYPE_ITEM_IMAGE;
            }
        }
        return TYPE_ITEM_NO_IMAGE;
    }

    @Override
    public BaseDataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_IMAGE:
                return new ItemHasImageViewHolder(parent, R.layout.item_has_image);
            case TYPE_ITEM_NO_IMAGE:
                return new ItemNoImageViewHolder(parent, R.layout.item_no_image);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseDataBindingViewHolder holder, int position) {
        boolean showCategory;
        if (position == 0) {
            showCategory = true;
        } else {
            if (mDataList.get(position).getType().equals(mDataList.get(position - 1).getType())) {
                showCategory = false;
            } else {
                showCategory = true;
            }
        }
        if (holder instanceof ItemHasImageViewHolder) {
            ((ItemHasImageViewHolder) holder).setHolderData(mDataList.get(position), showCategory, position);
        }
        if (holder instanceof ItemNoImageViewHolder) {
            ((ItemNoImageViewHolder) holder).setHolderData(mDataList.get(position), showCategory, position);
        }
    }

    public void setGankList(List<Gank> gankList) {
        mDataList = gankList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ItemHasImageViewHolder extends BaseDataBindingViewHolder<ItemHasImageBinding> {

        public ItemHasImageViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, boolean showCategory, final int pos) {
            getBinding().setData(data);
            getBinding().setShowCategory(showCategory);
            getBinding().executePendingBindings();
            getBinding().textTime.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
            if (data.getImages() != null && data.getImages().size() != 0) {
                Glide.with(itemView.getContext())
                        .load(data.getImages().get(0) + "?imageView2/0/w/200")
                        .placeholder(R.drawable.image_loading)
                        .crossFade()
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

    class ItemNoImageViewHolder extends BaseDataBindingViewHolder<ItemNoImageBinding> {

        public ItemNoImageViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        public void setHolderData(final Gank data, boolean showCategory, final int pos) {
            getBinding().setData(data);
            getBinding().setShowCategory(showCategory);
            getBinding().textTime.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
            getBinding().executePendingBindings();
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
}
