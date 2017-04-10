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
import com.example.administrator.myapplication.adapter.ArtcleAdapter.GifRecyclerViewAdapter;
import com.example.administrator.myapplication.common.Ip;
import com.example.administrator.myapplication.entity.Gif;
import com.example.administrator.myapplication.net.Service.HttpService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/2/22.
 */

public class GifFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static GifFragment instance;
    View view;

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    LinearLayout parent;

    List<Gif> list=new ArrayList<>();

    LinearLayoutManager manager;
    GifRecyclerViewAdapter adapter;

    boolean reflash=false;
    HttpService service;

    String str[]= new String[]{ Ip.url_gif_dongtai, Ip.url_gif_xiegif, Ip.url_gif_gaoxiao};
    String url;

    public static GifFragment newInstance(int type) {

            instance = new GifFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",type);
            instance.setArguments(bundle);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.gif_fragment,container,false);

        recyview= (RecyclerView) view.findViewById(R.id.recyview);
        fresh= (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        manager=new LinearLayoutManager(getActivity());
        parent= (LinearLayout) view.findViewById(R.id.parent);
        url=str[getArguments().getInt("type")];
        initView();
        getData(url,reflash);
        return view;
    }

    private void getData(final String url, boolean reflash)

    {

        //使用RxJava 异步网络请求
        Observable<Integer> observable=Observable.create(new Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {


                list = JsoupUtil.getGif(url, type);
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
                        if(integer==1)
                        {
                            updateUi();
                        }
                    }
                });

        //使用原始的线程方法
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                   list = JsoupUtil.getGif(url, type);
//                if (list.size() > 0) {
//                    if (getActivity() != null) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateUi();
//                            }
//                        });
//                    }
//                }
//
//            }
//        }.start();
//

    }

    private void updateUi()
    {
        adapter=new GifRecyclerViewAdapter(getContext(),list);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);

    }

    private void initView()
    {
        fresh.setOnRefreshListener(this);
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager=new LinearLayoutManager(getActivity());
        recyview.setLayoutManager(manager);


    }

    @Override
    public void onRefresh() {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            getData(url,reflash);
        }
    }
}
