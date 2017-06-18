package com.example.administrator.myapplication.common;

import android.app.Application;
import android.content.Context;

import com.example.administrator.myapplication.entity.gen.DaoMaster;
import com.example.administrator.myapplication.entity.gen.DaoSession;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/2/25.
 */

public class myApplication extends Application
{

    public static Context context;

    private DaoMaster daoMaster;
    private static DaoSession recentSession;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.init("Myapp")               // default tag : PRETTYLOGGER or use just init()
                .hideThreadInfo();           // default it is shown

        this.context = getApplicationContext();  //获得全局 的Context;
        getRecentDao();
    }

    /**
     * 获取Dao
     */
    private void getRecentDao()
    {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "recentStory.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        recentSession = daoMaster.newSession();
    }

    public static DaoSession getRecentSession()
    {
        return recentSession;
    }
}
