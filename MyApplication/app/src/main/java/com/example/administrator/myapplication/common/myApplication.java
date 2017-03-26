package com.example.administrator.myapplication.common;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/2/25.
 */

public class myApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger
                .init("Myapp")               // default tag : PRETTYLOGGER or use just init()
                .hideThreadInfo()      ;       // default it is shown



    }


}
