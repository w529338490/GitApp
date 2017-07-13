package com.duanzi.adapter.StoryAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duanzi.R;
import com.duanzi.entity.Story;

import java.util.List;


/**
 * 小说目录列表
 * Created by k9579 on 2017/4/20.
 */

public class StoryCatalogsAdapter extends RecyclerView.Adapter<StoryCatalogsAdapter.ViewHolder>
{
    Context context;
    LayoutInflater inflater;
    List<Story.StoryCatalog> catalogList;

    public OnItemClickListener itemClickListener = null;

    public StoryCatalogsAdapter(Context context, List<Story.StoryCatalog> list)
    {
        this.context = context;
        this.catalogList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.item_story_catalogs,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
       holder.tv_catalogs.setText(catalogList.get(position).getCatalog());

        if (itemClickListener != null)
        {
            final int pos = holder.getLayoutPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onItemClick(holder.itemView, catalogList.get(pos).getUrl());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return catalogList == null ? 0 : catalogList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_catalogs;

        public ViewHolder(View itemView)
        {
            super(itemView);
            tv_catalogs = (TextView) itemView.findViewById(R.id.tv_catalogs);
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
