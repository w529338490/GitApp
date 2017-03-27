package com.example.administrator.myapplication.ui.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.GankAdapter;
import com.example.administrator.myapplication.adapter.VideoViewAdapter;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.entity.Video;

import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;
import com.example.administrator.myapplication.net.Service.HttpService;
import com.example.administrator.myapplication.ui.gank.GankActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VideoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{

    List<Video.Data.DataBean> listData;
    Toolbar toolbar;
    SwipeRefreshLayout fresh;
    GankService service;
    LinearLayoutManager manager;
    RecyclerView recyview;
    VideoViewAdapter adapter;

    int page_num = 20;
    List<RandomData.Gank> list = new ArrayList<>();



    JCVideoPlayerSimple jcVideoPlayerSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
//
//        toolbar= (Toolbar) findViewById(R.id.toolbar);
//        fresh= (SwipeRefreshLayout) findViewById(R.id.fresh);

          init();
    }

    private void init()
    {
        recyview= (RecyclerView) findViewById(R.id.recyview);
        manager=new LinearLayoutManager(this);
        // init();

        JCFullScreenActivity.toActivity(this,
                "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayerStandard.class, "嫂子真牛逼");

        getData();



    }
    public void getData()
    {

        service =  Api.getInstance().apiGank();
        Observable<RandomData> obsVideo = service.getRandomData("休息视频", page_num);
     obsVideo .subscribeOn(Schedulers.io())//指定获取数据在io子线程
              .unsubscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<RandomData>()
              {
                  @Override
                  public void call(RandomData randomData)
                  {
                      UpDataUi(randomData);
                  }


              }) ;
    }

    public void UpDataUi(RandomData randomData)
    {
        if (randomData.isError() == false && randomData.getResults() != null)
        {
            list = randomData.getResults();
        }
        adapter=new VideoViewAdapter(list);

        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);

    }
    @Override
    public void onRefresh() {
        getData();

    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    //当屏幕方向改变时候
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            toolbar.setVisibility(View.GONE);
        }else{
            toolbar.setVisibility(View.VISIBLE);
        }


    }

}
