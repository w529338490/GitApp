package com.example.administrator.myapplication.ui.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Video;

import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.HttpService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{

    List<Video.Data.DataBean> listData;
    Toolbar toolbar;
    SwipeRefreshLayout fresh;
    HttpService service;


    JCVideoPlayerSimple jcVideoPlayerSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
//
//        toolbar= (Toolbar) findViewById(R.id.toolbar);
//        fresh= (SwipeRefreshLayout) findViewById(R.id.fresh);
        jcVideoPlayerSimple= (JCVideoPlayerSimple) findViewById(R.id.custom_videoplayer);

        Map<String, String> headData = new HashMap<>();
        headData.put("key1", "value1");
        headData.put("key2", "value2");
        jcVideoPlayerSimple.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                "嫂子想我没");

          init();
    }

    private void init()
    {

        JCFullScreenActivity.toActivity(this,
                "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayerStandard.class, "嫂子真牛逼");
//        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
//        fresh.setOnRefreshListener(this);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                finish();
//            }
//        });
//
//        getData();



    }
    public void getData()
    {




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
