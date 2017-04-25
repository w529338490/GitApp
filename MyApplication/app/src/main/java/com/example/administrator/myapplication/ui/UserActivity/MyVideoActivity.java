package com.example.administrator.myapplication.ui.UserActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MyVideoViewAdapter;
import com.example.administrator.myapplication.DB.DbBean.VideoBean;

import java.util.List;

public class MyVideoActivity extends AppCompatActivity
{
    RecyclerView recyview;
    List<VideoBean> myVideos;
    MyVideoViewAdapter adpter;
    LinearLayoutManager manger;
    ItemTouchHelper helper;   //实现recyview拖拽动画

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        manger=new LinearLayoutManager(this);
        myVideos= (List<VideoBean>) this.getIntent().getSerializableExtra("myvideo");
        init();
    }

    private void init()
    {
        recyview= (RecyclerView) findViewById(R.id.recyview);
        adpter=new MyVideoViewAdapter(myVideos,this);
        recyview.setAdapter(adpter);
        recyview.setLayoutManager(manger);

        //实现recyview拖拽
        helper = new ItemTouchHelper(new ItemTouchHelper.Callback()
        {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                /**  //判断LayoutManager的类型 线性还是GridView类型
                 * @param recyclerView
                 * @param viewHolder 拖动的ViewHolder
                 * @param target 目标位置的ViewHolder
                 * @return
                 */

                int dragFlags;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager)
                {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else
                {
                    dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);

            }

            //拖拽效果
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                //更新集合位置，从新排布顺序
                //  Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adpter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;

            }

            //滑动效果，在LinerLaout时候调用
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                /**
                 * @param viewHolder 滑动的ViewHolder
                 * @param direction 滑动的方向
                 */

            }

            @Override
            public boolean isLongPressDragEnabled()
            {
                // 不需要长按拖拽功能  我们手动控制
                return true;
            }
        });
        helper.attachToRecyclerView(recyview);



    }

}
