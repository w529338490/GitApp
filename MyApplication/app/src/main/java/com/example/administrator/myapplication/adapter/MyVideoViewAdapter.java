package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.DB.DbDao.VideoDao;
import com.example.administrator.myapplication.DB.DatabaseHelper;
import com.example.administrator.myapplication.DB.DbBean.VideoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MyVideoViewAdapter extends RecyclerView.Adapter<MyVideoViewAdapter.Holder>
{

    List<VideoBean> results;
    DatabaseHelper helper ;
    VideoDao  videdao;
    LayoutInflater inflater;
    private Context context;
    DownlodLiner downlinsner;

    public MyVideoViewAdapter( List<VideoBean> results, Context context)
    {
        this.results = results;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.helper = DatabaseHelper.getHelper(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        Holder holder = null;
        if (holder == null)
        {
            holder = new Holder(inflater.inflate(R.layout.myvideoview_adapter, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position)
    {

        if (results != null && results.get(position) != null )
        {
            final String thunbUrl=results.get(position).getThumbUrl();
            holder.custom_videoplayer.setUp(
                    results.get(position).getVideoUri(),
                    JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    results.get(position).getTittle()
            );

            Picasso.with(myApplication.context)
                    .load(String.valueOf(thunbUrl))
                    .into(holder.custom_videoplayer.thumbImageView);
        }
        holder.saved.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downlinsner.down(results.get(position).getVideoUri(),results.get(position).getTittle());
            }
        });
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
        TextView saved ;
        TextView img_share ;

        public Holder(View view)
        {
            super(view);
            tittle = (TextView) view.findViewById(R.id.tittle);
            saved = (TextView) view.findViewById(R.id.saved);
            img_share = (TextView) view.findViewById(R.id.img_share);
            custom_videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.custom_videoplayer);

        }
    }


    public void SetDownlodLiner(DownlodLiner liner)
    {
        this.downlinsner=liner;
    }
    public interface  DownlodLiner
    {
        void down(String path,String tittle);

    }

}
