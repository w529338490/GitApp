package com.duanzi.fragment;


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
import android.widget.Toast;

import com.duanzi.entity.Result;
import com.duanzi.net.Api;
import com.duanzi.ui.comm.WebActivity;
import com.duanzi.R;
import com.duanzi.adapter.NewsFragmentAdapter;
import com.duanzi.net.Service.HttpService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/2/19.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static NewsFragment instance;
    View view;

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    LinearLayout parent;
    HttpService service;


    List<String> datas = new ArrayList<>();
    List<Result.ResultBean.DataBean> data = new ArrayList<>();

    LinearLayoutManager manager;
    NewsFragmentAdapter adapter;
    Call<Result> call;


    String[] str_type = new String[]{"top", "keji", "shehui", "guonei", "yule"};
    int type = 0;
    boolean reflash = false;

    public static NewsFragment newInstance(int type)
    {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.news_fragment, container, false);

        recyview = (RecyclerView) view.findViewById(R.id.recyview);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        parent = (LinearLayout) view.findViewById(R.id.parent);

        type = this.getArguments().getInt("type");
        initView();
        initData(type, reflash);
        return view;
    }


    /**
     * @param type
     * @param reflash
     */
    private void initData(int type, final boolean reflash)
    {
        service = Api.getInstance().Api_News();  //新闻 借口
//
        call = service.getNews(str_type[type]);

        call.enqueue(new Callback<Result>()
        {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response)
            {
                ///
                if (response.isSuccessful() && response.body().getResult().getData() != null && response.body().getError_code() == 0)
                {
                    data = response.body().getResult().getData();

                    fresh.setRefreshing(false);

                    if (reflash)
                    {
                        Toast.makeText(NewsFragment.this.getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                    //更新UI
                    updataui(data);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t)
            {
                Toast.makeText(NewsFragment.this.getContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                if (fresh != null)
                {
                    fresh.setRefreshing(false);
                }
            }
        });


    }

    //跟新UI
    private void updataui(final List<Result.ResultBean.DataBean> data)
    {
        adapter = new NewsFragmentAdapter(NewsFragment.this.getContext(), data);
        recyview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new NewsFragmentAdapter.OnItemClickListener()
        {
            @Override
            public void getData(int position)
            {
                Intent intent = new Intent(NewsFragment.this.getContext(), WebActivity.class);
                intent.putExtra("url", data.get(position).getUrl());
                intent.putExtra("tittle",data.get(position).getTitle());
                NewsFragment.this.getContext().startActivity(intent);


            }
        });
    }

    private void initView()
    {
        fresh.setOnRefreshListener(this);

        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager = new LinearLayoutManager(NewsFragment.this.getContext());
        recyview.setLayoutManager(manager);


    }

    @Override
    public void onRefresh()
    {
        reflash = true;
        initData(type, reflash);

    }


    @Override
    public void onPause()
    {
        super.onPause();
        call.cancel();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
         call.cancel();
         ButterKnife.reset(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            // fresh.setRefreshing(true);
            initData(type, reflash);
        }
    }
}
