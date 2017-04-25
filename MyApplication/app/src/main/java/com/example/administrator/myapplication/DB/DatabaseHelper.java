package com.example.administrator.myapplication.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myapplication.DB.DbBean.VideoBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/25.
 * 原生的数据库操作，需要继承SQLiteOpenHelper，这里我们需要继承OrmLiteSqliteOpenHelper
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String TABLE_NAME = "sqlite.db";
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    /**
     * VideoBean ，每张表对于一个
     */
    private Dao<com.example.administrator.myapplication.DB.DbBean.VideoBean, Integer> VideoBean;
    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 2);
    }

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion)
    {
        super(context, databaseName, factory, databaseVersion);
    }

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, File configFile)
    {
        super(context, databaseName, factory, databaseVersion, configFile);
    }

    /**
     * 这里创建表
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {

        try
            {
                TableUtils.createTable(connectionSource, VideoBean.class);
            } catch (java.sql.SQLException e)
            {
                e.printStackTrace();
            }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, VideoBean.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    private static DatabaseHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    /**
     * 获得userDao
     *
     * @return
     * @throws SQLException
     */
    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
//    @Override
//    public void close()
//    {
//        super.close();
//        VideoBean = null;
//    }
    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();

        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
