package com.example.administrator.myapplication.net.Service;

import android.provider.MediaStore;

import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.entity.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface HttpService {
    @GET("index?key=9e05423f7ac6acf6d0dce3425c4ea9fe")
    Call<Result> Get_news(@Query("type") String type);

    @GET("neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1")
    Call<Video> Get_video();
}
