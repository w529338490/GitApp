package com.duanzi.net.Service;

import com.duanzi.entity.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface HttpService
{
    @GET("index?key=9e05423f7ac6acf6d0dce3425c4ea9fe")
    Call<Result> getNews(@Query("type") String type);
  //  Observable<Result>getNews(@Query("type") String type);
}
