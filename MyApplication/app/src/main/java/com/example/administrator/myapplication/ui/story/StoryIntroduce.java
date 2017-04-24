package com.example.administrator.myapplication.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.adapter.StoryCatalogsAdapter;
import com.example.administrator.myapplication.entity.Story;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 小说预览
 */
public class StoryIntroduce extends AppCompatActivity
{

    String intentUri = null;
    @InjectView(R.id.toolbar)
    Toolbar tb_bar;
    @InjectView(R.id.iv_story_pic)
    ImageView iv_pic;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_author)
    TextView tv_author;
    @InjectView(R.id.tv_words)
    TextView tv_words;
    @InjectView(R.id.tv_tpye)
    TextView tv_tpye;
    @InjectView(R.id.tv_introduction)
    TextView tv_introduction;
    @InjectView(R.id.rv_list)
    RecyclerView rv_list;
    @InjectView(R.id.bt_startRead)
    Button bt_read;

    private Story story;

    private Activity activity = StoryIntroduce.this;
    private StoryCatalogsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_introduce);
        intentUri = getIntent().getStringExtra("uri");
        ButterKnife.inject(this);
        initWidght();
        getData(intentUri);
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

    private void getData(final String uri)
    {
        //使用RxJava 异步网络请求
        Observable<Integer> observable = Observable.create(new rx.Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {
                story = JsoupUtil.getStoryDetail(uri);
                subscriber.onNext(1);
            }
        });
        observable.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//处理结果回调 在UI 主线程
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

    private void updateUi()
    {
        Glide.with(activity)
                .load(story.getStoryPic())
                .transform(new GlideRoundTransform(activity, 1))
                .placeholder(R.mipmap.ic_mr)
                .crossFade(1500)
                .into(iv_pic);
        tv_title.setText("作品：" + story.getTitle());
        tv_author.setText("作者：" + story.getAuthor());
        tv_tpye.setText("分类：" + story.getType());
        tv_words.setText("字数：" + story.getWords());
        tv_introduction.setText(story.getContent());

        mAdapter = new StoryCatalogsAdapter(activity, story.getCatalogList());
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(StoryIntroduce.this));
        rv_list.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new StoryCatalogsAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, String uri)
            {
                startActivity(new Intent(activity, StoryRead.class).putExtra("url", uri));
            }
        });
    }

    @OnClick({R.id.bt_startRead})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_startRead:
                startActivity(new Intent(activity, StoryRead.class).putExtra("url", story.getReadUrl()));
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
