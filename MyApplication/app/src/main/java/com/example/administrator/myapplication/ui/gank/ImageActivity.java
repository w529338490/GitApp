package com.example.administrator.myapplication.ui.gank;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.eventbus.BeseEvent;
import com.trello.rxlifecycle.components.RxActivity;

import de.greenrobot.event.EventBus;

public class ImageActivity extends RxActivity implements View.OnClickListener
{

    ImageView imgs;
    String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iamge);
        EventBus.getDefault().registerSticky(this);  //在onCreate，注入EventBus    使用registerSticky,防止Activity未启动,接收不到广播
        initView();
        initData();
    }

    private void initData()
    {
        Glide.with(ImageActivity.this)
                .load(imgPath)
                .transform(new GlideRoundTransform(this,20))
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .crossFade(1500)
                .into(imgs);

    }

    private void initView()
    {
        imgs= (ImageView) findViewById(R.id.imgs);
        imgs.setOnClickListener(this);
        initData();
    }

    //5、接收消息
   // 接收消息时，我们使用EventBus中最常用的onEventMainThread（）函数来接收消息，
    // 具体为什么用这个，我们下篇再讲，

    public void onEventMainThread(BeseEvent event)
    {

        imgPath = event.getUrl();



    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //onDestroy的时候 销毁 EvenBus
//        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imgs:

                break;
        }

    }
}
