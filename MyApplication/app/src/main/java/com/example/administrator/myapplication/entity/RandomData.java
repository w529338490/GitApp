package com.example.administrator.myapplication.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class RandomData
{
    boolean error;
    public List<Gank> results;  //历史日期集合

    public class Gank
    {

//            id":"58d1eae9421aa90f131786b0",
//            "createdAt":"2017-03-22T11:09:29.904Z",
//            "desc":"Android Picture in Picture 效果 Demo。",
//            "images":Array[1],
//            "publishedAt":"2017-03-22T11:47:09.555Z",
//            "source":"chrome",
//            "type":"Android",
//            "url":"https://github.com/googlesamples/android-PictureInPicture",
//            "used":true,
//            "who":"带马甲"
        int id;
        Date createdAt;
        String desc;
        List<String> images;
        String type;
        String url;
        String who;

        @Override
        public String toString()
        {
            return "Gank{" +
                    "id=" + id +
                    ", createdAt=" + createdAt +
                    ", desc='" + desc + '\'' +
                    ", images=" + images +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", who='" + who + '\'' +
                    '}';
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getWho()
        {
            return who;
        }

        public void setWho(String who)
        {
            this.who = who;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public List<String> getImages()
        {
            return images;
        }

        public void setImages(List<String> images)
        {
            this.images = images;
        }

        public String getDesc()
        {
            return desc;
        }

        public void setDesc(String desc)
        {
            this.desc = desc;
        }

        public Date getCreatedAt()
        {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt)
        {
            this.createdAt = createdAt;
        }
    }

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public List<Gank> getResults()
    {
        return results;
    }

    public void setResults(List<Gank> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "RandomData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
