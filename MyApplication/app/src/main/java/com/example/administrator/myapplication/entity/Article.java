package com.example.administrator.myapplication.entity;

import java.io.Serializable;
import java.util.Date;

import static com.example.administrator.myapplication.R.id.date;

/**
 * Created by Administrator on 2017/3/28.
 */

public class Article implements Serializable
{
    public  String tittle;
    public  String link;
    public  String description;  //描述
    public  String author;
    public  String pubDate;
    public int position;
}
