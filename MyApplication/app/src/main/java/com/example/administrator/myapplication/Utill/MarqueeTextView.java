package com.example.administrator.myapplication.Utill;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯滚动的文字
 * 
 * @author kowal
 * 
 */
public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}
