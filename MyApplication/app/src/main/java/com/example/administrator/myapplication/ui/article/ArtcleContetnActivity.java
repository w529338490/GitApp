package com.example.administrator.myapplication.ui.article;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.JsoupUtil;
import com.example.administrator.myapplication.entity.Article;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ArtcleContetnActivity extends AppCompatActivity
{

    TextView tittle;
    TextView date;
    TextView who;
    TextView content;
    Article art;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artcle_contetn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        art= (Article) this.getIntent().getSerializableExtra("Art");

        tittle= (TextView) findViewById(R.id.tittle);
        date= (TextView) findViewById(R.id.date);
        who= (TextView) findViewById(R.id.who);
        content= (TextView) findViewById(R.id.content);
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

    private void initData()
    {
        tittle.setText(art.tittle);
        date.setText(art.pubDate);
        who.setText(art.author);
        Observable<String> obs=Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                String contet= JsoupUtil.getArtcleContent(art.link);
                subscriber.onNext(contet);
            }
        });

            obs .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>()
                {
                    @Override
                    public void call(String s)
                    {
                        content.setText( s);
                    }
                });

    }

}
