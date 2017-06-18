package com.duanzi.ui.Fan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.duanzi.R;
import com.duanzi.Utill.ToastUtil;

public class FanActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView My3DGrop_v1_img;
    ImageView My3DGrop_v2_img;
    ImageView My3DGrop_v3_img;
    ImageView My3DGrop_v4_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        My3DGrop_v1_img = (ImageView) findViewById(R.id.My3DGrop_v1_img);
        My3DGrop_v2_img = (ImageView) findViewById(R.id.My3DGrop_v2_img);
        My3DGrop_v3_img = (ImageView) findViewById(R.id.My3DGrop_v3_img);
        My3DGrop_v4_img = (ImageView) findViewById(R.id.My3DGrop_v4_img);
        My3DGrop_v1_img.setOnClickListener(this);
        My3DGrop_v2_img.setOnClickListener(this);
        My3DGrop_v3_img.setOnClickListener(this);
        My3DGrop_v4_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.My3DGrop_v1_img:
                ToastUtil.show("this is img1");
                break;
            case R.id.My3DGrop_v2_img:
                ToastUtil.show("this is img2");
                break;
            case R.id.My3DGrop_v3_img:
                ToastUtil.show("this is img3");
                break;
            case R.id.My3DGrop_v4_img:
                ToastUtil.show("this is img4");
                break;


        }


    }
}
