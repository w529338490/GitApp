package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.MarqueeTextView;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.entity.Result;

import java.util.List;

/**
 * Created by k9579 on 2017/3/28.
 */

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    LayoutInflater inflater;
    List<String> list;

    RecyclerView parent;
    List<Result.ResultBean.DataBean> data;
    final int NOFOOT = 1;
    final int YESFOOT = 2;

    public OnItemClickListener itemClickListener = null;

    public StoryRecyclerViewAdapter(Context context, List<Result.ResultBean.DataBean> data)
    {
        this.context = context;
        this.data = data;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyHolder)
        {
            ((MyHolder) holder).tittle.setText(data.get(position).getTitle());
            ((MyHolder) holder).content.setText(data.get(position).getTitle());
            ((MyHolder) holder).score.setText(data.get(position).getTitle());
            ((MyHolder) holder).author.setText(data.get(position).getDate());


            ((MyHolder) holder).view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ToastUtil.show("点到了我");
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
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        MarqueeTextView tittle;
        TextView content;
        TextView score;
        TextView author;
        LinearLayout view;

        public MyHolder(View itemView)
        {
            super(itemView);
            tittle = (MarqueeTextView) itemView.findViewById(R.id.tittle);
            content = (TextView) itemView.findViewById(R.id.content);
            score = (TextView) itemView.findViewById(R.id.score);
            author = (TextView) itemView.findViewById(R.id.author);
            view = (LinearLayout) itemView.findViewById(R.id.cardview);
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
