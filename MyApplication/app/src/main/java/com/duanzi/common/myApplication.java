package com.duanzi.common;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/2/25.
 */

public class myApplication extends Application
{


    /**
     * 微信账号
     */
    public static final String WECHAI_APP_ID = "wx69e49555a65406a3";
    public static final String WECHAT_AppSecret = "0a3afa56b65e236dcd0d88735e38f3f7";

    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.init("Myapp")               // default tag : PRETTYLOGGER or use just init()
                .hideThreadInfo();           // default it is shown

        this.context = getApplicationContext();  //获得全局 的Context;

    }


}
