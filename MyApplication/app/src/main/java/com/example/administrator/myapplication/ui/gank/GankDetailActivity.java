package com.example.administrator.myapplication.ui.gank;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.eventbus.GankEvent;
import com.example.administrator.myapplication.ui.view.GifImageView;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *  Gank细节页面
 *  //布局页面 使用CoordinatorLayout
 *  参考 http://blog.csdn.net/xyz_lmn/article/details/48055919
 *
 */
public class GankDetailActivity extends Activity
{
    GankEvent gankDetail;
    GifImageView tittle_img;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);

        setContentView(R.layout.activity_gank_detail);
        initView();

    }

    private void initView()
    {
        tittle_img= (GifImageView) findViewById(R.id.tittle_img);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        collapsing= (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        initData();
    }

    private void initData()
    {

        Glide.with(GankDetailActivity.this)
                .load(gankDetail.getGankDetail().getUrl())
                .transform(new GlideRoundTransform(this,20))
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .crossFade(1500)
                .into(tittle_img);
        /**
         * 获取每日名言
         *
         */

        Observable<Famous> famous=Observable.create(new Observable.OnSubscribe<Famous>()
        {
            @Override
            public void call(Subscriber<? super Famous> subscriber)
            {
                List<Famous>list=JsoupUtil.getFamous("");
                Logger.e(list.size()+"");
               for(int i=0;i<list.size();i++)
               {
                   if(list.get(i).getFamous().length()>=13)
                   {
                       list.remove(i);
                   }
               }
                int  r=new Random(10).nextInt(list.size());
                Logger.e(r+"");
                subscriber.onNext(list.get(r));
            }
        });
        famous.subscribeOn(Schedulers.io())//指定获取数据在io子线程
              .unsubscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread()) //处理结果回调 在UI 主线程
              .subscribe(new Action1<Famous>()
              {
                  @Override
                  public void call(Famous famous)
                  {
                      Logger.e(famous.getFamous());
                      collapsing.setTitle(famous.getFamous()+"");
                   //   collapsing.setTitle("this is asdasd dfsdfdsfgdfghfhgfhgfhgfhgf");
                  }
              }, new Action1<Throwable>()
              {
                  @Override
                  public void call(Throwable throwable)
                  {
                      Logger.e(throwable.getMessage());
                      collapsing.setTitle("");
                  }
              });

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();

            }
        });




    }


    public void onEventMainThread (GankEvent event)
{
    gankDetail=event;


}

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

