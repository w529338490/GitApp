package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Gif;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class GifRecyclerViewAdapter extends RecyclerView.Adapter<GifRecyclerViewAdapter.Holder>
{
    Context context;
    LayoutInflater inflater;
    List<Gif> list;
    OnViewClickListener listener;

    public GifRecyclerViewAdapter(Context context, List<Gif> list) {
        this.context = context;
        this.list = list;
        this.inflater=LayoutInflater.from(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Holder holder=null;
        if(holder==null)
        {
            holder=new Holder(inflater.inflate(R.layout.gif_adapter,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position)

    {

        holder.tv.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getUrl())
                .asBitmap()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mv);
        holder.mv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Glide.with(context)
                        .load(list.get(position).getUrl())
                        .asGif()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_mr)
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(holder.mv);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tv;
        ImageView mv;
        View parent;
        public Holder(View view)
        {
            super(view);
            tv= (TextView) view.findViewById(R.id.gtittle);
            mv= (ImageView) view.findViewById(R.id.gpic);
            parent=view;
        }
        public void setImg(String imgUrl, final int position)
        {

            if (!imgUrl.contains("http")) {
                imgUrl = "http://www.zbjuran.com" + imgUrl;
                //Log.i("imgUrl", imgUrl);
            }
//            final WeakReference<ImageView> wr = new WeakReference<>(iv);
//            final ImageView wr_iv = wr.get();




        }

    }
    public interface OnViewClickListener
    {
        void onItemViewClickListener(int position);
    }
}
