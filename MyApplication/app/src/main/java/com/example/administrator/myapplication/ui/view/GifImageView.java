package com.example.administrator.myapplication.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

/**
 * 让图片看起来像是动的视频一样
 * 自定义一个ImageView
 */
public class GifImageView extends ImageView implements Animator.AnimatorListener
{
    private boolean scale = false;


    public GifImageView(Context context)
    {
        this(context, null);
    }


    public GifImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }


    public GifImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        nextAnimation();
    }


    private void nextAnimation()
    {
        AnimatorSet anim = new AnimatorSet();
        if (scale)
        {
            anim.playTogether(ObjectAnimator.ofFloat(this, "scaleX", 1.5f, 1f),
                    ObjectAnimator.ofFloat(this, "scaleY", 1.5f, 1f));
        } else
        {
            anim.playTogether(ObjectAnimator.ofFloat(this, "scaleX", 1, 1.5f),
                    ObjectAnimator.ofFloat(this, "scaleY", 1, 1.5f));
        }
        anim.setDuration(10989);
        anim.addListener(this);
        anim.start();
        scale = !scale;
    }


    @Override
    public void onAnimationStart(Animator animator)
    {

    }

    @Override
    public void onAnimationEnd(Animator animator)
    {
        nextAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animator)
    {

    }

    @Override
    public void onAnimationRepeat(Animator animator)
    {

    }
}
