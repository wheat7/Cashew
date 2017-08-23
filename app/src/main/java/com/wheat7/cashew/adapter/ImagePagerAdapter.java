package com.wheat7.cashew.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wheat7.cashew.model.Gank;

import java.util.List;

/**
 * Created by wheat7 on 23/08/2017.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private List<Gank> mDataList;

    public ImagePagerAdapter(List<Gank> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(container.getContext()).load(mDataList.get(position).getUrl()).into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}


