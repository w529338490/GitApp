package com.example.administrator.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Video;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/3/27.
 */

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.Holder>
{

    List<Video.DataBean.DataBeans> results;

    LayoutInflater inflater;

    public VideoViewAdapter(List<Video.DataBean.DataBeans> results)
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
    public void onBindViewHolder(final Holder holder, int position)
    {
//        if(results.get(position).getGroup().text!=null&&results.get(position).getGroup().text.trim().length()!=0)
//        {
//            holder.tittle.setText(results.get(position).getGroup().text+"");
//        }

        if(results!=null&&results.get(position)!=null&&results.get(position).group!=null)
        {
            holder.custom_videoplayer.setUp(results.get(position).group.mp4_url,results.get(position).group.text);
     //       Logger.e(results.get(position).group.mp4_url);
            holder.tittle.setText(results.get(position).getGroup().text+"");

            Picasso.with(myApplication.context)
                    .load(String.valueOf(results.get(position).getGroup().medium_cover.getUrl_list().get(0).url))
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Drawable drawable =new BitmapDrawable(bitmap);
                            holder.custom_videoplayer.setBackground(drawable);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            Logger.e(results.get(position).getGroup().medium_cover.uri);

        }
//        JCVideoPlayer.setThumbImageViewScalType();
//
//        holder.custom_videoplayer.setThumbInCustomProject("视频/MP3缩略图地址");


        Observable<Void> observable = RxView.clicks( holder.custom_videoplayer).share();
        observable.buffer(observable.debounce(200, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Void>>() {
                    @Override
                    public void call(List<Void> voids) {
                        if(voids.size() >= 2){
                            ToastUtil.show("sahngji ");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

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
        JCVideoPlayerSimple custom_videoplayer;
        ImageView img;
        public Holder(View view)
        {

            super(view);
            tittle= (TextView) view.findViewById(R.id.tittle);
            custom_videoplayer= (JCVideoPlayerSimple) view.findViewById(R.id.custom_videoplayer);
            img= (ImageView) view.findViewById(R.id.img);
        }
    }
}
