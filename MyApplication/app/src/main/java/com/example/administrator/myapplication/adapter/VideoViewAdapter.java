package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.RandomData;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;

/**
 * Created by Administrator on 2017/3/27.
 */

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.Holder>
{

    List<RandomData.Gank> results;

    LayoutInflater inflater;

    public VideoViewAdapter(List<RandomData.Gank> results)
    {
        this.results = results;
        this.inflater= LayoutInflater.from(myApplication.context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        Holder holder=null;
        if(holder==null)
        {
            holder=new Holder(inflater.inflate(R.layout.videoview_adapter,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        holder.tittle.setText(results.get(position).getDesc());
        holder.custom_videoplayer.setUp(results.get(position).getUrl());

    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tittle;
        JCVideoPlayerSimple custom_videoplayer;
        public Holder(View view)
        {
            super(view);
            tittle= (TextView) view.findViewById(R.id.tittle);
            custom_videoplayer= (JCVideoPlayerSimple) view.findViewById(R.id.custom_videoplayer);
        }
    }
}
