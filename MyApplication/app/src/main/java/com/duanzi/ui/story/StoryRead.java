package com.duanzi.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duanzi.Utill.JsoupUtil;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.R;
import com.duanzi.entity.Story;
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
    private String url= null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_read);
        ButterKnife.inject(this);
        url = getIntent().getStringExtra("url");
        initWidght();
        getData(url);
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

    private void getData(final String url)
    {

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
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mune_story_reading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_add_booklist:
                ToastUtil.show("添加至我的书架");
                return true;
            case R.id.item_setting:
                ToastUtil.show("暂时不让你设置");
//                startActivity(new Intent(activity, StorySettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
