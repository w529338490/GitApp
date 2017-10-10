package com.duanzi.adapter.StoryAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.duanzi.R;
import com.duanzi.entity.StoryShuJia;

import java.util.List;


/**
 * /**
 * 最近阅读适配器
 * Created by k9579 on 2017/6/18.
 */

public class StoryShuJiaAdapter extends RecyclerView.Adapter<StoryShuJiaAdapter.ViewHolder>
{
    private Activity activity;
    private List<StoryShuJia> recentStoryList;
    LayoutInflater inflater;
    public OnItemClickListener itemClickListener = null;
    private OnDelClickListerner delClick = null;

    public StoryShuJiaAdapter(Activity context, List<StoryShuJia> list)
    {
        this.activity = context;
        this.recentStoryList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.item_story_recent, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        Glide.with(activity)
                .load(recentStoryList.get(position).getPic()).asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .into(holder.iv_pic)
                .getSize(new SizeReadyCallback()
                {
                    @Override
                    public void onSizeReady(int width, int height)
                    {
                        holder.itemView.setVisibility(View.VISIBLE);
                    }
                });

        holder.tv_title.setText(recentStoryList.get(position).getStoryName());

        final int pos = holder.getLayoutPosition();
        if (itemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onItemClick(holder.itemView, recentStoryList.get(pos).getUrl());
                }
            });
        }
        if (delClick != null)
        {
            holder.tv_del.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    delClick.onDelClick(holder.tv_del, recentStoryList.get(pos), position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return recentStoryList.size() == 0 ? 0 : recentStoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_pic;
        TextView tv_title, tv_del;

        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_del = (TextView) itemView.findViewById(R.id.tv_del);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, String uri);
    }

    public interface OnDelClickListerner
    {
        void onDelClick(View view, StoryShuJia recent, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.itemClickListener = listener;
    }

    public void setOnDelClickListener(OnDelClickListerner onDelClick)
    {
        this.delClick = onDelClick;
    }

    public void remove(int position)
    {
        recentStoryList.remove(position);
    }
}
