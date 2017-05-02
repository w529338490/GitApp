package com.example.administrator.myapplication.Utill;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/4/26.
 * 文件 工具类
 */
public class FileUtil
{
    /**
     *文件保存 根目录
     *
     */
    public static  String FILE_ROOT= Environment.getExternalStorageDirectory().getAbsolutePath()+"/GitApp/";
    /**
     * 检查文件是否存在
     *
     * @param name
     * @return
     */
    public static  boolean chechFile(String name)
    {


        boolean status;
        if (!name.equals(""))
        {
            File newPath = new File(FILE_ROOT + name);
            status = newPath.exists();
        } else
        {
            status = false;
        }
        return status;

    }


}
