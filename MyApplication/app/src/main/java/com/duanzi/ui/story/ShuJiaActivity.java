package com.duanzi.ui.story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.duanzi.DB.gen.StoryShuJiaDao;
import com.duanzi.R;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.adapter.StoryAdapter.StoryShuJiaAdapter;
import com.duanzi.common.myApplication;
import com.duanzi.entity.StoryShuJia;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShuJiaActivity extends AppCompatActivity
{
    @InjectView(R.id.rv_list)
    RecyclerView rv_list;
    @InjectView(R.id.tb_toolbar)
    Toolbar tb_toolbar;

    private StoryShuJiaDao dao;
    private StoryShuJiaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shujia);
        ButterKnife.inject(this);

        initWidget();
        getData();
    }

    private void initWidget()
    {
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        dao = myApplication.getShuJiaSession().getStoryShuJiaDao();
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
        List<StoryShuJia> recentStoryList = dao.queryBuilder().list();
        mAdapter = new StoryShuJiaAdapter(ShuJiaActivity.this, recentStoryList);
        rv_list.setAdapter(mAdapter);
        // 跳转阅读
        mAdapter.setOnItemClickListener(new StoryShuJiaAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, String uri)
            {
                startActivity(new Intent(ShuJiaActivity.this, StoryIntroduce.class).putExtra("uri", uri));
            }
        });

        // 删除
        mAdapter.setOnDelClickListener(new StoryShuJiaAdapter.OnDelClickListerner()
        {
            @Override
            public void onDelClick(View view, StoryShuJia recent, int position)
            {
                try
                {
                    dao.queryBuilder().where(StoryShuJiaDao.Properties.StoryName.eq(recent.getStoryName())).buildDelete().executeDeleteWithoutDetachingEntities();
                    mAdapter.remove(position);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e)
                {
                    ToastUtil.show(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

}
