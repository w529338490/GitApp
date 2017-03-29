package com.example.administrator.myapplication.ui.gank;

import android.app.Activity;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.entity.GankAllData;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.eventbus.GankEvent;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;
import com.example.administrator.myapplication.ui.view.GifImageView;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
    List<Famous>list =new ArrayList<>();
    String date;
    GankService service;

    LinearLayout video_ll,android_ll,ios_ll,more_ll;
    View chid;
    TextView desc;
    TextView who;

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
        video_ll= (LinearLayout) findViewById(R.id.video_ll);
        android_ll= (LinearLayout) findViewById(R.id.video_ll);
        ios_ll= (LinearLayout) findViewById(R.id.video_ll);
        more_ll= (LinearLayout) findViewById(R.id.video_ll);

        initData();
    }

    private void initData()
    {
        getAllData();
        Logger.e("initData");
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

        new Thread() {
            @Override
            public void run() {
                super.run();
                   list = JsoupUtil.getFamous("");

                if (list.size() > 0) {
                    if (GankDetailActivity.this != null) {
                        GankDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int max=list.size();
                                int min=0;
                                Random random = new Random();
                                int s = random.nextInt(max)%(max-min+1) + min;
                                collapsing.setTitle(list.get(s).getFamous());
                            }
                        });
                    }
                }

            }
        }.start();


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
    date=new SimpleDateFormat("yyyy/MM/dd").format(gankDetail.getGankDetail().getCreatedAt());


}

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void getAllData()
    {
        service = Api.getInstance().apiGank();
             Observable<GankAllData>  obAll=service.getAllData(date);

           obAll.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GankAllData>()
                {
                    @Override
                    public void call(GankAllData gankAllData)
                    {
                        addview(gankAllData);
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {
                        Logger.e(throwable.getMessage());

                    }
                });


    }

    private void addview(GankAllData gankAllData)
    {

        chid= LayoutInflater.from(this).inflate(R.layout.gankdetail_child,null,false);
        desc= (TextView) chid.findViewById(R.id.desc);
        who= (TextView) chid.findViewById(R.id.who);
       // video_ll,android_ll,ios_ll,more_ll;
        ((TextView)video_ll.getChildAt(0)).setText(gankAllData.getCategory().get(0));


          Observable<GankAllData.ResultsBean.AndroidBean> android=Observable.from(gankAllData.getResults().getAndroid());
          android.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                 .unsubscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Action1<GankAllData.ResultsBean.AndroidBean>()
                 {
                     @Override
                     public void call(GankAllData.ResultsBean.AndroidBean androidBean)
                     {

//                         desc.setText(androidBean.getDesc());
//                         who.setText(androidBean.getWho());
//                         video_ll.addView(chid);
                     }
                 });


    }
}

