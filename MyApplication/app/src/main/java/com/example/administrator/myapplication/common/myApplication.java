package com.example.administrator.myapplication.common;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/2/25.
 */

public class myApplication extends Application
{

    public static Context context;
    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.init("Myapp")               // default tag : PRETTYLOGGER or use just init()
              .hideThreadInfo();           // default it is shown

        this.context=getApplicationContext();  //获得全局 的Context;

    }


}
