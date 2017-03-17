package com.example.administrator.myapplication.fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.WebActivity;
import com.example.administrator.myapplication.adapter.News_fragmentAdapter;
import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    List<String> datas=new ArrayList<>();
    List<Result.ResultBean.DataBean> data=new ArrayList<>();

    LinearLayoutManager manager;
    News_fragmentAdapter adapter;


    String[] str_type = new String[]{"top","keji","shehui","guonei","yule"};
    int type=0;
    boolean reflash=false;

    public static NewsFragment newInstance(int type) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.news_fragment,container,false);

        recyview= (RecyclerView) view.findViewById(R.id.recyview);
        fresh= (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        parent= (LinearLayout) view.findViewById(R.id.parent);

        type=this.getArguments().getInt("type");
        initView( );
        initData( type,reflash);
        return  view;
    }

    private void initData(int type, final boolean reflash)
    {
        service= Api.getInstance().Api_News();
        Call<Result> call=service.Get_news(str_type[type]);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()&&response.body().getResult().getData()!=null&& response.body().getError_code()==0){
                    data= response.body().getResult().getData();
                    int max=data.size();
                    int min=0;
                    Random random = new Random();
                    int s = random.nextInt(max)%(max-min+1) + min;

                    fresh.setRefreshing(false);
                    Glide.with(NewsFragment.this)
                            .load(data.get(s).getThumbnail_pic_s())
                            .asBitmap()
                            .fitCenter()
                            .into(new SimpleTarget<Bitmap>()
                            {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                                {
                                    Drawable drawable =new BitmapDrawable(resource);
                                    if(drawable!=null)
                                    {
                                        parent.setBackground(drawable);
                                    }

                                }
                            });

                    if(reflash)
                    {
                        Toast.makeText(NewsFragment.this.getContext(),"刷新完成",Toast.LENGTH_SHORT).show();
                    }
                    //更新UI
                    updataui(data);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t)
            {
                Toast.makeText(NewsFragment.this.getContext(),"网络加载失败",Toast.LENGTH_SHORT).show();
                if (fresh != null) {
                    fresh.setRefreshing(false);
                }
            }
        });
    }
    //跟新UI
    private void updataui(final List<Result.ResultBean.DataBean> data)
    {
        adapter=new News_fragmentAdapter(NewsFragment.this.getContext(),data);
        recyview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new News_fragmentAdapter.OnItemClickListener()
        {
            @Override
            public void getData(int position)
            {
                Intent intent=new Intent(NewsFragment.this.getContext(), WebActivity.class);
                intent.putExtra("url",data.get(position).getUrl());
                NewsFragment.this.getContext().startActivity(intent);
            }
        });
    }

    private void initView()
    {
        fresh.setOnRefreshListener(this);
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager=new LinearLayoutManager(NewsFragment.this.getContext());
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

}
