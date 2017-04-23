package com.example.administrator.myapplication.entity;

import java.util.List;

/**
 * 书籍实体类
 * Created by admin on 2017/4/3.
 */

public class Story
{
    private List<Story> storyList;
    private String type; // 小说分类
    private String title; // 标题
    private String uri; // 链接
    private String content; // 简介
    private String author; // 作者
    private String index; // 作者主页
    private Integer hot; // 人气
    private String mark; // 是否签约
    private String updateTime; // 更新时间
    private String readUrl; // 开始阅读链接
    private String storyPic; // 封面
    private String words; // 字数
    private List<Story.StoryCatalog> catalogList; // 小说目录列表


    public static class StoryCatalog
    {
        private String catalog;
        private String url;

        public String getCatalog()
        {
            return catalog;
        }

        public void setCatalog(String catalog)
        {
            this.catalog = catalog;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        @Override
        public String toString()
        {
            return "StoryCatalog{" +
                    "catalog='" + catalog + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public String getWords()
    {
        return words;
    }

    public void setWords(String words)
    {
        this.words = words;
    }

    public String getStoryPic()
    {
        return storyPic;
    }

    public void setStoryPic(String storyPic)
    {
        this.storyPic = storyPic;
    }

    public List<Story> getStoryList()
    {
        return storyList;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setStoryList(List<Story> storyList)
    {
        this.storyList = storyList;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Integer getHot()
    {
        return hot;
    }

    public void setHot(Integer hot)
    {
        this.hot = hot;
    }

    public String getMark()
    {
        return mark;
    }

    public void setMark(String mark)
    {
        this.mark = mark;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getReadUrl()
    {
        return readUrl;
    }

    public void setReadUrl(String readUrl)
    {
        this.readUrl = readUrl;
    }

    public List<StoryCatalog> getCatalogList()
    {
        return catalogList;
    }

    public void setCatalogList(List<StoryCatalog> catalogList)
    {
        this.catalogList = catalogList;
    }

    @Override
    public String toString()
    {
        return "Story{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", uri='" + uri + '\'' +
                ", author='" + author + '\'' +
                ", index='" + index + '\'' +
                ", hot=" + hot +
                ", mark='" + mark + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
