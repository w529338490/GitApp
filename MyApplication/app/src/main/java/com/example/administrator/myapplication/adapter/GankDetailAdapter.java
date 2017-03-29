package com.example.administrator.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Gank;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class GankDetailAdapter extends RecyclerView.Adapter<GankDetailAdapter.Holder>
{
    List<Gank> results;
    LayoutInflater inflater;

    public GankDetailAdapter(List<Gank> results)
    {
        this.results = results;
        this.inflater=LayoutInflater.from(myApplication.context);

    }


    @Override
    public GankDetailAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Holder holder=null;
        if(holder==null)
        {
            holder=new Holder(inflater.inflate(R.layout.gank_detail_adapter,parent,false));

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(GankDetailAdapter.Holder holder, int position)
    {


        if(position==0)
        {
            holder.category.setVisibility(View.VISIBLE);
            holder.category.setText(results.get(0).getCategory());

        }
        else if(position>0&&!results.get(position).getCategory().equals(results.get(position-1).getCategory()))
        {
            Logger.e(results.get(position).getCategory());
            holder.category.setVisibility(View.VISIBLE);
            holder.category.setText(results.get(position).getCategory());

        }else
        {
            holder.category.setVisibility(View.GONE);
        }
        holder.desc.setText(results.get(position).getDesc());
        holder.who.setText(results.get(position).getWho());


    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView category;
        LinearLayout  gank_layout;
        TextView desc;
        TextView who;

        public Holder(View view)
        {
            super(view);
            category= (TextView) view.findViewById(R.id.category);
            gank_layout= (LinearLayout) view.findViewById(R.id.gank_layout);
            desc= (TextView) view.findViewById(R.id.desc);
            who= (TextView) view.findViewById(R.id.who);
        }
    }
}
