package com.example.administrator.myapplication.net;

import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.net.Service.GankService;
import com.example.administrator.myapplication.net.Service.HttpService;
import com.orhanobut.logger.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/21.
 */

public class Api
{
    public static String uri_news = "http://v.juhe.cn/toutiao/";
    private static Api instances;

    //来自gank.io提供的免费APi
    public static String httpBaseUrl = "http://gank.io/api/";

    //搜索的关键词
    public static String android_ = "Android";
    public static String ios = "iOS";
    public static String video = "休息视频";
    public static String welfare = "福利";
    public static String extendSource = "拓展资源";
    public static String front = "前端";
    public static String recommand = "瞎推荐";
    public static String app = "App";
    private static OkHttpClient.Builder builder;

    //retrofit2初始化GsonConverterFactory，转化Json 数据
    private Converter.Factory factory = GsonConverterFactory.create();
    private HttpService service;
    private GankService gankService;



    public static Api getInstance()
    {
        builder = new OkHttpClient.Builder();
          //打印请求
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
//                Logger.json(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        builder.connectTimeout(5, TimeUnit.SECONDS);


        if (instances == null)
        {
            instances = new Api();


        }

        return instances;
    }

    public HttpService Api_News(  )
    {

        Retrofit retrofit =
                new Retrofit.Builder()
                         .client(builder.build())
                        .baseUrl(uri_news)
                        .addConverterFactory(factory)
                        .build();

        service = retrofit.create(HttpService.class);
        return service;

    }
    public GankService apiGank( )
    {

        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(builder.build())
                        .baseUrl(httpBaseUrl)
                        .addConverterFactory(factory)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //使用Rxjava+retrofit必须有这个
                        .build();

        gankService = retrofit.create(GankService.class);
        return gankService;
    }

}
