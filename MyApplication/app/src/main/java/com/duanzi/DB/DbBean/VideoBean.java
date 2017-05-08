package com.duanzi.DB.DbBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 */

@DatabaseTable(tableName = "video")
public class VideoBean implements Serializable
{
    //generatedId 表示id为主键且自动生成
    @DatabaseField(generatedId = true)
    private int id;

    //columnName的值为该字段在数据中的列名
    @DatabaseField(columnName = "thumbUrl")
    String thumbUrl;  //视屏缩略图
    @DatabaseField(columnName = "videoUri")
    String videoUri; //视屏地址
    @DatabaseField(columnName = "tittle")
    String tittle;   //视屏标题

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl)
    {
        this.thumbUrl = thumbUrl;
    }

    public String getTittle()
    {
        return tittle;
    }

    public void setTittle(String tittle)
    {
        this.tittle = tittle;
    }

    public String getVideoUri()
    {
        return videoUri;
    }

    public void setVideoUri(String videoUri)
    {
        this.videoUri = videoUri;
    }
}
