package com.duanzi.DB.DbDao;

import android.content.Context;

import com.duanzi.DB.DatabaseHelper;
import com.duanzi.Utill.ToastUtil;
import com.duanzi.DB.DbBean.VideoBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 * 数据库操作类
 */
public class  VideoDao
{
    private Context context;
    private Dao<VideoBean, Integer> userDaoOpe;
    private DatabaseHelper helper;

    public VideoDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            userDaoOpe = helper.getDao(VideoBean.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个用户
     * @param video
     */
    public void add(VideoBean  video)
    {
        try
        {
            userDaoOpe.createOrUpdate(video);
            ToastUtil.show("收藏成功");
        } catch (SQLException e)
        {
            ToastUtil.show("收藏失败");
            e.printStackTrace();
        }

    }//...other operations

    /**
     * 删除一个
     */
    public  void deletData(VideoBean video)
    {
        try
        {
            userDaoOpe.delete(video);
            ToastUtil.show("删除成功");
        } catch (SQLException e)
        {
            e.printStackTrace();
            ToastUtil.show("删除失败");
        }
    }
    public  void deleAll()
    {
        for (VideoBean img:getLiveItemBeans())
        {
            deletData(img);
                   }

    }

    /**
     * 查询所有
     */
    public List<VideoBean> getLiveItemBeans(){
        List<VideoBean> listsBeans = new ArrayList<VideoBean>();
        try {
            listsBeans = userDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listsBeans;
    }
    /**
     * 查询单个
     */
    public VideoBean findBeanByPath(String paath)
    {
        VideoBean bean = null;
        try
        {
             if(userDaoOpe.queryBuilder().where().eq("videoUri", paath).query().size()!=0)
             {
                 bean=userDaoOpe.queryBuilder().where().eq("videoUri", paath).query().get(0);
             }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return bean;
    }

}