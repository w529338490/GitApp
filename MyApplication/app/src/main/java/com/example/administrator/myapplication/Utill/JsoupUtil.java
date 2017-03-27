package com.example.administrator.myapplication.Utill;

import android.util.Log;

import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.entity.Gif;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


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

}
