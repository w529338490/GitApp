package com.example.administrator.myapplication.adapter;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.CircleTransform;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.entity.Result;

import java.security.spec.PSSParameterSpec;
import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class News_fragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    LayoutInflater inflaters;
    List<String> list;

    List<Result.ResultBean.DataBean> data;
    final int NOFOOT = 1;
    final int YESFOOT = 2;

    public OnItemClickListener listener;

    public News_fragmentAdapter(Context context, List<Result.ResultBean.DataBean> data) {
        this.context = context;
        this.data = data;
        inflaters=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType)
        {
            case NOFOOT:
                holder=new MyHolder(inflaters.inflate(R.layout.news_adapter,parent,false));
                break;

            case  YESFOOT:
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder)
        {
            ((MyHolder) holder).tittle.setTextColor(context.getResources().getColor(R.color.colorAccent));
            ((MyHolder) holder).tittle.setText(data.get(position).getTitle());
            ((MyHolder) holder).date.setText(data.get(position).getDate());


            Glide.with(context)
                    .load(data.get(position).getThumbnail_pic_s())
                    .transform(new GlideRoundTransform(context,20))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_mr)
                    .crossFade(1500)
                    .into(((MyHolder) holder).pic);


            ((MyHolder) holder).view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)

                {
                    listener.getData(position);

                }
            });

        }
    }

    @Override
    public int getItemCount() {
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
        View view;


        public MyHolder(View itemView) {
            super(itemView);
            tittle= (TextView) itemView.findViewById(R.id.tittle);
            date= (TextView) itemView.findViewById(R.id.date);
            pic= (ImageView) itemView.findViewById(R.id.pic);

            view=itemView;
        }

    }
    class MyHolder_foot extends RecyclerView.ViewHolder {


        public MyHolder_foot(View itemView) {
            super(itemView);

        }
    }

    public  void setOnItemClickListener(OnItemClickListener listener)
    {

        this.listener=listener;

    }
    public interface  OnItemClickListener
    {
        void getData(int position);
    }
}
