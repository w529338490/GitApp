package com.example.administrator.myapplication.Utill;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.myapplication.common.myApplication;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/3/26.
 */

public  class ToastUtil
{
    private Context context;
    public static void  show(String str)
    {

        if (str != null && str.trim().length() != 0)
        {
            Toast.makeText(myApplication.context, str, Toast.LENGTH_SHORT).show();
        } else
        {
            ToastUtil.show("Error>>:  You Showed String is Null!!");
            Logger.e("error for Str>>" + str);
        }

    }

}
