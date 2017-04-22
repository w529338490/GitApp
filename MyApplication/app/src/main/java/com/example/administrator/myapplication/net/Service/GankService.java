package com.example.administrator.myapplication.net.Service;

import com.example.administrator.myapplication.entity.DayGankResult;
import com.example.administrator.myapplication.entity.GankAllData;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.entity.Video;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/21.
 * 来自Gank的APi
 *   使用ＲｘＡｎｄｒｏｉｄ
 */


public  interface GankService
{
    /**
     * 获取Gank的历史日期
     * @return
     *   http://gank.io/api/day/history
     */

    @GET("day/history")
    Observable<DayGankResult>getHistory();

    /**
     *
     * @param type 类型 android ios 福利
     *
     * @return
     */
    //http://gank.io/api/random/data/Android/20
    @GET("random/data/{type}/{page_num}")
    Observable<RandomData>getRandomData(@Path("type")String type,@Path("page_num")Integer page_num);

    @GET("day/{date}")
    Observable<GankAllData>getAllData(@Path("date")String date);

    //视屏接口
    @GET("http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1463225362814&count=30&min_time=1489143837&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120")
    Observable<Video>getVideo();
}
