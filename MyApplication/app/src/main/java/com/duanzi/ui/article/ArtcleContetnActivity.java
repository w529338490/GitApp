package com.duanzi.ui.article;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.duanzi.Utill.JsoupUtil;
import com.duanzi.entity.Article;
import com.duanzi.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ArtcleContetnActivity extends RxAppCompatActivity
{

    TextView tittle;
    TextView who;
    WebView content;
    Article art;
    Toolbar toobar;
    TextView end,member;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artcle_contetn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        art = (Article) this.getIntent().getSerializableExtra("Art");

        tittle = (TextView) findViewById(R.id.tittle);
        who = (TextView) findViewById(R.id.who);
        member = (TextView) findViewById(R.id.member);
        end = (TextView) findViewById(R.id.end);
        content = (WebView) findViewById(R.id.content);

        toobar = (Toolbar) findViewById(R.id.toolbar);
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
        toobar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        Observable< String[]> obs = Observable.create(new Observable.OnSubscribe< String[]>()
        {
            @Override
            public void call(Subscriber<? super  String[]> subscriber)
            {
                String[] contet = JsoupUtil.getArtcleContent(art.link);
                subscriber.onNext(contet);
            }
        });

        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.< String[]>bindToLifecycle())
                .subscribe(new Action1< String[]>()
                {
                    @Override
                    public void call( String[] s)
                    {
                        content.setVerticalScrollBarEnabled(false);
                        tittle.setText(s[0]);
                        who.setText(s[1]);
                        toobar.setTitle(s[0]);
                        String cotetnt=
                                "<![CDATA"+
                                "<html>" +
                                " <head>" +
                                        "<style type=\"text/css\">\n" +
                                        "  p {text-align:justify;font-size:18px;color:#323232;text-indent:2em;}\n" +
                                        "</style>"+
                                        "</head>" +

                                " <body style=\"background-color:#F5F5F5;\">" + s[2] +
                                "      </body>\n" +
                                "</html>\n" ;
                        content.loadDataWithBaseURL("",cotetnt, "text/html", "utf-8","");
                        member.setText(cotetnt.length()+"字");
                        end.setVisibility(View.VISIBLE);
                    }
                });

    }

}
