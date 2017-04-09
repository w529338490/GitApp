package com.example.administrator.myapplication.ui.story;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Document doc = Jsoup.connect("http://36kr.com/").get();
                            Elements news = doc.getElementsByClass("headline__news");//通过class来选择
                            for (Element element : news)
                            {
                                Elements aNode = element.getElementsByTag("a");//标签选择
                                String link = aNode.attr("href");//获得属性值
                                Logger.e("link", link.toString());
                                Elements hNode = element.getElementsByTag("h");//标签选择
                                String title = aNode.text();//获得文本内容
                                Logger.e("title", title.toString());
                            }
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).run();
            }
        });
    }



}
