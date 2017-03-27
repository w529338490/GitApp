package com.example.administrator.myapplication.net.Service;

import com.example.administrator.myapplication.entity.DayGankResult;
import com.example.administrator.myapplication.entity.GankAllData;
import com.example.administrator.myapplication.entity.RandomData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/21.
 * 来自Gank的APi
 *   使用ＲｘＡｎｄｒｏｉｄ
 */


public  interface GankService
{
    /**
     * 获取Gank的历史日期
     * @return
     *   http://gank.io/api/day/history
     */

    @GET("day/history")
    Observable<DayGankResult>getHistory();

    /**
     *
     * @param type 类型 android ios 福利
     *
     * @return
     */
    //http://gank.io/api/random/data/Android/20
    @GET("random/data/{type}/{page_num}")
    Observable<RandomData>getRandomData(@Path("type")String type,@Path("page_num")Integer page_num);

    @GET("day/{date}")
    Observable<GankAllData>getAllData(@Path("date")String date);


}
