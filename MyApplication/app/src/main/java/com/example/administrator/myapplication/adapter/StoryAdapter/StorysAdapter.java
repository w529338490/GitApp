package com.example.administrator.myapplication.adapter.StoryAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duanzi.R;
import com.duanzi.Utill.MarqueeTextView;
import com.duanzi.entity.Story;

import java.util.List;

/**
 * Created by k9579 on 2017/3/28.
 */


public class StorysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>

{

    Context context;
    LayoutInflater inflater;
    List<Story> list;

    RecyclerView parent;

    public OnItemClickListener itemClickListener = null;

    public StorysAdapter(Context context, List<Story> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyHolder holder  = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_story_adapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        ((MyHolder) holder).type.setText(list.get(position).getType());
        ((MyHolder) holder).tittle.setText(list.get(position).getTitle());
        ((MyHolder) holder).update.setText("更新时间：" + list.get(position).getUpdateTime());
        ((MyHolder) holder).content.setText("最新更新概览：" + list.get(position).getContent());
        ((MyHolder) holder).author.setText("作者：" + list.get(position).getAuthor());
        ((MyHolder) holder).score.setText("人气" + list.get(position).getHot());

        if (itemClickListener != null)
        {
            final int pos = holder.getLayoutPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onItemClick(holder.itemView, list.get(pos));
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return 1;
    }

    @Override
    public int getItemCount()
    {
        return list == null ? 0 : list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        TextView type;
        MarqueeTextView tittle;
        TextView update;
        MarqueeTextView content;
        TextView author;
        TextView score;

        public MyHolder(View itemView)
        {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.story_type);
            tittle = (MarqueeTextView) itemView.findViewById(R.id.story_tittle);
            update = (TextView) itemView.findViewById(R.id.story_update);
            content = (MarqueeTextView) itemView.findViewById(R.id.story_content);
            author = (TextView) itemView.findViewById(R.id.story_author);
            score = (TextView) itemView.findViewById(R.id.story_score);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, Story story);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.itemClickListener = listener;
    }
}