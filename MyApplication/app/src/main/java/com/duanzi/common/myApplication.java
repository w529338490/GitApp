package com.duanzi.common;

import android.app.Application;
import android.content.Context;

import com.duanzi.DB.gen.DaoMaster;
import com.duanzi.DB.gen.DaoSession;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/2/25.
 */

public class myApplication extends Application
{

    /**
     * 微信账号
     */
    public static final String WECHAI_APP_ID = "wx086676363a5b32a4";
    //public static final String WECHAT_AppSecret = "0a3afa56b65e236dcd0d88735e38f3f7";

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
