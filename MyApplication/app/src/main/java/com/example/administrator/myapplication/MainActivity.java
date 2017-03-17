package com.example.administrator.myapplication;

import android.content.Intent;
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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.adapter.PaperAdapter;
import com.example.administrator.myapplication.fragment.GifFragment;
import com.example.administrator.myapplication.fragment.NewsFragment;

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
    private final String[] mTitles = {"头条","科技", "社会", "国内", "娱乐"};
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
        for(int i=0;i<mTitles.length;i++)
        {
            NewsFragment newsf= NewsFragment.newInstance(i);
               list.add(newsf);

        }
        adapter=new PaperAdapter(getSupportFragmentManager(),list,mTitles);
        pager.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_news:
                Toast.makeText(MainActivity.this,"nav_news",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_gif:

                Intent intent=new Intent(MainActivity.this, GifActivity.class);
                startActivity(intent);
                break;
            case  R.id.nav_video:
                Intent intent2=new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

    long nowTime;
    @Override
    public void onBackPressed()

    {
        if(mDrawerLayout.isDrawerOpen(nv))
        {
            mDrawerLayout.closeDrawer(nv);
            return;
        }

        if(System.currentTimeMillis()-nowTime>2000)
        {
            nowTime=System.currentTimeMillis();
            Snackbar snackbar = Snackbar.make(mDrawerLayout, "再按一次返回键退出程序", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }else {
            finish();
        }
    }


}
