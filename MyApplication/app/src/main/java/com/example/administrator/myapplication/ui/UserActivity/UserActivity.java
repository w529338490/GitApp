package com.example.administrator.myapplication.ui.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.DB.DbDao.VideoDao;
import com.trello.rxlifecycle.components.RxActivity;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserActivity extends RxActivity implements View.OnClickListener
{
    RecyclerView recyview;

    @InjectView(R.id.myvideo)
    LinearLayout myvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        recyview= (RecyclerView) findViewById(R.id.recyview);
        myvideo.setOnClickListener(this);
        testList();

    }
    public void testList()
    {
        VideoDao dao=new VideoDao(UserActivity.this);
      //  dao.deleAll();

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.myvideo:
                ToastUtil.show("aaa");
                VideoDao dao=new VideoDao(UserActivity.this);
                dao.getLiveItemBeans();
                Intent intent=new Intent(UserActivity.this,MyVideoActivity.class);
                intent.putExtra("myvideo", (Serializable) dao.getLiveItemBeans());
                startActivity(intent);
                break;

        }
    }
}
