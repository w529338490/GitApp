package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.MarqueeTextView;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.entity.Story;

import java.util.List;

/**
 * Created by k9579 on 2017/3/28.
 */

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    LayoutInflater inflater;
    List<Story> list;

    RecyclerView parent;
    final int NOFOOT = 1;
    final int YESFOOT = 2;

    public OnItemClickListener itemClickListener = null;

    public StoryRecyclerViewAdapter(Context context, List<Story> list)
    {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder holder = null;
        switch (viewType)
        {
            case NOFOOT:
                holder = new MyHolder(inflater.inflate(R.layout.item_story_adapter, parent, false));
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
            ((MyHolder) holder).type.setText(list.get(position).getType());
            ((MyHolder) holder).tittle.setText(list.get(position).getTitle());
            ((MyHolder) holder).update.setText("更新时间："+list.get(position).getUpdateTime());
            ((MyHolder) holder).content.setText("最新更新概览："+list.get(position).getContent());
            ((MyHolder) holder).author.setText("作者："+list.get(position).getAuthor());
            ((MyHolder) holder).score.setText("人气"+list.get(position).getHot());

            ((MyHolder) holder).itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ToastUtil.show(list.get(position).getUri());
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
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.itemClickListener=listener;
    }
}
