package com.example.administrator.myapplication.Utill;

import android.util.Log;

import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.entity.Gif;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by jiangzehui on 16/11/9.
 */
public class JsoupUtil {

    public static ArrayList<String> list_dongtai = new ArrayList<>();
    public static ArrayList<String> list_xiegif = new ArrayList<>();
    public static ArrayList<String> list_gaoxiao = new ArrayList<>();

    public static int getListSize(int type) {
        switch (type) {
            case 0:
                return list_dongtai.size();

            case 1:
                return list_xiegif.size();
            case 2:
                return list_gaoxiao.size();

            default:
                return 0;

        }
    }

    public static String getListItem(int position, int type) {
        switch (type) {
            case 0:
                return list_dongtai.get(position);

            case 1:
                return list_xiegif.get(position);
            case 2:
                return list_gaoxiao.get(position);
            default:
                return "";

        }
    }

    /**
     * 抓取网页动态图
     *
     * @param urls
     * @param type
     * @return
     */
    public static ArrayList<Gif> getGif(String urls, int type) {


        ArrayList<Gif> list = new ArrayList<>();
        Document doc = null;
        try {

            doc = Jsoup.parse(new URL(urls), 5000);
             Logger.e(doc.nodeName());

            Elements es_item = doc.getElementsByClass("item");

            for (int i = 0; i < es_item.size(); i++) {
                Element et = es_item.get(i).getElementsByTag("h3").first();
                if (et != null) {
                    String title = et.getElementsByTag("b").text();
                    String url = es_item.get(i).select("img").first().attr("src");
                    Log.i("jsoup", title + "\t\t" + url + "\n");
                    list.add(new Gif(title, url));

                }

            }
            Elements es_page = doc.getElementsByClass("page").first().getElementsByTag("select").first().getElementsByTag("option");
            for (int i = 0; i < es_page.size(); i++) {
                Element et = es_page.get(i);
                if (et != null) {
                    switch (type) {
                        case 0:
                            list_dongtai.add(et.attr("value"));
                            break;
                        case 1:
                            list_xiegif.add(et.attr("value"));
                            break;
                        case 2:
                            list_gaoxiao.add(et.attr("value"));
                            break;
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     *
     * @param url
     * 每日名言 爬取;
     *
     */
    public  static ArrayList<Famous> getFamous(String url)
    {
        ArrayList<Famous>list_famous=new ArrayList<>();
        String Famous[] = new String[0];
        Document docs = null;
        try
        {
            docs=Jsoup.parse(new URL("http://www.diyifanwen.com/tool/jingdianmingyan/1251421170040936.htm"), 5000);
            Element element=docs.getElementById("ArtContent");
            Elements es=element.getElementsByTag("p");

          //  String strs[]=es.get(0).text().split(" ");

          for(int j=0;j<es.size();j++)
          {
              for(String str:es.get(j).text().split(" "))
              {
                  com.example.administrator.myapplication.entity.Famous famous=new Famous();
                  if(str.length()<=16&&str.length()>6)
                  {
                      famous.setFamous(str);
                      list_famous.add(famous);
                  }
              }
          }

        } catch (IOException e)
        {
            Logger.e("Jsoup->getFamous:",e.getMessage());
            e.printStackTrace();
        }

        return list_famous;
    }

    /**
     * 每日一文。爬取http://w.ihx.cc/meiriyiwen/1434.html
     * http://w.ihx.cc/category/meiriyiwen
     */

    public  static ArrayList<Article> getArticle()
    {

        ArrayList<Article>articles =new ArrayList<>();
        Document docs = null;
        try
        {
            docs=Jsoup.parse(new URL("http://w.ihx.cc/category/meiriyiwen/feed"),10000);
            Elements elements=docs.getElementsByTag("item");
              for(int i=0;i<elements.size();i++)
              {
                  Article article=new Article();
               //  Logger.e(elements.get(i).getElementsByTag("a").first().attr("href"));
                  Logger.e(elements.get(i).getElementsByTag("title").outerHtml());
                  Logger.e(elements.get(i).getElementsByTag("link").outerHtml());
                  Logger.e(elements.get(i).getElementsByTag("pubDate").outerHtml());
                  Logger.e(elements.get(i).getElementsByTag("description").outerHtml());
                  Logger.e(elements.get(i).getElementsByTag("category").eq(2).outerHtml());

                  article.author=elements.get(i).getElementsByTag("category").eq(2).text();
                  article.tittle=elements.get(i).getElementsByTag("title").text();
                  article.description=elements.get(i).getElementsByTag("description").text();
                  article.link=elements.get(i).getElementsByTag("link").text();
                  article.position=i;
                  String d = elements.get(i).getElementsByTag("pubDate").text();
                  article.pubDate=convertDateFormat(d,"EEE, d MMM yyyy HH:mm:ss","yyyy年MM月dd日 HH:mm");

                  articles.add(article);
                }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return articles;
    }
    /**
     *  每日文章的 具体内容
     * @param url   文章链接
     * @return    f返回文章内容
     */
    public  static String getArtcleContent(String url)
    {
        String content="";
        Document docs = null;
        try
        {
            docs=Jsoup.parse(new URL(url),5000);
            Elements elements=docs.getElementsByClass("article_text");
            content=elements.text();

        } catch (IOException e)
        {
            e.printStackTrace();
        }


        return content;
    }
    public static String convertDateFormat(String dateTime, String previousFormat,
                                           String destinationFormat) {

        String formattedDateTime = null;

        try {
            DateFormat dateFormat = new SimpleDateFormat(previousFormat, Locale.getDefault());
            Date date = dateFormat.parse(dateTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(destinationFormat, Locale.getDefault());
            formattedDateTime = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDateTime;

    }
}
