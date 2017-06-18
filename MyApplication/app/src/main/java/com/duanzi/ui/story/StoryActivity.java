package com.duanzi.ui.story;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.duanzi.R;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.adapter.PaperAdapter;
import com.duanzi.fragment.StoryFragment;
import com.example.administrator.myapplication.ui.story.RecentActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.duanzi.R.id.toolbar;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mune_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_shujia:
                ToastUtil.show("暂时不给你看书架");
                return true;
            case R.id.item_recent:
                startActivity(new Intent(StoryActivity.this, RecentActivity.class));
                return true;
            case R.id.item_setting:
                ToastUtil.show("暂时不让你设置");
//                startActivity(new Intent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
