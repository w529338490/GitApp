package com.example.administrator.myapplication.eventbus;

/**
 * Created by Administrator on 2017/3/23.
 * EvevtBus是用于程序里面数据传输的，
 * 其优点就是结构优雅，使用简单，解耦性强大，
 * 代替Android中原有的Intent，Handler等传递数据
 */

public class BeseEvent
{
    String url;

    public BeseEvent(String url)
    {
        url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
