package com.example.administrator.myapplication.Utill;

/**
 * Created by k9579 on 2017/4/6.
 */

public class ConvertUtils
{
    /**
     * 将字符串转换为int
     * @param str
     * @return
     */
    public static Integer strToInt(String str)
    {
        if (str.isEmpty())
        {
            return null;
        }
        return Integer.parseInt(str);
    }
}
