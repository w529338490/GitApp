package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.adapter.StorysAdapter;
import com.example.administrator.myapplication.common.Ip;
import com.example.administrator.myapplication.entity.Story;
import com.example.administrator.myapplication.ui.story.StoryIntroduce;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.example.administrator.myapplication.common.Ip.url_story_kehuan;

/**
 * Created by k9579 on 2017/2/25.
 */
public class StoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static StoryFragment instance;
    View view;

    RecyclerView recyview;
    SwipeRefreshLayout fresh;

    List<Story> storyList = new ArrayList<>();
    LinearLayoutManager manager;
    StorysAdapter adapter;

    boolean reflash = false;

    String url = "";
    String str[] = new String[]{Ip.url_story_qihuan, Ip.url_story_wuxia, Ip.url_story_history, Ip.url_story_yule, url_story_kehuan};

    public static StoryFragment newInstance(int type)
    {
        instance = new StoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.story_fragment, container, false);
        recyview = (RecyclerView) view.findViewById(R.id.rv_list);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.story_fresh);
        manager = new LinearLayoutManager(getActivity());
        url = str[getArguments().getInt("type")];
        initView();
        getData(url, reflash);
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        fresh.setOnRefreshListener(this);
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager = new LinearLayoutManager(StoryFragment.this.getContext());
        recyview.setLayoutManager(manager);
    }

    /**
     * @param url
     * @param reflash
     */
    private void getData(final String url, boolean reflash)
    {
        //使用RxJava 异步网络请求
        Observable<Integer> observable = Observable.create(new rx.Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {
                storyList = JsoupUtil.getStory(url);
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

    // 更新UI
    private void updateUi()
    {

        adapter=new StorysAdapter(getContext(),storyList);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);
        adapter.setOnItemClickListener(new StorysAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, Story story)
            {
                startActivity(new Intent(getActivity(), StoryIntroduce.class).putExtra("uri", story.getUri()));
            }
        });
    }

    @Override
    public void onRefresh()
    {
        reflash = true;
        updateUi();
        adapter.notifyDataSetChanged();
        fresh.setRefreshing(false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            getData(url, reflash);
        }
    }
}