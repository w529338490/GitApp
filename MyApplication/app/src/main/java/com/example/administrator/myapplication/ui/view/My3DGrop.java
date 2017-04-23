package com.example.administrator.myapplication.ui.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/4.
 */

public class My3DGrop extends ViewGroup implements GestureDetector.OnGestureListener
{
    private Context context;
    private Scroller scroller;
    private boolean scrolling = false; //拦截子类的滑动事件，不拦截子类的点击事件
    private Camera mCamera;
    private Matrix mMatrix;
    private float mAngle = 90;//两个item间的夹角

    //定义手势检测器实例
    GestureDetector detector;

    //在滑动菜单的时候，有时需要快速的滑动条件下，才显示菜单
    private VelocityTracker mVelocityTracker = null;

    public My3DGrop(Context context)
    {
        super(context);
        Log.e("context", "================");
    }

    public My3DGrop(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        scroller = new Scroller(context);
        mCamera = new Camera();
        mMatrix = new Matrix();
        detector = new GestureDetector(context, this);
        Log.e("attrs", "================");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

    }

    protected void onLayout(boolean changed, int l, int i1, int i2, int i3)
    {


        Log.e("onLayout", "=================");
        //初始化一些变量
        int count = getChildCount();
        int width = 0;
        int height = 0;
        int hahha = 0;
        //  View view = getChildAt(0);

        //循环子View,并定义他们的位置
        for (int i = 0; i < count; i++)
        {
            View childView = getChildAt(i);
            width = childView.getMeasuredWidth();
            height = childView.getMeasuredHeight();
            childView.layout(0, hahha, width, (i + 1) * height);
            hahha += height;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = ev.getY();
                Log.e("onInterceptTouchEvent", "=====================ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //如果滑动距离<20像素,则视为点击事件,不拦截
                if (Math.abs(mLastMotionY - ev.getY()) < 20)
                {
                    scrolling = false;
                    break;
                }
                //否则是滑动事件,拦截子类滑动
                else
                {
                    scrolling = true;
                }
                //默认,为true　拦截滑动事件
                scrolling = true;
                Log.e("onInterceptTouchEvent", "=====================ACTION_MOVE");

                break;

        }
        return scrolling;
    }

    float mLastMotionY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {


        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                scrolling = false;
                break;

            case MotionEvent.ACTION_MOVE:

                int detaY = (int) (mLastMotionY - ev.getY());
                scrollBy(0, detaY);
                mLastMotionY = ev.getY();

                //向下 滑动
                if (getScrollY() > getChildAt(0).getMeasuredHeight())
                {

                    int childCount = getChildCount();
                    View view = getChildAt(0);
                    removeViewAt(0);
                    addView(view, childCount - 1);
                    scrollBy(0, -getChildAt(0).getMeasuredHeight());

                }
                //向上滑动
                if (getScrollY() < 0)
                {
                    int childCount = getChildCount();
                    View view = getChildAt(childCount - 1);
                    removeViewAt(childCount - 1);
                    addView(view, 0);
                    scrollBy(0, getChildAt(0).getMeasuredHeight());

                }
                break;
            case MotionEvent.ACTION_UP:
                scrolling = false;
                break;
        }

        return true;
    }


    @Override
    public void computeScroll()
    {

        super.computeScroll();
        if (scroller.computeScrollOffset())
        {
            scrollTo(0, scroller.getCurrY());
            //view一旦重绘就会回调onDraw，onDraw中又会调用computeScroll，不停绘制
            postInvalidate();
            Log.e(">>>>>", "====================" + scroller.getCurrX());

        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {

        for (int i = 0; i < getChildCount(); i++)
        {
            drawScreen(canvas, i, getDrawingTime());
        }

    }

    private void drawScreen(Canvas canvas, int i, long drawingTime)
    {
        int mHeight = getMeasuredHeight();
        int mWidth = getMeasuredWidth();

        int curScreenY = getMeasuredHeight() * i;
        //屏幕中不显示的部分不进行绘制
        if (getScrollY() + mHeight < curScreenY)
        {
            return;
        }
        if (curScreenY < getScrollY() - mHeight)
        {
            return;
        }
        float centerX = mWidth / 2;
        float centerY = (getScrollY() > curScreenY) ? curScreenY + mHeight : curScreenY;
        float degree = mAngle * (getScrollY() - curScreenY) / mHeight;
        if (degree > 90 || degree < -90)
        {
            return;
        }
        canvas.save();

        mCamera.save();
        mCamera.rotateX(degree);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        canvas.concat(mMatrix);
        drawChild(canvas, getChildAt(i), drawingTime);
        canvas.restore();

    }


    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {

        Log.e(">>>>>", "====================" + scroller.getCurrX());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        Log.e(">>onFling>>>", "====================" + scroller.getCurrX());
        float minMove = 120;         //最小滑动距离
        float minVelocity = 0;      //最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity)
        {   //左滑
            Toast.makeText(context, velocityX + "左滑", Toast.LENGTH_SHORT).show();
        } else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity)
        {   //右滑
            Toast.makeText(context, velocityX + "右滑", Toast.LENGTH_SHORT).show();
        } else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity)
        {   //上滑
            Toast.makeText(context, velocityX + "上滑", Toast.LENGTH_SHORT).show();
        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity)
        {   //下滑
            Toast.makeText(context, velocityX + "下滑", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
