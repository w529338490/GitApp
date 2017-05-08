package com.duanzi.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/28.
 */

public class Article implements Serializable
{
    public String tittle;
    public String link;
    public String description;  //描述
    public String author;
    public String pubDate;
    public int position;
}
