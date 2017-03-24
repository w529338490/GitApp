package com.example.administrator.myapplication.eventbus;

/**
 * Created by Administrator on 2017/3/23.
 */

public class FirstEvent
{

    private String mMsg;
    public FirstEvent(String msg) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }
}
