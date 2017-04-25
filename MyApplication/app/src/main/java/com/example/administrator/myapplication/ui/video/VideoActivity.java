package com.example.administrator.myapplication.ui.video;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.VideoViewAdapter;
import com.example.administrator.myapplication.DB.DbBean.VideoBean;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.entity.Video;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VideoActivity extends RxAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    Toolbar toolbar;
    SwipeRefreshLayout fresh;
    GankService service;
    LinearLayoutManager manager;
    RecyclerView recyview;
    VideoViewAdapter adapter;

    int page_num = 20;
    List<RandomData.Gank> list = new ArrayList<>();
    List<Video.DataBean.DataBeans> listData = new ArrayList();
    List<VideoBean> myVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        init();
    }

    private void init()
    {
        myVideos= (List<VideoBean>) this.getIntent().getSerializableExtra("myvideo");
        recyview = (RecyclerView) findViewById(R.id.recyview);
        manager = new LinearLayoutManager(this);
        fresh = (SwipeRefreshLayout)findViewById(R.id.fresh);
        fresh.setOnRefreshListener(this);

        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);

        if(myVideos!=null&&myVideos.size()!=0)
        {
            fresh.setRefreshing(false);
            fresh.setVisibility(View.GONE);
            listData.clear();
            for(int i=0;i<myVideos.size();i++)
            {
                Video.DataBean.DataBeans dataBeans=new Video.DataBean.DataBeans();
                Video.DataBean.DataBeans.GroupBean groupBean=new Video.DataBean.DataBeans.GroupBean();

                if(myVideos.get(i).getThumbUrl()!=null&&myVideos.get(i).getThumbUrl().trim().length()!=0)
                {

                    List<Video.DataBean.DataBeans.GroupBean.MediumCoverBean.UrlListBean> url_list = new ArrayList<>();
                    Video.DataBean.DataBeans.GroupBean.MediumCoverBean.UrlListBean UrlListBean = new Video.DataBean.DataBeans.GroupBean.MediumCoverBean.UrlListBean();
                    UrlListBean.setUrl(myVideos.get(i).getThumbUrl());

                    dataBeans.group.medium_cover.setUrl_list(url_list);
                }

                groupBean.mp4_url=myVideos.get(i).getVideoUri();
                dataBeans.setGroup(groupBean);
                listData.add(dataBeans);

            }
            adapter = new VideoViewAdapter(listData, VideoActivity.this);
            recyview.setLayoutManager(manager);
            adapter.notifyDataSetChanged();
            recyview.setAdapter(adapter);
        }else
        {
            fresh.setVisibility(View.VISIBLE);
            getData();
        }

    }

    public void getData()
    {
        service = Api.getInstance().apiGank();
        Observable<Video> obsVideo = service.getVideo();
        obsVideo.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Video>bindToLifecycle())
                .subscribe(new Action1<Video>()
                {
                    @Override
                    public void call(Video randomData)
                    {
                        UpDataUi(randomData);
                    }

                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
    }

    public void UpDataUi(Video randomData)
    {
        if (randomData.getMessage().equals("success"))
        {
            listData = randomData.data.data;
            fresh.setRefreshing(false);
        }
        adapter = new VideoViewAdapter(listData, VideoActivity.this);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);

    }

    @Override
    public void onRefresh()
    {
        getData();

    }


    @Override
    protected void onPause()
    {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
