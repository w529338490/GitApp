package com.duanzi.ui.comm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.duanzi.Utill.JsoupUtil;
import com.duanzi.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initWidght();
    }


    private void initWidght()
    {
        Observable<Integer> observable = Observable.create(new rx.Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {
                JsoupUtil.getStoryText("http://book.zongheng.com/chapter/661757/36580613.html");
                subscriber.onNext(1);
            }
        });
        observable.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//处理结果回调 在UI 主线程
                .subscribe(new Action1<Integer>()
                {
                    @Override
                    public void call(Integer integer)
                    {
                    }
                });

    }
}
