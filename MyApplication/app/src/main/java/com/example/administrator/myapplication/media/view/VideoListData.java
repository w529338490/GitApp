package com.example.administrator.myapplication.media.view;

import com.example.administrator.myapplication.entity.Video;

import java.io.Serializable;
import java.util.List;



/**
 * Author  wangchenchen
 * Description
 */
public class VideoListData implements Serializable {

    private List<Video> list;

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }
}
