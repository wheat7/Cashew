package com.wheat7.cashew.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseFragment;
import com.wheat7.cashew.databinding.FragmentDailyListBinding;
import com.wheat7.cashew.http.ApiException;
import com.wheat7.cashew.http.ApiService;
import com.wheat7.cashew.model.BaseRes;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.model.GankDaily;
import com.wheat7.cashew.recycler.DailyRecyclerAdapter;

import java.util.ArrayList;
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
 * Created by wheat7 on 16/08/2017.
 */

public class DailyListFragment extends BaseFragment<FragmentDailyListBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private String mDate;
    private DailyRecyclerAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().swipeRefreshDaily.setColorSchemeColors(Color.parseColor("#515151"));
        Bundle bundle = getArguments();
        mDate = bundle.getString("DATE");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        getBinding().recyclerDaily.setLayoutManager(layoutManager);
        mAdapter = new DailyRecyclerAdapter();
        getBinding().recyclerDaily.setAdapter(mAdapter);
        getBinding().swipeRefreshDaily.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getBinding().swipeRefreshDaily.setRefreshing(true);
        String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getDailyData(mDate)
                .map(new BaseResFunc<GankDaily.Results>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<GankDaily.Results> mObserver = new Observer<GankDaily.Results>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull GankDaily.Results results) {
            if (results != null) {
                mAdapter.setGankList(addAllResult(results));
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Toast.makeText(getActivity(), "刷新失败，请检查网络重试", Toast.LENGTH_LONG).show();
            getBinding().swipeRefreshDaily.setRefreshing(false);
        }

        @Override
        public void onComplete() {
            getBinding().swipeRefreshDaily.setRefreshing(false);
        }
    };

    private List<Gank> addAllResult(GankDaily.Results results) {
        List<Gank> gankList = new ArrayList<>();
        if (results.androidList != null) gankList.addAll(results.androidList);
        if (results.iOSList != null) gankList.addAll(results.iOSList);
        if (results.前端List != null) gankList.addAll(results.前端List);
        if (results.appList != null) gankList.addAll(results.appList);
        if (results.拓展资源List != null) gankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) gankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) gankList.addAll(results.休息视频List);
        return gankList;
    }

    public static DailyListFragment getDailyListFragmentInstance(String date) {
        DailyListFragment fragment = new DailyListFragment();
        Bundle args = new Bundle();
        args.putString("DATE", date);
        fragment.setArguments(args);
        return fragment;
    }

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
