package com.duanzi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duanzi.DB.DatabaseHelper;
import com.duanzi.DB.DbBean.VideoBean;
import com.duanzi.DB.DbDao.VideoDao;
import com.duanzi.R;
import com.duanzi.Utill.FileUtil;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.common.myApplication;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
        this.videdao=new VideoDao(context);

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
            //查看是否有下载到本地的 视屏 有则加载本地,无则加载网络
                String sdkPath=videdao.findBeanByPath(results.get(position).getVideoUri()).getTittle();
                if(FileUtil.chechFile(sdkPath+".mp4"))
                {
                    holder.custom_videoplayer.canShouwDialog=false;
                    holder.custom_videoplayer.setUp(
                            FileUtil.FILE_ROOT+sdkPath+".mp4",
                            JCVideoPlayer.SCREEN_LAYOUT_LIST,
                            results.get(position).getTittle());

                    Logger.e(FileUtil.FILE_ROOT+sdkPath);
                }
            else
            {
                holder.custom_videoplayer.canShouwDialog=true;
                holder.custom_videoplayer.setUp(
                    results.get(position).getVideoUri(),
                    JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    results.get(position).getTittle());
                ToastUtil.show("播放网络视屏");

            }

            Picasso.with(myApplication.context)
                    .load(String.valueOf(thunbUrl))
                    .into(holder.custom_videoplayer.thumbImageView);
        }
        //下载 视屏
        holder.saved.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downlinsner.down(results.get(position).getVideoUri(),results.get(position).getTittle());
            }
        });

        //删除保存
        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                downlinsner.delet(results.get(position),position);
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
        TextView delete ;

        public Holder(View view)
        {
            super(view);
            tittle = (TextView) view.findViewById(R.id.tittle);
            saved = (TextView) view.findViewById(R.id.saved);
            img_share = (TextView) view.findViewById(R.id.img_share);
            delete = (TextView) view.findViewById(R.id.delete);
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
         void delet(VideoBean currentBean,int position);
    }

}
