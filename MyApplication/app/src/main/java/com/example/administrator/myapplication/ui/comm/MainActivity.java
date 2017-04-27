package com.example.administrator.myapplication.ui.comm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.PaperAdapter;
import com.example.administrator.myapplication.fragment.NewsFragment;
import com.example.administrator.myapplication.ui.Fan.FanActivity;
import com.example.administrator.myapplication.ui.UserActivity.UserActivity;
import com.example.administrator.myapplication.ui.article.ArtcleActivity;
import com.example.administrator.myapplication.ui.gank.GankActivity;
import com.example.administrator.myapplication.ui.music.MusicActivity;
import com.example.administrator.myapplication.ui.story.StoryActivity;
import com.example.administrator.myapplication.ui.video.VideoActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @InjectView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.bar)
    Toolbar bar;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.paper)
    ViewPager pager;
    PaperAdapter adapter;

    @InjectView(R.id.nv)
    NavigationView nv;
    ArrayList<Fragment> list = new ArrayList<>();
    private final String[] mTitles = {"头条", "科技", "社会", "国内", "娱乐"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initData();
    }

    private void initData()
    {
        nv.setNavigationItemSelectedListener(this);
        tab.setupWithViewPager(pager);
        bar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });
        //设置Toolbar和DrawerLayout实现动画和联动
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        for (int i = 0; i < mTitles.length; i++)
        {
            NewsFragment newsf = NewsFragment.newInstance(i);
            list.add(newsf);

        }
        adapter = new PaperAdapter(getSupportFragmentManager(), list, mTitles);
        pager.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.nav_user:
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_video:
                Intent intent2 = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent2);
                break;
            case R.id.gank:
                Intent intentGandk = new Intent(MainActivity.this, GankActivity.class);
                startActivity(intentGandk);
                break;
            case R.id.nav_story:
                startActivity(new Intent(MainActivity.this, StoryActivity.class));
                break;
            case R.id.nav_music:
                startActivity(new Intent(MainActivity.this, MusicActivity.class));
                break;
            case R.id.nav_artcle:
                startActivity(new Intent(MainActivity.this, ArtcleActivity.class));
                break;
            case R.id.nav_fan:

                startActivity(new Intent(MainActivity.this, FanActivity.class));
                break;
        }
        return true;
    }

    long nowTime;

    @Override
    public void onBackPressed()

    {
        if (mDrawerLayout.isDrawerOpen(nv))
        {
            mDrawerLayout.closeDrawer(nv);
            return;
        }

        if (System.currentTimeMillis() - nowTime > 2000)
        {
            nowTime = System.currentTimeMillis();
            Snackbar snackbar = Snackbar.make(mDrawerLayout, "再按一次返回键退出程序", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        } else
        {
            finish();
        }
    }


}
