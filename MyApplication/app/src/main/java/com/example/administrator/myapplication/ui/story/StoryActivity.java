package com.example.administrator.myapplication.ui.story;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.PaperAdapter;
import com.example.administrator.myapplication.fragment.StoryFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.administrator.myapplication.R.id.toolbar;


public class StoryActivity extends AppCompatActivity
{
    @InjectView(toolbar)
    Toolbar tb_bar;
    @InjectView(R.id.tl_layout)
    TabLayout tl_layout;
    @InjectView(R.id.vp_paper)
    ViewPager vp_paper;

    ArrayList<Fragment> fm_list = new ArrayList<>();
    private final String[] mTitles = {"奇幻", "武侠", "历史", "都市", "游戏"};
    PaperAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.inject(this);
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData()
    {
        tl_layout.setupWithViewPager(vp_paper);
        for (int i = 0; i < mTitles.length; i++)
        {
            StoryFragment sFragment = StoryFragment.newInstance(i);
            fm_list.add(sFragment);
        }
        mAdapter = new PaperAdapter(getSupportFragmentManager(), fm_list, mTitles);
        vp_paper.setAdapter(mAdapter);
        setSupportActionBar(tb_bar);
        tb_bar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
