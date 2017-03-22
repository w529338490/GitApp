package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.entity.RandomData;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/3/22.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.Holder>
{
    List<RandomData.Gank> results;
    Context context;
    LayoutInflater inflater;

    public GankAdapter(Context context, List<RandomData.Gank> results)
    {
        this.context = context;
        this.results = results;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public GankAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        GankAdapter.Holder holder = null;
        if(holder==null)
        {
            holder=new Holder(inflater.inflate(R.layout.gankadapter,parent,false));

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(GankAdapter.Holder holder, int position)
    {
        Glide.with(context)
                .load(results.get(position).getUrl())
                .transform(new GlideRoundTransform(context,20))
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .crossFade(1500)
                .into(holder.img);
        holder.desc.setText(results.get(position).getDesc());
    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView desc;
        TextView date;



        public Holder(View view)
        {
            super(view);
            img= (ImageView) view.findViewById(R.id.img);
            desc= (TextView) view.findViewById(R.id.desc);
            date= (TextView) view.findViewById(R.id.date);

        }
    }
}
