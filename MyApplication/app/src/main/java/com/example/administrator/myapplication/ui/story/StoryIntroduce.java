package com.example.administrator.myapplication.ui.story;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * 小说预览
 */
public class StoryIntroduce extends AppCompatActivity
{

    String intentUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_introduce);
        intentUri = getIntent().getStringExtra("uri");
        if (null != intentUri)
        {
            ToastUtil.show(intentUri);
            Logger.e(intentUri);
        }
    }
}
