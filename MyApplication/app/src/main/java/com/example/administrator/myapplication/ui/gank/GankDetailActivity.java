package com.example.administrator.myapplication.ui.gank;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.adapter.GankDetailAdapter;
import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.entity.Gank;
import com.example.administrator.myapplication.entity.GankAllData;
import com.example.administrator.myapplication.eventbus.GankEvent;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;
import com.example.administrator.myapplication.ui.view.GifImageView;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
import rx.Observable;
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
    List<Gank>list_Gank =new ArrayList<>();
    GankDetailAdapter adapter;
    String date;
    GankService service;
    RecyclerView recyview;
    LinearLayoutManager manager;
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
        recyview= (RecyclerView) findViewById(R.id.recyview);
        manager=new LinearLayoutManager(this);
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
    getAllData();

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

    private void addview(final GankAllData gankAllData)
    {

        Observable<Gank> obAndroid=Observable.from(gankAllData.getResults().getAndroid());
        obAndroid.map(new Func1<Gank, Gank>()
        {
            @Override
            public Gank call(Gank gank)
            {
                gank.setCategory("Android");
                return gank;
            }
        }).subscribe(new Action1<Gank>()
        {
            @Override
            public void call(Gank gank)
            {
                list_Gank.add(gank);
            }
        });
        Observable<Gank> obIos=Observable.from(gankAllData.getResults().getiOS());
        obIos.map(new Func1<Gank, Gank>()
        {
            @Override
            public Gank call(Gank gank)
            {
                gank.setCategory("IOS");
                return gank;
            }
        }).subscribe(new Action1<Gank>()
        {
            @Override
            public void call(Gank gank)
            {
                list_Gank.add(gank);
            }
        });
        Observable<Gank> obmore=Observable.from(gankAllData.getResults().get拓展资源());
        obmore.map(new Func1<Gank, Gank>()
        {
            @Override
            public Gank call(Gank gank)
            {
                gank.setCategory("拓展资源");
                return gank;
            }
        }).subscribe(new Action1<Gank>()
        {
            @Override
            public void call(Gank gank)
            {
                list_Gank.add(gank);

            }
        });

        adapter=new GankDetailAdapter(list_Gank);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);


    }



}

