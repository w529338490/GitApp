package com.example.administrator.myapplication.ui.gank;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.eventbus.BeseEvent;
import com.example.administrator.myapplication.eventbus.FirstEvent;
import com.trello.rxlifecycle.components.RxActivity;

import de.greenrobot.event.EventBus;

public class IamgeActivity extends RxActivity
{

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iamge);
        EventBus.getDefault().register(this);  //在onCreate，注入EventBus
        initView();
        initData();
    }

    private void initData()
    {

    }

    private void initView()
    {
        img= (ImageView) findViewById(R.id.img);
    }

    //5、接收消息
   // 接收消息时，我们使用EventBus中最常用的onEventMainThread（）函数来接收消息，
    // 具体为什么用这个，我们下篇再讲，

    public void onEventMainThread(BeseEvent event)
    {

        String msg = event.getUrl();
        Log.e("msg","================="+msg);

//        Glide.with(this)
//                .load(msg)
//                .transform(new GlideRoundTransform(this,20))
//                .centerCrop()
//                .placeholder(R.mipmap.ic_mr)
//                .crossFade(1500)
//                .into(img);
    }

    public void onEventMainThread(FirstEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //onDestroy的时候 销毁 EvenBus
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

}
