package com.example.administrator.myapplication.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.entity.Story;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StoryRead extends RxAppCompatActivity
{
    @InjectView(R.id.toolbar)
    Toolbar tb_bar;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.bt_previous)
    Button bt_previous;
    @InjectView(R.id.bt_next)
    Button bt_next;

    private Story story;
    private Activity activity = StoryRead.this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_read);
        ButterKnife.inject(this);
        initWidght();
        getData();
    }


    private void initWidght()
    {
        setSupportActionBar(tb_bar);
        tb_bar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void getData()
    {
        final String url = getIntent().getStringExtra("url");
        if (null == url || "".equals(url))
        {
            Snackbar.make(tb_bar.getRootView(), "网络出现了一些故障，请您稍后再试", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else
        {
            //使用RxJava 异步网络请求
            Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>()
            {
                @Override
                public void call(Subscriber<? super Integer> subscriber)
                {
                    story = JsoupUtil.getStoryText(url);
                    subscriber.onNext(1);
                }
            });
            observable.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//处理结果回调 在UI 主线程
                    .compose(this.<Integer>bindToLifecycle())
                    .subscribe(new Action1<Integer>()
                    {
                        @Override
                        public void call(Integer integer)
                        {
                            if (integer == 1)
                            {
                                updateUi();
                            }
                        }
                    });
        }
    }

    private void updateUi()
    {
        tv_content.setText(story.getText());
    }

    @OnClick({R.id.bt_previous, R.id.bt_next})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_previous:
            try
            {

                if (story.getPrevious() == null || "".equals(story.getPrevious()))
                {
                    Snackbar.make(bt_previous, "已经是第一章了", Snackbar.LENGTH_SHORT).show();
                } else
                {
                    startActivity(new Intent(activity, StoryRead.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("url", story.getPrevious()));
                }
            }catch (NullPointerException e)
            {
                ToastUtil.show("亲，你点击太快了！！");
            }

                break;
            case R.id.bt_next:
            try
            {

                if (story.getNext() == null || "".equals(story.getNext()))
                {
                    Snackbar.make(bt_next, "已经是最后一章了", Snackbar.LENGTH_SHORT).show();
                } else
                {

                    startActivity(new Intent(activity, StoryRead.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("url", story.getNext()));

                }
            }catch (NullPointerException e)
            {

            }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        getData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
