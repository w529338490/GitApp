package com.example.administrator.myapplication.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.Utill.layoutmanger.CardConfig;
import com.example.administrator.myapplication.Utill.layoutmanger.OverLayCardLayoutManager;
import com.example.administrator.myapplication.adapter.ArtcleAdapter.ArtcleAdapter;
import com.example.administrator.myapplication.common.myApplication;
import com.example.administrator.myapplication.entity.Article;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ArtcleActivity extends RxAppCompatActivity
{

    OverLayCardLayoutManager manager;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyview;
    ArtcleAdapter adapter;
    List<Article> results = new ArrayList<>();

    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artcle);

        initView();
        initData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView()
    {
        CardConfig.initConfig(this);
        manager = new OverLayCardLayoutManager();
        recyview = (RecyclerView) findViewById(R.id.recyview);
    }

    private void initData()
    {
        Observable<Integer> getArtcle = Observable.create(new Observable.OnSubscribe<Integer>()
        {
            @Override
            public void call(Subscriber<? super Integer> subscriber)
            {
                results = JsoupUtil.getArticle();
                Collections.reverse(results);
                subscriber.onNext(1);

            }
        });
        getArtcle.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .compose(this.<Integer>bindToLifecycle())
                .subscribe(new Action1<Integer>()
                {
                    @Override
                    public void call(Integer s)
                    {
                        upDateUI();
                    }
                });


    }

    private void upDateUI()
    {
        adapter = new ArtcleAdapter(results);

        linearLayoutManager=new LinearLayoutManager(this);
        recyview.setLayoutManager(linearLayoutManager);
        recyview.setAdapter(adapter);
        recyview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
        {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
            {

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e)
            {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
            {

            }
        });
        adapter.setOnItemViewClickLisnter(new ArtcleAdapter.OnItemViewClickLisnter()
        {
            @Override
            public void getItemViewPosition(int Postion, View v)
            {
                Intent intent = new Intent(ArtcleActivity.this, ArtcleContetnActivity.class);
                intent.putExtra("Art", results.get(Postion));
             //   startActivity(intent);
                Picasso.with(myApplication.context)
                        .load("https://unsplash.it/400/800/?random")
                        .resize(400,400)
                        .error(R.drawable.bg_card)
                        .placeholder(R.drawable.bg_card)
                        .centerCrop()
                        .skipMemoryCache()
                        .into((ImageView) v);

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

                int dragFlags, swipeFlags;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof GridLayoutManager || manager instanceof OverLayCardLayoutManager)
                {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else
                {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                return makeMovementFlags(dragFlags, swipeFlags);

            }

            //拖拽效果
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                //更新集合位置，从新排布顺序
                //  Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                //  adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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

                Object remove = results.remove(viewHolder.getLayoutPosition());
                results.add(0, (Article) remove);
                adapter.notifyDataSetChanged();
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

}
