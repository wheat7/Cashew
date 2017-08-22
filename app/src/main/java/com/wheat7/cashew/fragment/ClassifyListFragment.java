package com.wheat7.cashew.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseFragment;
import com.wheat7.cashew.databinding.FragmentClassifyListBinding;
import com.wheat7.cashew.http.ApiException;
import com.wheat7.cashew.http.ApiService;
import com.wheat7.cashew.model.BaseRes;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.recycler.ClassifyRecyclerAdapter;
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
 * Created by wheat7 on 2017/8/5.
 */

public class ClassifyListFragment extends BaseFragment<FragmentClassifyListBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private String mType;
    private ClassifyRecyclerAdapter mAdapter;
    private int mCurrentPage;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        getBinding().swipeRefreshClassify.setColorSchemeColors(Color.parseColor("#515151"));
        mAdapter = new ClassifyRecyclerAdapter(getActivity());
        getBinding().recyclerClassify.setAdapter(mAdapter);
        Bundle bundle = getArguments();
        mType = bundle.getString("TYPE");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        getBinding().recyclerClassify.setLayoutManager(layoutManager);
        getBinding().recyclerClassify.setOnScrollListener(new LoadMoreRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mCurrentPage = current_page;
                loadMore(current_page);
            }
        });
        getBinding().swipeRefreshClassify.setOnRefreshListener(this);
        getBinding().swipeRefreshClassify.setRefreshing(true);
        onRefresh();
    }

    private void loadMore(int current_page) {
        String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getClassifyData(mType, current_page)
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
            Log.d("GANK", "on next");
            if (gankClassifies != null && gankClassifies.size() != 0) {
                mAdapter.addMoreItem(gankClassifies);
            } else {
                mAdapter.setOnNoLoadMore();
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d("GANK", e.getMessage());
            mAdapter.setNetError();
            mAdapter.setOnReloadClickListener(new ClassifyRecyclerAdapter.OnReloadClickListener() {
                @Override
                public void onClick() {
                    mAdapter.setIsLoading();
                    //loadMore(mCurrentPage);
                }
            });
        }

        @Override
        public void onComplete() {
            getBinding().swipeRefreshClassify.setRefreshing(false);
        }
    };


    public static ClassifyListFragment getClassifyListFragmentInstance(String type) {
        ClassifyListFragment classifyListFragment = new ClassifyListFragment();
        Bundle args = new Bundle();
        args.putString("TYPE", type);
        classifyListFragment.setArguments(args);
        return classifyListFragment;
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
        apiService.getClassifyData(mType, 1)
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
            getBinding().swipeRefreshClassify.setRefreshing(false);
            if (mAdapter.getRealCount() == 0 ) {
                getBinding().viewError.setVisibility(View.VISIBLE);
                getBinding().viewError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefresh();
                        getBinding().swipeRefreshClassify.setRefreshing(true);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "刷新失败，请检查网络重试", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onComplete() {
            getBinding().swipeRefreshClassify.setRefreshing(false);
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
}
