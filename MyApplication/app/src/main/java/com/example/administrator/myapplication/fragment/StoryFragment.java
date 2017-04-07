package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.adapter.StoryRecyclerViewAdapter;
import com.example.administrator.myapplication.common.Ip;
import com.example.administrator.myapplication.entity.Story;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.attr.type;

/**
 * Created by k9579 on 2017/2/25.
 */
public class StoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static StoryFragment instance;
    View view;

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    LinearLayout parent;

    List<Story> storyList = new ArrayList<>();
    LinearLayoutManager manager;
    StoryRecyclerViewAdapter adapter;

    boolean reflash = false;

    String url = "";
//    String str[] = new String[]{Ip.url_story_qihuan, Ip.url_story_wuxia, Ip.url_story_history, Ip.url_story_yule};
    String str[] = new String[]{Ip.url_story_qihuan};

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
        view = inflater.inflate(R.layout.gif_fragment, container, false);
        recyview = (RecyclerView) view.findViewById(R.id.recyview);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        manager = new LinearLayoutManager(StoryFragment.this.getContext());
        parent = (LinearLayout) view.findViewById(R.id.parent);
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
        rx.Observable<Integer> observable = rx.Observable.create(new rx.Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {
                ArrayList<Story> storyList= JsoupUtil.getStory(url, type);
                Logger.e("拿到的集合就是："+storyList.toString());
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
//        adapter=new StoryRecyclerViewAdapter(getContext(),list);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);
    }

    @Override
    public void onRefresh()
    {
        reflash = true;
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
//            getData(url,reflash);
        }
    }
}
