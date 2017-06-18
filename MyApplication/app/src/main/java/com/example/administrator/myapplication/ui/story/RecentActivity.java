package com.example.administrator.myapplication.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duanzi.R;
import com.duanzi.common.myApplication;
import com.duanzi.ui.story.StoryRead;
import com.example.administrator.myapplication.adapter.StoryAdapter.StoryRecentAdapter;
import com.example.administrator.myapplication.entity.RecentStory;
import com.example.administrator.myapplication.entity.gen.RecentStoryDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecentActivity extends AppCompatActivity
{

    @InjectView(R.id.rv_list)
    RecyclerView rv_list;

    private Activity activity = RecentActivity.this;
    private RecentStoryDao recent;
    private StoryRecentAdapter mAdapter;

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
    }

    private void getData()
    {
        recent = myApplication.getRecentSession().getRecentStoryDao();
        List<RecentStory> recentStoryList = recent.queryBuilder().list();
        if (recentStoryList.size() > 0)
        {
            mAdapter = new StoryRecentAdapter(activity, recentStoryList);
            rv_list.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new StoryRecentAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View view, String uri)
                {
                    startActivity(new Intent(activity, StoryRead.class).putExtra("url", uri));
                }
            });
        }


    }


}
