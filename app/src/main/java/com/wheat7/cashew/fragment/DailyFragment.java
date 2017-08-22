package com.wheat7.cashew.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.wheat7.cashew.R;
import com.wheat7.cashew.activity.InfoActivity;
import com.wheat7.cashew.adapter.BaseFragmentPageAdapter;
import com.wheat7.cashew.base.BaseFragment;
import com.wheat7.cashew.databinding.FragmentDailyBinding;
import com.wheat7.cashew.http.ApiException;
import com.wheat7.cashew.http.ApiService;
import com.wheat7.cashew.model.BaseRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class DailyFragment extends BaseFragment<FragmentDailyBinding> {

    private ProgressDialog mProgressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("正在加载...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        getBinding().setFrag(this);
        getHistoryDate();
    }


    private void getHistoryDate() {
        mProgressDialog.show();
        String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getHistoryDate()
                .map(new BaseResFunc<List<String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }


    Observer<List<String>> mObserver = new Observer<List<String>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull List<String> strings) {
            BaseFragmentPageAdapter fragmentPageAdapter = new BaseFragmentPageAdapter(getActivity().getSupportFragmentManager());
            List<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                String date = dateFormat(strings.get(i));
                fragments.add(DailyListFragment.getDailyListFragmentInstance(date));
            }
            fragmentPageAdapter.setItems(strings.subList(0, 15), fragments);
            getBinding().viewpagerDay.setOffscreenPageLimit(5);
            getBinding().viewpagerDay.setAdapter(fragmentPageAdapter);
            getBinding().tabLayoutDay.setupWithViewPager(getBinding().viewpagerDay);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Toast.makeText(getActivity(), "请求失败，请检查网络后重试", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
            getBinding().viewError.setVisibility(View.VISIBLE);
            getBinding().viewError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgressDialog.show();
                    getHistoryDate();
                }
            });
        }

        @Override
        public void onComplete() {
            mProgressDialog.dismiss();
            getBinding().viewError.setVisibility(View.GONE);
        }
    };

    /**
     * 格式化日期，将获取到的yyyy-MM-dd转为yyyy/MM/dd
     * @param dateStr
     * @return
     */
    private String dateFormat(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String newDate = simpleDateFormat.format(date);
        return newDate;
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

    /**
     * Info click
     */
    public void onInfoClick() {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        startActivity(intent);
    }

}
