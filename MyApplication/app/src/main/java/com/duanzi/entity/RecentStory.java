package com.duanzi.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 小说的最近浏览和书架
 * Created by k9579 on 2017/6/18.
 */
@Entity
public class RecentStory
{
    @Id(autoincrement = true)
    private Long id;
    private String storyName;
    private String url;
    private String pic;
    @Generated(hash = 1153263673)
    public RecentStory(Long id, String storyName, String url, String pic) {
        this.id = id;
        this.storyName = storyName;
        this.url = url;
        this.pic = pic;
    }
    @Generated(hash = 265140000)
    public RecentStory() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStoryName() {
        return this.storyName;
    }
    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
   

   
}
