package com.example.administrator.myapplication;

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

import com.example.administrator.myapplication.entity.Video;
import com.example.administrator.myapplication.media.view.VideoListLayout;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.HttpService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    VideoListLayout layout;
    List<Video.Data.DataBean> listData;
    Toolbar toolbar;
    SwipeRefreshLayout fresh;
    HttpService service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        layout=(VideoListLayout)findViewById(R.id.layout);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        fresh= (SwipeRefreshLayout) findViewById(R.id.fresh);
          init();
    }

    private void init()
    {
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        fresh.setOnRefreshListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        getData();
    }
    public void getData()
    {
        service=  Api.getInstance().Api_News();
        Call<Video> call=service.Get_video();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response)
            {
                if (fresh != null) {
                    fresh.setRefreshing(false);
                }
                if (response.body() != null) {
                    listData = response.body().getData().getData();
                    for (int i = 0; i < listData.size(); i++) {
                        if(listData.get(i).getType() != 1){//过滤掉不是视频的数据
                            listData.remove(i);
                        }
                    }

                    layout.setListData(listData);
                    Log.e("listData","======================"+listData.size()+">>"+listData.get(0));
                }

            }

            @Override
            public void onFailure(Call<Video> call, Throwable t)
            {

                //Log.i("video", t.getMessage());
                if (fresh != null) {
                    fresh.setRefreshing(false);
                }
            }
        });


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
