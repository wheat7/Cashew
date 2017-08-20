package com.wheat7.cashew.http;

import com.wheat7.cashew.model.BaseRes;
import com.wheat7.cashew.model.Gank;
import com.wheat7.cashew.model.GankDaily;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wheat7 on 17/07/2017.
 */

public interface ApiService {

    @GET("data/{type}/"+20+"/{page}")
    Observable<BaseRes<List<Gank>>> getClassifyData(@Path("type") String type, @Path("page") int page);

    @GET("day/history")
    Observable<BaseRes<List<String>>> getHistoryDate();

    @GET("day/{date}")
    Observable<BaseRes<GankDaily.Results>> getDailyData(@Path("date") String date);
}
