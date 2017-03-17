package com.example.administrator.myapplication.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.adapter.GifRecyclerViewAdapter;
import com.example.administrator.myapplication.adapter.News_fragmentAdapter;
import com.example.administrator.myapplication.common.Ip;
import com.example.administrator.myapplication.entity.Gif;
import com.example.administrator.myapplication.entity.Result;
import com.example.administrator.myapplication.net.Service.HttpService;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;
import static android.R.id.list;

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
    HttpService service;

    List<Gif> list=new ArrayList<>();

    LinearLayoutManager manager;
    GifRecyclerViewAdapter adapter;

    boolean reflash=false;

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
        manager=new LinearLayoutManager(GifFragment.this.getContext());
        parent= (LinearLayout) view.findViewById(R.id.parent);
        url=str[getArguments().getInt("type")];
        initView();
        getData(url,reflash);
        return view;
    }

    private void getData(final String url, boolean reflash)

    {
        new Thread() {
            @Override
            public void run() {
                super.run();
                list = JsoupUtil.getGif(url, type);
                Log.e("list","=================="+list.size());
                if (list.size() > 0) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUi();
                            }
                        });
                    }


                }

            }
        }.start();


    }

    private void updateUi()
    {
        adapter=new GifRecyclerViewAdapter(GifFragment.this.getContext(),list);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);

    }

    private void initView()
    {
        fresh.setOnRefreshListener(this);
        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        manager=new LinearLayoutManager(GifFragment.this.getContext());
        recyview.setLayoutManager(manager);


    }

    @Override
    public void onRefresh() {

    }
}
