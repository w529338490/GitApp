package com.example.administrator.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.administrator.myapplication.adapter.PaperAdapter;
import com.example.administrator.myapplication.fragment.GifFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GifActivity extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout tab;
    ViewPager pager;

    PaperAdapter adapter;


    ArrayList<Fragment> list = new ArrayList<>();
    String str[] = new String[]{"搞笑GIF", "邪恶GIF", "搞笑图片"};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tab= (TabLayout) findViewById(R.id.tab);
        pager= (ViewPager) findViewById(R.id.paper);

         init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tab.setupWithViewPager(pager);
        for(int i=0;i<str.length;i++)
        {

            GifFragment g=GifFragment.newInstance(i);
            list.add(g);
        }
        adapter=new PaperAdapter(getSupportFragmentManager(),list,str);
        pager.setAdapter(adapter);
    }
}
