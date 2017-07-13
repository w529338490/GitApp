package com.duanzi.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.duanzi.DB.gen.RecentStoryDao;
import com.duanzi.R;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.adapter.StoryAdapter.StoryRecentAdapter;
import com.duanzi.common.myApplication;
import com.duanzi.entity.RecentStory;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 最近浏览
 */
public class RecentActivity extends AppCompatActivity
{

    @InjectView(R.id.rv_list)
    RecyclerView rv_list;
    @InjectView(R.id.tb_toolbar)
    Toolbar tb_toolbar;

    private Activity activity = RecentActivity.this;
    private RecentStoryDao recent;
    private StoryRecentAdapter mAdapter;
    private RecentStoryDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        ButterKnife.inject(this);

        initWidget();
        getData();
    }

    private void initWidget()
    {
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(activity));

        dao = myApplication.getRecentSession().getRecentStoryDao();

        setSupportActionBar(tb_toolbar);
        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void getData()
    {
        recent = myApplication.getRecentSession().getRecentStoryDao();
        List<RecentStory> recentStoryList = recent.queryBuilder().list();
        mAdapter = new StoryRecentAdapter(activity, recentStoryList);
        rv_list.setAdapter(mAdapter);
        // 跳转阅读
        mAdapter.setOnItemClickListener(new StoryRecentAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, String uri)
            {
                startActivity(new Intent(activity, StoryIntroduce.class).putExtra("uri", uri));
            }
        });

        // 删除
        mAdapter.setOnDelClickListener(new StoryRecentAdapter.OnDelClickListerner()
        {
            @Override
            public void onDelClick(View view, RecentStory recent, int position)
            {
                try
                {
                    dao.queryBuilder().where(RecentStoryDao.Properties.StoryName.eq(recent.getStoryName())).buildDelete().executeDeleteWithoutDetachingEntities();
                    mAdapter.remove(position);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e)
                {
                    ToastUtil.show(e.getMessage());
                }
            }
        });
    }


}
