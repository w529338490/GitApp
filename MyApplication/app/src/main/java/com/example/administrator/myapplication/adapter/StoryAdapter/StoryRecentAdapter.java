package com.example.administrator.myapplication.adapter.StoryAdapter;

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
import com.example.administrator.myapplication.entity.RecentStory;

import java.util.List;


/**
 * Created by k9579 on 2017/6/18.
 */

public class StoryRecentAdapter extends RecyclerView.Adapter<StoryRecentAdapter.ViewHolder>
{
    private Activity activity;
    private List<RecentStory> recentStoryList;
    LayoutInflater inflater;
    public OnItemClickListener itemClickListener = null;

    public StoryRecentAdapter(Activity context, List<RecentStory> list)
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
    public void onBindViewHolder(final ViewHolder holder, int position)
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
        if (itemClickListener != null)
        {
            final int pos = holder.getLayoutPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onItemClick(holder.itemView, recentStoryList.get(pos).getUrl());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return recentStoryList.size() == 0 ? recentStoryList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_pic;
        TextView tv_title;

        public ViewHolder(View itemView)
        {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, String uri);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.itemClickListener = listener;
    }

}
