package com.example.administrator.myapplication.net;

import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.net.Service.HttpService;

import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/21.
 */

public class Api {
    public static String uri_news = "http://v.juhe.cn/toutiao/";
    private  static Api instances;
    private static String type;

    private Converter.Factory factory = GsonConverterFactory.create();
    private  HttpService service;

    public  static Api  getInstance()
    {
        if(instances==null){
            instances=new Api();
        }
        return instances;
    }
    public HttpService Api_News() {
        Retrofit retrofit=
                 new Retrofit.Builder()
                .baseUrl(uri_news)
                .addConverterFactory(factory)
                .build();

        service=retrofit.create(HttpService.class);
        return service;

    }

}
