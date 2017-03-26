package com.example.administrator.myapplication.ui.gank;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.eventbus.GankEvent;
import com.example.administrator.myapplication.ui.view.GifImageView;
import com.orhanobut.logger.Logger;

import de.greenrobot.event.EventBus;

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
    //    toolbar= (Toolbar) findViewById(R.id.toolbar);

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
                .into(tittle_img);//    toolbar.setTitle("这里是Title");
     //   toolbar.setSubtitle("这里是子标题");
    //    toolbar.setTitleTextColor(Color.RED);




    }


    public void onEventMainThread (GankEvent event)
{
    gankDetail=event;
    Logger.json(gankDetail.toString());
    Log.e("gankDetail","============================"+gankDetail.getGankDetail().getUrl());
}

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

