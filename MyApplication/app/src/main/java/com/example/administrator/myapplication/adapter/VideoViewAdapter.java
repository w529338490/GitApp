package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Video;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/3/27.
 */

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.Holder>
{

    List<Video.DataBean.DataBeans> results;

    LayoutInflater inflater;
    JCMediaManager mediaManager;
    private Context context;

    public VideoViewAdapter(List<Video.DataBean.DataBeans> results,Context context)
    {
        this.results = results;
        this.inflater= LayoutInflater.from(context);
        this.context=context;
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
    public void onBindViewHolder(final Holder holder, int position)
    {

        if(results!=null&&results.get(position)!=null&&results.get(position).group!=null)
        {
            holder.custom_videoplayer.setUp(
                    results.get(position).group.mp4_url,
                    JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    results.get(position).getGroup().text
            );
            Logger.e(results.get(position).group.mp4_url);

      //      holder.tittle.setText(results.get(position).getGroup().text+"");
            Picasso.with(myApplication.context)
                    .load(String.valueOf(results.get(position).getGroup().medium_cover.getUrl_list().get(0).url))
                    .into(holder.custom_videoplayer.thumbImageView);

        }
    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tittle;
        JCVideoPlayerStandard custom_videoplayer;
        public Holder(View view)
        {
            super(view);
            tittle= (TextView) view.findViewById(R.id.tittle);
            custom_videoplayer= (JCVideoPlayerStandard) view.findViewById(R.id.custom_videoplayer);

        }
    }
}
