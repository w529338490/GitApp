package com.duanzi.adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duanzi.Utill.GlideRoundTransform;
import com.duanzi.entity.Result;
import com.duanzi.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    LayoutInflater inflaters;
    List<String> list;

    RecyclerView parent;
    List<Result.ResultBean.DataBean> data;
    final int NOFOOT = 1;
    final int YESFOOT = 2;

    public OnItemClickListener listener;

    public NewsFragmentAdapter(Context context, List<Result.ResultBean.DataBean> data)
    {
        this.context = context;
        this.data = data;
        inflaters = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder holder = null;
        switch (viewType)
        {
            case NOFOOT:
                holder = new MyHolder(inflaters.inflate(R.layout.news_adapter, parent, false));

                this.parent = (RecyclerView) parent;
                break;

            case YESFOOT:
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyHolder)
        {
            ((MyHolder) holder).tittle.setTextColor(context.getResources().getColor(R.color.colorAccent));
            ((MyHolder) holder).tittle.setText(data.get(position).getTitle());
            ((MyHolder) holder).date.setText(data.get(position).getDate());

            ((MyHolder) holder).view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                }
            });


            Glide.with(context)
                    .load(data.get(position).getThumbnail_pic_s())
                    .transform(new GlideRoundTransform(context, 20))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_mr)
                    .crossFade(1500)
                    .into(((MyHolder) holder).pic);


            ((MyHolder) holder).view.setOnClickListener(new View.OnClickListener()
            {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(final View v)
                {
                    listener.getData(position);

                }
            });


        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public int getItemViewType(int position)
    {

        return 1;

    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tittle;
        TextView date;
        ImageView pic;
        ImageView imag;
        LinearLayout view;


        public MyHolder(View itemView)
        {
            super(itemView);
            tittle = (TextView) itemView.findViewById(R.id.tittle);
            date = (TextView) itemView.findViewById(R.id.date);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            imag = (ImageView) itemView.findViewById(R.id.imag);

            view = (LinearLayout) itemView.findViewById(R.id.cardview);
        }

    }

    class MyHolder_foot extends RecyclerView.ViewHolder
    {


        public MyHolder_foot(View itemView)
        {
            super(itemView);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {

        this.listener = listener;

    }

    public interface OnItemClickListener
    {
        void getData(int position);
    }
}
