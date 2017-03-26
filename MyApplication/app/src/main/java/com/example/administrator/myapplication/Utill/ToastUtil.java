package com.example.administrator.myapplication.Utill;

import android.content.Context;
import android.widget.Toast;
import com.example.administrator.myapplication.R;
/**
 * Created by Administrator on 2017/3/26.
 */

public  class ToastUtil
{
    private Context context;
    public static void  show(Context context,
            String str)
    {

        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

}
