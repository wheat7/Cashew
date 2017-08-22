package com.wheat7.cashew.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.InfoActivity;
import com.wheat7.cashew.base.BaseFragment;
import com.wheat7.cashew.databinding.FragmentGirlBinding;
import com.wheat7.cashew.http.ApiException;
import com.wheat7.cashew.http.ApiService;
import com.wheat7.cashew.model.BaseRes;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.recycler.GirlRecyclerAdapter;
import com.wheat7.cashew.recycler.LoadMoreRecyclerOnScrollListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wheat7 on 2017/8/22.
 */

public class GirlFragment extends BaseFragment<FragmentGirlBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private GirlRecyclerAdapter mAdapter;
    private int mCurrentPage;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setFrag(this);
        getBinding().swipeRefreshGirl.setColorSchemeColors(Color.parseColor("#515151"));
        mAdapter = new GirlRecyclerAdapter(getActivity());
        getBinding().recyclerGirl.setAdapter(mAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        getBinding().recyclerGirl.setLayoutManager(staggeredGridLayoutManager);
        getBinding().recyclerGirl.setOnScrollListener(new LoadMoreRecyclerOnScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mCurrentPage = current_page;
                loadMore(current_page);
            }
        });
        getBinding().swipeRefreshGirl.setOnRefreshListener(this);
        getBinding().swipeRefreshGirl.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getClassifyData("福利", 1)
                .map(new BaseResFunc<List<Gank>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }

    Observer<List<Gank>> mObserver = new Observer<List<Gank>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull List<Gank> gankClassifies) {
            Log.d("GANK", "on next");
            if (gankClassifies != null && gankClassifies.size() != 0) {
                mAdapter.setDataList(gankClassifies);
            } else {
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d("GANK", e.getMessage());
            getBinding().swipeRefreshGirl.setRefreshing(false);
            if (mAdapter.getRealCount() == 0) {
                getBinding().viewError.setVisibility(View.VISIBLE);
                getBinding().viewError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefresh();
                        getBinding().swipeRefreshGirl.setRefreshing(true);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "刷新失败，请检查网络重试", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onComplete() {
            getBinding().swipeRefreshGirl.setRefreshing(false);
            getBinding().viewError.setVisibility(View.GONE);
        }
    };


    private class BaseResFunc<T> implements io.reactivex.functions.Function<BaseRes<T>, T> {

        @Override
        public T apply(@NonNull BaseRes<T> tBaseRes) throws Exception {
            if (tBaseRes.isError() == true) {
                throw new ApiException("请求错误！");
            }
            return tBaseRes.getResults();
        }
    }

    public void loadMore(int currentPage) {
        String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getClassifyData("福利", currentPage)
                .map(new BaseResFunc<List<Gank>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLoadMoreObserver);
    }

    Observer<List<Gank>> mLoadMoreObserver = new Observer<List<Gank>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull List<Gank> gankClassifies) {
            if (gankClassifies != null && gankClassifies.size() != 0) {
                mAdapter.addMoreItem(gankClassifies);
            } else {
                mAdapter.setOnNoLoadMore();
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            mAdapter.setNetError();
            mAdapter.setOnReloadClickListener(new GirlRecyclerAdapter.OnReloadClickListener() {
                @Override
                public void onClick() {
                    mAdapter.setIsLoading();
                    loadMore(mCurrentPage);
                }
            });
        }

        @Override
        public void onComplete() {
            getBinding().swipeRefreshGirl.setRefreshing(false);
        }
    };

    /**
     * About click
     */
    public void onGirlInfoClick() {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        startActivity(intent);
    }
}
