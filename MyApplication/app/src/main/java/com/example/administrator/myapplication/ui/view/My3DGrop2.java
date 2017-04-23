package com.example.administrator.myapplication.ui.view;

import android.animation.ValueAnimator;
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
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */

public class My3DGrop2 extends LinearLayout implements GestureDetector.OnGestureListener
{
    //定义手势检测器实例
    GestureDetector detector;
    float mLastMotionY = 0;
    float mLastMotionX = 0;
    private Context context;
    private Scroller scroller;
    private boolean scrolling = false; //拦截子类的滑动事件，不拦截子类的点击事件
    private Camera mCamera;
    private Matrix mMatrix;
    private float mAngle = 90;//两个item间的夹角
    private static final int standerSpeed = 2000;
    //在滑动菜单的时候，有时需要快速的滑动条件下，才显示菜单
    private VelocityTracker mVelocityTracker = null;

    boolean up_down = false, left_right = false;
    boolean completed = false;
    int leftOrientation = 0;
    int updownOrientation = 1;
    private List<View> list = new ArrayList<>();

    public My3DGrop2(Context context)
    {
        super(context);
    }


    public My3DGrop2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        scroller = new Scroller(context);
        mCamera = new Camera();
        mMatrix = new Matrix();
        detector = new GestureDetector(context, this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
    }

    //    protected void onLayout(boolean changed, int l, int i1, int i2, int i3)
//    {
//
//
//        Log.e("onLayout", "=================");
//        //初始化一些变量
//        int count = getChildCount();
//        int width = 0;
//        int height = 0;
//        int hahha = 0;
//        //  View view = getChildAt(0);
//
//        //循环子View,并定义他们的位置
////        for (int i = 0; i < count; i++)
////        {
////            View childView = getChildAt(i);
////            width = childView.getMeasuredWidth();
////            height = childView.getMeasuredHeight();
////            childView.layout(0, hahha, width, (i + 1) * height);
////            hahha += height;
////            list.add(childView);
////        }
//
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = ev.getY();
                mLastMotionX = ev.getX();
                Log.e("onInterceptTouchEvent", "=====================ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //如果滑动距离<20像素,则视为点击事件,不拦截
                if (Math.abs(mLastMotionY - ev.getY()) < 20 ||
                        Math.abs(mLastMotionX - ev.getX()) < 10)
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

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        detector.onTouchEvent(ev);
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                scrolling = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!scroller.isFinished())
                {
                    // 终止滑动
                    scroller.abortAnimation();
                }
                int detaY = (int) (mLastMotionY - ev.getY());
                int detaX = (int) (mLastMotionX - ev.getX());
                if (up_down)
                {
                    upAnddowm(detaY, ev);
                }
                if (left_right)
                {
                    leftAndRight(detaX, ev);
                }

                break;
            case MotionEvent.ACTION_UP:

                if (up_down)
                {
                    int distance = getScrollY();
                    if (distance > 0)
                    { // 向上滑动
                        if (distance < getMeasuredHeight() / 3)
                        {

                            // 回到原来位置
                            scroller.startScroll(0, getScrollY(), 0, -distance);
                        } else
                        {
                            // 滚到屏幕的剩余位置
                            scroller.startScroll(0, getScrollY(), 0, getMeasuredHeight() - distance);
                        }
                    } else
                    {             // 向下滑动
                        if (-distance < getMeasuredHeight() / 3)
                        {
                            scroller.startScroll(0, getScrollY(), 0, -distance);
                        } else
                        {
                            scroller.startScroll(0, getScrollY(), 0, -getMeasuredHeight() - distance);
                        }
                    }
                }
                if (left_right)
                {
                    int distance = getScrollX();
                    if (distance > 0)
                    { // 向上滑动
                        if (distance < getMeasuredWidth() / 3)
                        {

                            // 回到原来位置
                            scroller.startScroll(getScrollX(), 0, -distance, 0);
                        } else
                        {
                            // 滚到屏幕的剩余位置
                            scroller.startScroll(getScrollX(), 0, getMeasuredWidth() - distance, 0);
                        }
                    } else
                    {             // 向下滑动
                        if (-distance < getMeasuredWidth() / 3)
                        {
                            scroller.startScroll(getScrollX(), 0, -distance, 0);
                        } else
                        {
                            scroller.startScroll(getScrollX(), 0, -getMeasuredWidth() - distance, 0);
                        }
                    }

                }
                postInvalidate();
                scrolling = false;
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll()
    {

        if (scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
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
        float degree = 0;
        float centerX = 0;
        float centerY = 0;

        int curScreenX = getMeasuredWidth() * i;
        int curScreenY = getMeasuredHeight() * i;

        if (up_down)
        {
            // 屏幕中不显示的部分不进行绘制
            if (getScrollY() + mHeight < curScreenY)
            {
                return;
            }
            if (curScreenY < getScrollY() - mHeight)
            {
                return;
            }
            centerX = mWidth / 2;

            if (getScrollY() > curScreenY)
            {
                centerY = curScreenY + mHeight;
            } else
            {
                centerY = curScreenY;
            }
            degree = mAngle * (getScrollY() - curScreenY) / mHeight;
        }
        if (left_right)
        {
            // 屏幕中不显示的部分不进行绘制
            if (getScrollX() + mWidth < curScreenX)
            {
                return;
            }
            if (curScreenX < getScrollX() - mWidth)
            {
                return;
            }
            centerY = mHeight / 2;

            if (getScrollX() > curScreenX)
            {
                centerX = curScreenX + mWidth;
            } else
            {
                centerX = curScreenX;
            }
            degree = (-mAngle) * (getScrollX() - curScreenX) / mWidth;

        }

        if (degree > 90 || degree < -90)
        {
            return;
        }
        canvas.save();
        mCamera.save();
        if (up_down)
        {
            mCamera.rotateX(degree);

        } else
        {
            mCamera.rotateY(degree);

        }
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
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        float minMove = 40;         //最小滑动距离
        float minVelocity = 40;      //最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        int detaY = (int) (mLastMotionY - e2.getY());
        int detaX = (int) (mLastMotionX - e2.getX());

        if (Math.abs(beginX - endX) > Math.abs(beginY - endY))
        {
            leftOrientation = getOrientation();
            if (leftOrientation != 0)
            {
                mLastMotionX = e2.getX();
                list.clear();
                for (int b = 0; b < getChildCount(); b++)
                {
                    list.add(getChildAt(b));

                }
                setOrientation(HORIZONTAL);


                removeAllViews();
                for (int i = 0; i < list.size(); i++)
                {
                    addView(list.get(i));

                }

                ValueAnimator valueAnimator;
                valueAnimator = ValueAnimator.ofInt(0, 21);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        int dey = (int) valueAnimator.getAnimatedValue();
                        scrollBy(dey, 0);
                    }
                });
                valueAnimator.start();
                //   postInvalidateDelayed(getDrawingTime());
            }

