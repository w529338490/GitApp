package com.example.administrator.myapplication.adapter.ArtcleAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Article;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ArtcleAdapter extends RecyclerView.Adapter<ArtcleAdapter.Holder>
{

    List<Article> results;
    LayoutInflater inflater;

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
    public void onBindViewHolder(ArtcleAdapter.Holder holder, int position)
    {
        holder.who.setText(results.get(position).author);
        holder.content.setText(results.get(position).description);
        int sum=position+1;
        holder.tvPrecent.setText(sum+"/"+results.size());

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
        public Holder(View view)
        {
            super(view);
            who= (TextView) view.findViewById(R.id.who);
            content= (TextView) view.findViewById(R.id.content);
            tvPrecent= (TextView) view.findViewById(R.id.tvPrecent);

        }
    }
}
