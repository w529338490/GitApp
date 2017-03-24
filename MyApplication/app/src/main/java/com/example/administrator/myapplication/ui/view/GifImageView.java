package com.example.administrator.myapplication.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 让图片看起来像是动的视频一样
 * 自定义一个ImageView
 */
public class GifImageView extends ImageView implements Animation.AnimationListener
{
    private boolean scale = false;
    public GifImageView(Context context)
    {
        super(context,null);
    }

    public GifImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs,0);
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
        anim.setDuration(10987);
        anim.addListener((Animator.AnimatorListener) this);
        anim.start();
        scale =!scale;


    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {

    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }
}
