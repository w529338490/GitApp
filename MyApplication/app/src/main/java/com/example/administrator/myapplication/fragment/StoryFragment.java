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
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.StoryRecyclerViewAdapter;
import com.example.administrator.myapplication.common.Ip;
import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.net.Service.HttpService;
import com.example.administrator.myapplication.ui.comm.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/22.
 */

public class StoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static StoryFragment instance;
    View view;

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    LinearLayout parent;
    HttpService service;

    List<String> datas = new ArrayList<>();
    List<Result.ResultBean.DataBean> data = new ArrayList<>();

    LinearLayoutManager manager;
    StoryRecyclerViewAdapter adapter;

    boolean reflash = false;
    int type=0;

    String str[] = new String[]{Ip.url_gif_dongtai, Ip.url_gif_xiegif, Ip.url_gif_gaoxiao};
    String url;

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
     *
     * @param type
     * @param reflash
     */
    private void initData(int type, final boolean reflash)
    {

    }

    private void getData(final String url, boolean reflash)
    {
    }

    // 更新UI
    private void updataui(final List<Result.ResultBean.DataBean> data)
    {
        adapter = new StoryRecyclerViewAdapter(StoryFragment.this.getContext(), data);
        recyview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new StoryRecyclerViewAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Intent intent = new Intent(StoryFragment.this.getContext(), WebActivity.class);
                intent.putExtra("url", data.get(position).getUrl());
            }
        });
    }

    private void initView()
    {
        fresh.setOnRefreshListener(this);
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager = new LinearLayoutManager(StoryFragment.this.getContext());
        recyview.setLayoutManager(manager);
    }

    @Override
    public void onRefresh()
    {
        reflash=true;
        initData(type,reflash);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            initData(type,reflash);
        }
    }
}
