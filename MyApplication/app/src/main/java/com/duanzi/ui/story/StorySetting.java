package com.duanzi.ui.story;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.duanzi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StorySetting extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.rg_txSize)
    RadioGroup rg_txSize;
    @InjectView(R.id.rg_txColor)
    RadioGroup rg_txColor;
    @InjectView(R.id.rg_txBg)
    RadioGroup rg_txBg;
    @InjectView(R.id.rg_txPadding)
    RadioGroup rg_txPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_setting);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        switch (group.getId())
        {
            case R.id.rg_txSize:
                switch (checkedId)
                {
                    case R.id.rb_big:
                        break;
                    case R.id.rb_small:
                        break;
                }
                break;
            case R.id.rg_txColor:
                switch (checkedId)
                {
                    case R.id.rg_txColor:
                        break;
                    case R.id.rb_txlight:
                        break;
                }
                break;
            case R.id.rg_txBg:
                switch (checkedId)
                {
                    case R.id.rb_bglight:
                        break;
                    case R.id.rb_bgdark:
                        break;
                }
                break;
            case R.id.rg_txPadding:
                switch (checkedId)
                {
                    case R.id.rb_pdbig:
                        break;
                    case R.id.rb_pdsmall:
                        break;
                }
                break;
        }
    }
}
