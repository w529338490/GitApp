package com.example.administrator.myapplication.Utill;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.myapplication.common.myApplication;

/**
 * Created by Administrator on 2017/3/26.
 */

public  class ToastUtil
{
    private Context context;
    public static void  show(String str)
    {

        Toast.makeText(myApplication.context,str,Toast.LENGTH_SHORT).show();
    }

}
