package com.example.administrator.myapplication.ui.gank;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.GankAdapter;
import com.example.administrator.myapplication.entity.DayGankResult;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GankActivity extends AppCompatActivity
{

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    GridLayoutManager parent;
    GankService service;
    GankAdapter adapter;
    int page_num=20;
    List<RandomData.Gank> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        recyview= (RecyclerView) findViewById(R.id.recyview);
        fresh= (SwipeRefreshLayout) findViewById(R.id.fresh);
        parent= new GridLayoutManager(this,2);
        initData();
    }

    private void initData()
    {
        //使用RxAndroid
        service= Api.getInstance().apiGank();
        //通过service获得 Observable对象,完成 异步加载数据
        Observable<RandomData> obs=  service.getRandomData("福利",page_num);

         obs.subscribeOn(Schedulers.io())//指定获取数据在io子线程
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())//处理结果回调 在UI 主线程
                  //subscribe  为返回回调
            .subscribe(new Subscriber<RandomData>()
                {
                    @Override
                    public void onCompleted()
                    {
                        Log.e("onCompleted","=====================");
                      //  请求完成回调
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        //请求 错误回调
                        Log.e("error","================="+e.getMessage());

                    }
                    @Override
                    public void onNext(RandomData randomData)
                    {
                        Log.e("randomData","================="+randomData.isError());
                        //在主线程 更新UI
                        UpDataUi(randomData);
                    }
                });
    }

    //更新 UI炒作
    public void UpDataUi(RandomData randomData)
    {
        if(randomData.isError()==false&&randomData.getResults()!=null)
        {
            list=randomData.getResults();
        }

        adapter=new GankAdapter(GankActivity.this,list);
        recyview.setLayoutManager(parent);
        recyview.setAdapter(adapter);

    }

}
