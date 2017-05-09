package com.duanzi.eventbus;

import com.duanzi.entity.RandomData;

/**
 * Created by Administrator on 2017/3/25.
 */

public class GankEvent
{
    RandomData.Gank gankDetail;

    public GankEvent(RandomData.Gank gankDetail)
    {
        this.gankDetail = gankDetail;
    }

    public RandomData.Gank getGankDetail()
    {
        return gankDetail;
    }
}