            up_down = false;
            left_right = true;
            leftAndRight(detaX, e2);

        } else
        {
            updownOrientation = getOrientation();
            if (updownOrientation != 1)
            {
                mLastMotionY = e2.getY();
                list.clear();
                for (int b = 0; b < getChildCount(); b++)
                {
                    list.add(getChildAt(b));

                }
                //  up_down=false;
                setOrientation(VERTICAL);

                removeAllViews();

                for (int i = 0; i < list.size(); i++)
                {

                    addView(list.get(i));

                }

                ValueAnimator valueAnimator;
                valueAnimator = ValueAnimator.ofInt(0, 21);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        int dey = (int) valueAnimator.getAnimatedValue();
                        scrollBy(0, (int) dey);
                    }
                });
                valueAnimator.start();
                //  scrollBy(0, 2*(int) (mLastMotionY +e2.getY()));

                //   postInvalidateDelayed(getDrawingTime());
            }

            up_down = true;
            left_right = false;
            upAnddowm(detaY, e2);
        }

        if (Math.abs(beginX - endX) > minMove && Math.abs(velocityX) > minVelocity)
        {   //左右滑
            //   Toast.makeText(context, velocityX + "左滑", Toast.LENGTH_SHORT).show();

            setOrientation(HORIZONTAL);
            up_down = false;
            left_right = true;
        } else if (Math.abs(beginY - endY) > minMove && Math.abs(velocityY) > minVelocity)
        {//上 下滑

            setOrientation(VERTICAL);
            Log.e("drawingtime", "=================" + getDrawingTime());
            up_down = true;
            left_right = false;
            //    Toast.makeText(context, velocityX + "上滑", Toast.LENGTH_SHORT).show();
        } else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity)
        {   //右滑
            //    Toast.makeText(context, velocityX + "右滑", Toast.LENGTH_SHORT).show();
            setOrientation(HORIZONTAL);

            up_down = false;
            left_right = true;
        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity)
        {   //下滑
            //    Toast.makeText(context, velocityX + "下滑", Toast.LENGTH_SHORT).show();
            setOrientation(VERTICAL);
            up_down = true;
            left_right = false;
        }

        return false;
    }

    public void upAnddowm(int detaY, MotionEvent ev)
    {
        if (up_down)
        {
            Log.e("up_down", "============================");
            setOrientation(VERTICAL);
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
        }


    }

    public void leftAndRight(int detaX, MotionEvent ev)
    {
        if (left_right)
        {
            scrollBy(detaX, 0);
            mLastMotionX = ev.getX();

            //向左 滑动
            if (getScrollX() > getChildAt(0).getMeasuredWidth())
            {
                int childCount = getChildCount();
                View view = getChildAt(0);
                removeViewAt(0);
                addView(view, childCount - 1);
                scrollBy(-getChildAt(0).getMeasuredWidth(), 0);

            }
            //向右滑动
            if (getScrollX() < 0)
            {
                int childCount = getChildCount();
                View view = getChildAt(childCount - 1);
                removeViewAt(childCount - 1);
                addView(view, 0);
                scrollBy(getChildAt(0).getMeasuredWidth(), 0);

            }

        }

    }
}
