package com.duanzi.Utill;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/2/24.
 */

public class MyVideoView extends VideoView
{


    public MyVideoView(Context context)
    {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);
        int heigMode = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heigMode == MeasureSpec.EXACTLY)
        {
            setMeasuredDimension(widthSize, heigthSize);
        } else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
