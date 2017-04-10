package com.example.administrator.myapplication.adapter.GankAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.RandomData;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.Holder>
{
    List<RandomData.Gank> results;
    Context context;
    LayoutInflater inflater;
    OnImageViewLisnter lisnter;  //点击图片监听回调
    OnItemViewClickLisnter onItemViewClickLisnter;  //点击每个item监听事件


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
    public void onBindViewHolder(final GankAdapter.Holder holder, final int position)
    {
        Glide.with(context)
                .load(results.get(position).getUrl())
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .crossFade(1500)
                .into(holder.img)
                .getSize(new SizeReadyCallback()
                {
                    @Override
                    public void onSizeReady(int width, int height)
                    {
                        holder.parent.setVisibility(View.VISIBLE);
                    }
                });
        ;
        holder.desc.setText(results.get(position).getDesc());

       // 点击图片跳转IamgeActivity
        holder.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lisnter.getImgPath(results.get(position).getUrl(),view);

            }
        });
        //点击item监听，跳转页面
        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
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

        ImageView img;
        TextView desc;
        TextView date;
        View parent;

        public Holder(View view)
        {
            super(view);
            img= (ImageView) view.findViewById(R.id.img);
            desc= (TextView) view.findViewById(R.id.desc);
            date= (TextView) view.findViewById(R.id.date);
            parent=view;

        }
    }

    public  void setOnImageViewLisnter (OnImageViewLisnter lisnter)
    {
        this.lisnter=lisnter;
    }
    public  void setOnItemViewClickLisnter (OnItemViewClickLisnter lisnter)
    {
        this.onItemViewClickLisnter=lisnter;
    }
    public interface OnImageViewLisnter
    {
        void getImgPath(String url,View v);
    }
    public interface OnItemViewClickLisnter
    {
        void getItemViewPosition(int Postion);
    }
}
