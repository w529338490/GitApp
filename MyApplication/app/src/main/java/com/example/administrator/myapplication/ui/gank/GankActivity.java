package com.example.administrator.myapplication.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.GankAdapter;
import com.example.administrator.myapplication.entity.DayGankResult;
import com.example.administrator.myapplication.entity.RandomData;
import com.example.administrator.myapplication.eventbus.BeseEvent;
import com.example.administrator.myapplication.eventbus.FirstEvent;
import com.example.administrator.myapplication.net.Api;
import com.example.administrator.myapplication.net.Service.GankService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class GankActivity extends RxAppCompatActivity
{

    RecyclerView recyview;
    SwipeRefreshLayout fresh;
    GridLayoutManager parent;
    GankService service;
    GankAdapter adapter;
    int page_num = 20;
    List<RandomData.Gank> list = new ArrayList<>();
    ItemTouchHelper helper;   //实现recyview拖拽动画

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);

        initView();
        initData();
    }

    private void initView()
    {
        recyview = (RecyclerView) findViewById(R.id.recyview);
        fresh = (SwipeRefreshLayout) findViewById(R.id.fresh);
        parent = new GridLayoutManager(this, 2);


    }

    private void initData()
    {
        //使用RxAndroid
        service = Api.getInstance().apiGank();
        //通过service获得 Observable对象,完成 异步加载数据
        Observable<RandomData> obsFuli = service.getRandomData("福利", page_num);
        Observable<RandomData> obsAndroid = service.getRandomData("Android", page_num);
        Observable<RandomData> obs = Observable.zip(obsFuli, obsAndroid, new Func2<RandomData, RandomData, RandomData>()
        {
            @Override
            public RandomData call(RandomData randomData, RandomData randomData2)
            {
                for (int i = 0; i < randomData2.getResults().size(); i++)
                {
                    //results.get(position).getUrl()
                    randomData2.getResults().get(i).setUrl(randomData.getResults().get(i).getUrl());
                }
                return randomData2;
            }
        });
        obs.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//处理结果回调 在UI 主线程
                .compose(this.<RandomData>bindToLifecycle())   //RxJava与Activity生命周期一起绑定，节约内存
                //subscribe  为返回回调
                .subscribe(new Subscriber<RandomData>()

                {
                    @Override
                    public void onCompleted()
                    {
                        Log.e("onCompleted", "=====================");
                        //  请求完成回调
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        //请求 错误回调
                        Log.e("error", "=================" + e.getMessage());

                    }

                    @Override
                    public void onNext(RandomData randomData)
                    {

                        //在主线程 更新UI
                        UpDataUi(randomData);
                    }
                });
    }

    //更新 UI炒作
    public void UpDataUi(RandomData randomData)
    {
        if (randomData.isError() == false && randomData.getResults() != null)
        {
            list = randomData.getResults();
        }


        adapter = new GankAdapter(GankActivity.this, list);
        recyview.setLayoutManager(parent);
        recyview.setAdapter(adapter);
        adapter.setOnImageViewLisnter(new GankAdapter.OnImageViewLisnter()
        {
            @Override
            public void getImgPath(String url)
            {

                // TODO Auto-generated method stub
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked"));
               // EventBus.getDefault().post(new BeseEvent("hahahha"));
                Intent intent=new Intent(GankActivity.this,IamgeActivity.class);
                startActivity(intent);
            }
        });
        //实现recyview拖拽
        helper = new ItemTouchHelper(new ItemTouchHelper.Callback()
        {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                /**  //判断LayoutManager的类型 线性还是GridView类型
                 * @param recyclerView
                 * @param viewHolder 拖动的ViewHolder
                 * @param target 目标位置的ViewHolder
                 * @return
                 */

                int dragFlags;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager)
                {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else
                {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);

            }

            //拖拽效果
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                //更新集合位置，从新排布顺序
                Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;

            }

            //滑动效果，在LinerLaout时候调用
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                /**
                 * @param viewHolder 滑动的ViewHolder
                 * @param direction 滑动的方向
                 */

            }

            @Override
            public boolean isLongPressDragEnabled()
            {
                // 不需要长按拖拽功能  我们手动控制
                return true;
            }
        });
        helper.attachToRecyclerView(recyview);

    }

    class MyHelper extends ItemTouchHelper
    {
        /**
         * Creates an ItemTouchHelper that will work with the given Callback.
         * <p>
         * You can attach ItemTouchHelper to a RecyclerView via
         * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
         * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
         *
         * @param callback The Callback which controls the behavior of this touch helper.
         */
        public MyHelper(Callback callback)
        {
            super(callback);
        }
    }


}




