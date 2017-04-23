package com.example.administrator.myapplication.net.Service;

import android.provider.MediaStore;

import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.entity.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface HttpService
{
    @GET("index?key=9e05423f7ac6acf6d0dce3425c4ea9fe")
    Call<Result> getNews(@Query("type") String type);
  //  Observable<Result>getNews(@Query("type") String type);
}
