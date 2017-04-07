package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.RandomData;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ArtcleAdapter extends RecyclerView.Adapter<ArtcleAdapter.Holder>
{

    List<Article> results;
    LayoutInflater inflater;
    GankAdapter.OnItemViewClickLisnter onItemViewClickLisnter;  //点击每个item监听事件

    public ArtcleAdapter(List<Article> results)
    {
        this.results = results;
        this.inflater=LayoutInflater.from(myApplication.context);
    }

    @Override
    public ArtcleAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Holder holder=null;
        if(holder==null)
        {
            holder=new Holder(inflater.inflate(R.layout.item_gallery,parent,false));

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ArtcleAdapter.Holder holder, final int position)
    {
        holder.who.setText(results.get(position).author);
        holder.content.setText(results.get(position).description);
          int sum=results.get(position).position+1;
        holder.tvPrecent.setText(sum+"/"+results.size());

        holder.pView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onItemViewClickLisnter.getItemViewPosition(position);
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
        TextView who;
        TextView  content;
        TextView tvPrecent;
        View pView;
        public Holder(View view)
        {
            super(view);
            who= (TextView) view.findViewById(R.id.who);
            content= (TextView) view.findViewById(R.id.content);
            tvPrecent= (TextView) view.findViewById(R.id.tvPrecent);
            pView=view;
        }
    }

    public  void setOnItemViewClickLisnter (GankAdapter.OnItemViewClickLisnter lisnter)
    {
        this.onItemViewClickLisnter=lisnter;
    }
    public interface OnItemViewClickLisnter
    {
        void getItemViewPosition(int Postion);
    }
}
