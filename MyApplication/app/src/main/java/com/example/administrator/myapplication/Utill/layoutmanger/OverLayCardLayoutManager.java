package com.example.administrator.myapplication.Utill.layoutmanger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/29.
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager
{


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams()
    {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * @param recycler 以recycler放松回收,不显示的布局
     * @param state    指定子布局的 状态
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        //获得 子布局的个数 ，也就是 adapter 中List的size;
        int itemCount = getItemCount();
        if (itemCount < 1)
        {
            return;
        }
        int bottomPosition;
        //边界处理
        if (itemCount < CardConfig.MAX_SHOW_COUNT)
        {
            bottomPosition = 0;
        } else
        {
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        //从可见的最底层View开始layout，依次层叠上去 每一个 item 放大
        for (int position = bottomPosition; position < itemCount; position++)
        {
            //从回收堆中取出相应的子view
            View view = recycler.getViewForPosition(position);
            //将view添加进入RecyclerView,显示
            addView(view);
            //测量子布局
            measureChildWithMargins(view, 0, 0);
            //获得实际的宽 高
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            //我们在布局时，将childView居中处理，这里也可以改为只水平居中
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            //缩放比比例，从最底层开始，一层比一层大,最底层是0；最上层是itemCount；

            int level = itemCount - position - 1;
            if (level > 0)
            {
                view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                //前N层，依次向下位移和Y方向的缩小
                if (level < CardConfig.MAX_SHOW_COUNT)
                {

                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);

                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else
                {
                    //第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }
        }

    }
}
