package com.duanzi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class Video
{
    public String message;

    public DataBean data;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public static class DataBean
    {
        public boolean has_more;
        public String tip;
        public boolean has_new_message;
        public double max_time;
        public int min_time;
        /**
         * group : {"video_id":"384cd31f1efb4e8aa500d0ab00d7b4bc","360p_video":{"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360},"mp4_url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=0&is_gif=0&device_platform=android.mp4","text":"这个速度能达到多少？","720p_video":{"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360},"digg_count":1128,"duration":7.56,"480p_video":{"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360},"create_time":1492420587,"keywords":"","id":59162686275,"favorite_count":370,"danmaku_attrs":{"allow_show_danmaku":1,"allow_send_danmaku":1},"m3u8_url":"","large_cover":{"url_list":[{"url":"http://p1.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"}],"uri":"large/1c7d000a1c3cb8196e84"},"user_favorite":0,"share_type":1,"title":"","user":{"user_id":6000099775,"name":"灬魏宏飞灬","followings":0,"user_verified":false,"ugc_count":120,"avatar_url":"http://q.qlogo.cn/qqapp/100290348/7E838171140B127E35819258D25536E0/100","followers":182,"is_following":false,"is_pro_user":false},"is_can_share":1,"category_type":1,"share_url":"http://m.neihanshequ.com/share/group/59162686275/?iid=3216590132&app=joke_essay","label":1,"content":"这个速度能达到多少？","video_height":360,"comment_count":833,"cover_image_uri":"1c7d000a1c3cb8196e84","id_str":"59162686275","media_type":3,"share_count":1373,"type":2,"category_id":65,"status":112,"has_comments":0,"publish_time":"","go_detail_count":67929,"activity":{},"status_desc":"热门投稿","dislike_reason":[{"type":1,"id":338,"title":"体育运动"},{"type":1,"id":340,"title":"猎奇"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6000099775,"title":"作者:灬魏宏飞灬"}],"neihan_hot_start_time":"00-00-00","play_count":207167,"user_repin":0,"quick_comment":false,"medium_cover":{"url_list":[{"url":"http://p1.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"}],"uri":"medium/1c7d000a1c3cb8196e84"},"neihan_hot_end_time":"00-00-00","user_digg":0,"video_width":360,"online_time":1492420587,"category_name":"搞笑视频","flash_url":"","category_visible":true,"bury_count":78,"is_anonymous":false,"repin_count":370,"is_video":1,"is_neihan_hot":false,"uri":"384cd31f1efb4e8aa500d0ab00d7b4bc","is_public_url":1,"has_hot_comments":0,"allow_dislike":true,"origin_video":{"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360},"cover_image_url":"","neihan_hot_link":{},"group_id":59162686275,"user_bury":0,"display_type":0}
         * comments : []
         * type : 1
         * display_time : 1492761442
         * online_time : 1492761442
         */

        public List<DataBeans> data;

        public boolean isHas_more()
        {
            return has_more;
        }

        public void setHas_more(boolean has_more)
        {
            this.has_more = has_more;
        }

        public String getTip()
        {
            return tip;
        }

        public void setTip(String tip)
        {
            this.tip = tip;
        }

        public boolean isHas_new_message()
        {
            return has_new_message;
        }

        public void setHas_new_message(boolean has_new_message)
        {
            this.has_new_message = has_new_message;
        }

        public double getMax_time()
        {
            return max_time;
        }

        public void setMax_time(double max_time)
        {
            this.max_time = max_time;
        }

        public int getMin_time()
        {
            return min_time;
        }

        public void setMin_time(int min_time)
        {
            this.min_time = min_time;
        }

        public List<DataBeans> getData()
        {
            return data;
        }

        public void setData(List<DataBeans> data)
        {
            this.data = data;
        }

        public static class DataBeans
        {
            /**
             * video_id : 384cd31f1efb4e8aa500d0ab00d7b4bc
             * 360p_video : {"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360}
             * mp4_url : http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=0&is_gif=0&device_platform=android.mp4
             * text : 这个速度能达到多少？
             * 720p_video : {"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360}
             * digg_count : 1128
             * duration : 7.56
             * 480p_video : {"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360}
             * create_time : 1492420587
             * keywords :
             * id : 59162686275
             * favorite_count : 370
             * danmaku_attrs : {"allow_show_danmaku":1,"allow_send_danmaku":1}
             * m3u8_url :
             * large_cover : {"url_list":[{"url":"http://p1.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"}],"uri":"large/1c7d000a1c3cb8196e84"}
             * user_favorite : 0
             * share_type : 1
             * title :
             * user : {"user_id":6000099775,"name":"灬魏宏飞灬","followings":0,"user_verified":false,"ugc_count":120,"avatar_url":"http://q.qlogo.cn/qqapp/100290348/7E838171140B127E35819258D25536E0/100","followers":182,"is_following":false,"is_pro_user":false}
             * is_can_share : 1
             * category_type : 1
             * share_url : http://m.neihanshequ.com/share/group/59162686275/?iid=3216590132&app=joke_essay
             * label : 1
             * content : 这个速度能达到多少？
             * video_height : 360
             * comment_count : 833
             * cover_image_uri : 1c7d000a1c3cb8196e84
             * id_str : 59162686275
             * media_type : 3
             * share_count : 1373
             * type : 2
             * category_id : 65
             * status : 112
             * has_comments : 0
             * publish_time :
             * go_detail_count : 67929
             * activity : {}
             * status_desc : 热门投稿
             * dislike_reason : [{"type":1,"id":338,"title":"体育运动"},{"type":1,"id":340,"title":"猎奇"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6000099775,"title":"作者:灬魏宏飞灬"}]
             * neihan_hot_start_time : 00-00-00
             * play_count : 207167
             * user_repin : 0
             * quick_comment : false
             * medium_cover : {"url_list":[{"url":"http://p1.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"}],"uri":"medium/1c7d000a1c3cb8196e84"}
             * neihan_hot_end_time : 00-00-00
             * user_digg : 0
             * video_width : 360
             * online_time : 1492420587
             * category_name : 搞笑视频
             * flash_url :
             * category_visible : true
             * bury_count : 78
             * is_anonymous : false
             * repin_count : 370
             * is_video : 1
             * is_neihan_hot : false
             * uri : 384cd31f1efb4e8aa500d0ab00d7b4bc
             * is_public_url : 1
             * has_hot_comments : 0
             * allow_dislike : true
             * origin_video : {"width":360,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/384cd31f1efb4e8aa500d0ab00d7b4bc","height":360}
             * cover_image_url :
             * neihan_hot_link : {}
             * group_id : 59162686275
             * user_bury : 0
             * display_type : 0
             */

            public GroupBean group;
            public int type;


            public GroupBean getGroup()
            {
                return group;
            }

            public void setGroup(GroupBean group)
            {
                this.group = group;
            }

            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }


            public static class GroupBean
            {
                public String video_id;
                public String mp4_url;
                public String text;
                public long id;
                public int favorite_count;
                public String m3u8_url;
                /**
                 * url_list : [{"url":"http://p1.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/large/1c7d000a1c3cb8196e84.webp"}]
                 * uri : large/1c7d000a1c3cb8196e84
                 */
                public int user_favorite;
                public int share_type;
                public String title;
                /**
                 * user_id : 6000099775
                 * name : 灬魏宏飞灬
                 * followings : 0
                 * user_verified : false
                 * ugc_count : 120
                 * avatar_url : http://q.qlogo.cn/qqapp/100290348/7E838171140B127E35819258D25536E0/100
                 * followers : 182
                 * is_following : false
                 * is_pro_user : false
                 */

                public String content;
                public int comment_count;
                public String cover_image_uri;
                public String id_str;
                public int media_type;
                public int share_count;
                public int status;
                public int go_detail_count;
                public int play_count;
                public int user_repin;
                public boolean quick_comment;
                /**
                 * url_list : [{"url":"http://p1.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"},{"url":"http://pb3.pstatp.com/w202/1c7d000a1c3cb8196e84.webp"}]
                 * uri : medium/1c7d000a1c3cb8196e84
                 */

                public MediumCoverBean medium_cover;
                public String category_name;
                public String flash_url;
                public String uri;
                /**
                 * width : 360
                 * url_list : [{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=1&is_gif=0&device_platform=android"}]
                 * uri : origin/384cd31f1efb4e8aa500d0ab00d7b4bc
                 * height : 360
                 */

                public OriginVideoBean origin_video;
                public String cover_image_url;
                public long group_id;

                /**
                 * type : 1
                 * id : 338
                 * title : 体育运动
                 */

                public String getVideo_id()
                {
                    return video_id;
                }

                public void setVideo_id(String video_id)
                {
                    this.video_id = video_id;
                }

                public String getMp4_url()
                {
                    return mp4_url;
                }

                public void setMp4_url(String mp4_url)
                {
                    this.mp4_url = mp4_url;
                }

                public String getText()
                {
                    return text;
                }

                public void setText(String text)
                {
                    this.text = text;
                }

                public long getId()
                {
                    return id;
                }

                public void setId(long id)
                {
                    this.id = id;
                }

                public int getFavorite_count()
                {
                    return favorite_count;
                }

                public void setFavorite_count(int favorite_count)
                {
                    this.favorite_count = favorite_count;
                }

                public String getM3u8_url()
                {
                    return m3u8_url;
                }

                public void setM3u8_url(String m3u8_url)
                {
                    this.m3u8_url = m3u8_url;
                }


                public int getUser_favorite()
                {
                    return user_favorite;
                }

                public void setUser_favorite(int user_favorite)
                {
                    this.user_favorite = user_favorite;
                }

                public int getShare_type()
                {
                    return share_type;
                }

                public void setShare_type(int share_type)
                {
                    this.share_type = share_type;
                }

                public String getTitle()
                {
                    return title;
                }

                public void setTitle(String title)
                {
                    this.title = title;
                }

                public String getContent()
                {
                    return content;
                }

                public void setContent(String content)
                {
                    this.content = content;
                }

                public int getComment_count()
                {
                    return comment_count;
                }

                public void setComment_count(int comment_count)
                {
                    this.comment_count = comment_count;
                }

                public String getCover_image_uri()
                {
                    return cover_image_uri;
                }

                public void setCover_image_uri(String cover_image_uri)
                {
                    this.cover_image_uri = cover_image_uri;
                }

                public String getId_str()
                {
                    return id_str;
                }

                public void setId_str(String id_str)
                {
                    this.id_str = id_str;
                }

                public int getMedia_type()
                {
                    return media_type;
                }

                public void setMedia_type(int media_type)
                {
                    this.media_type = media_type;
                }

                public int getShare_count()
                {
                    return share_count;
                }

                public void setShare_count(int share_count)
                {
                    this.share_count = share_count;
                }

                public int getStatus()
                {
                    return status;
                }

                public void setStatus(int status)
                {
                    this.status = status;
                }

                public int getGo_detail_count()
                {
                    return go_detail_count;
                }

                public void setGo_detail_count(int go_detail_count)
                {
                    this.go_detail_count = go_detail_count;
                }

                public int getPlay_count()
                {
                    return play_count;
                }

                public void setPlay_count(int play_count)
                {
                    this.play_count = play_count;
                }

                public int getUser_repin()
                {
                    return user_repin;
                }

                public void setUser_repin(int user_repin)
                {
                    this.user_repin = user_repin;
                }

                public boolean isQuick_comment()
                {
                    return quick_comment;
                }

                public void setQuick_comment(boolean quick_comment)
                {
                    this.quick_comment = quick_comment;
                }

                public MediumCoverBean getMedium_cover()
                {
                    return medium_cover;
                }

                public void setMedium_cover(MediumCoverBean medium_cover)
                {
                    this.medium_cover = medium_cover;
                }

                public String getCategory_name()
                {
                    return category_name;
                }

                public void setCategory_name(String category_name)
                {
                    this.category_name = category_name;
                }

                public String getFlash_url()
                {
                    return flash_url;
                }

                public void setFlash_url(String flash_url)
                {
                    this.flash_url = flash_url;
                }

                public String getUri()
                {
                    return uri;
                }

                public void setUri(String uri)
                {
                    this.uri = uri;
                }

                public OriginVideoBean getOrigin_video()
                {
                    return origin_video;
                }

                public void setOrigin_video(OriginVideoBean origin_video)
                {
                    this.origin_video = origin_video;
                }

                public String getCover_image_url()
                {
                    return cover_image_url;
                }

                public void setCover_image_url(String cover_image_url)
                {
                    this.cover_image_url = cover_image_url;
                }

                public long getGroup_id()
                {
                    return group_id;
                }

                public void setGroup_id(long group_id)
                {
                    this.group_id = group_id;
                }


                public static class MediumCoverBean
                {
                    public String uri;
                    /**
                     * url : http://p1.pstatp.com/w202/1c7d000a1c3cb8196e84.webp
                     */

                    public List<UrlListBean> url_list;

                    public String getUri()
                    {
                        return uri;
                    }

                    public void setUri(String uri)
                    {
                        this.uri = uri;
                    }

                    public List<UrlListBean> getUrl_list()
                    {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBean> url_list)
                    {
                        this.url_list = url_list;
                    }

                    public static class UrlListBean
                    {
                        public String url;

                        public String getUrl()
                        {
                            return url;
                        }

                        public void setUrl(String url)
                        {
                            this.url = url;
                        }
                    }
                }

                public static class OriginVideoBean
                {
                    public int width;
                    public String uri;
                    public int height;
                    /**
                     * url : http://ic.snssdk.com/neihan/video/playback/1492761442.91/?video_id=384cd31f1efb4e8aa500d0ab00d7b4bc&quality=origin&line=0&is_gif=0&device_platform=android
                     */

                    public List<UrlListBean> url_list;

                    public int getWidth()
                    {
                        return width;
                    }

                    public void setWidth(int width)
                    {
                        this.width = width;
                    }

                    public String getUri()
                    {
                        return uri;
                    }

                    public void setUri(String uri)
                    {
                        this.uri = uri;
                    }

                    public int getHeight()
                    {
                        return height;
                    }

                    public void setHeight(int height)
                    {
                        this.height = height;
                    }

                    public List<UrlListBean> getUrl_list()
                    {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListBean> url_list)
                    {
                        this.url_list = url_list;
                    }

                    public static class UrlListBean
                    {
                        public String url;

                        public String getUrl()
                        {
                            return url;
                        }

                        public void setUrl(String url)
                        {
                            this.url = url;
                        }
                    }
                }

                public static class DislikeReasonBean
                {
                    public int type;
                    public int id;
                    public String title;

                    public int getType()
                    {
                        return type;
                    }

                    public void setType(int type)
                    {
                        this.type = type;
                    }

                    public int getId()
                    {
                        return id;
                    }

                    public void setId(int id)
                    {
                        this.id = id;
                    }

                    public String getTitle()
                    {
                        return title;
                    }

                    public void setTitle(String title)
                    {
                        this.title = title;
                    }
                }
            }
        }
    }


}
