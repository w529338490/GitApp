package com.duanzi.ui.video;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.duanzi.DB.DbBean.VideoBean;
import com.duanzi.R;
import com.duanzi.adapter.VideoViewAdapter;
import com.duanzi.entity.RandomData;
import com.duanzi.entity.Video;
import com.duanzi.net.Api;
import com.duanzi.net.Service.GankService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VideoActivity extends RxAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    Toolbar toolbar;
    SwipeRefreshLayout fresh;
    GankService service;
    LinearLayoutManager manager;
    RecyclerView recyview;
    VideoViewAdapter adapter;

    int page_num = 20;
    List<RandomData.Gank> list = new ArrayList<>();
    List<Video.DataBean.DataBeans> listData = new ArrayList();
    List<VideoBean> myVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ShareSDK.initSDK(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        init();
    }

    private void init()
    {
        recyview = (RecyclerView) findViewById(R.id.recyview);
        manager = new LinearLayoutManager(this);
        fresh = (SwipeRefreshLayout)findViewById(R.id.fresh);
        fresh.setOnRefreshListener(this);

        fresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);


            getData();

    }

    public void getData()
    {
        service = Api.getInstance().apiGank();
        Observable<Video> obsVideo = service.getVideo();
        obsVideo.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Video>bindToLifecycle())
                .subscribe(new Action1<Video>()
                {
                    @Override
                    public void call(Video randomData)
                    {
                        UpDataUi(randomData);
                    }

                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
    }

    public void UpDataUi(Video randomData)
    {
        if (randomData.getMessage().equals("success"))
        {
            listData = randomData.data.data;
            fresh.setRefreshing(false);
        }
        adapter = new VideoViewAdapter(listData, VideoActivity.this);
        recyview.setLayoutManager(manager);
        recyview.setAdapter(adapter);
        adapter.setOnShareClickListner(new VideoViewAdapter.onShareClickListner()
        {
            @Override
            public void onItemClick(View view, VideoBean Bean)
            {
                showShare();
            }
        });

    }

    @Override
    public void onRefresh()
    {
        getData();

    }


    @Override
    protected void onPause()
    {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
    private void showShare()
    {
        ShareSDK.initSDK(VideoActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(VideoActivity.this);

    }

}
