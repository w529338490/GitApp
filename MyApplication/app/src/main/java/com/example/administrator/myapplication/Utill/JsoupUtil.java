package com.example.administrator.myapplication.Utill;

import android.util.Log;

import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Famous;
import com.example.administrator.myapplication.entity.Gif;
import com.example.administrator.myapplication.entity.Story;
import com.example.administrator.myapplication.entity.Story.StoryCatalog;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
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
public class JsoupUtil
{

    public static ArrayList<String> list_dongtai = new ArrayList<>();
    public static ArrayList<String> list_xiegif = new ArrayList<>();
    public static ArrayList<String> list_gaoxiao = new ArrayList<>();

    public static int getListSize(int type)
    {
        switch (type)
        {
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

    public static String getListItem(int position, int type)
    {
        switch (type)
        {
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
    public static ArrayList<Gif> getGif(String urls, int type)
    {


        ArrayList<Gif> list = new ArrayList<>();
        Document doc = null;
        try
        {

            doc = Jsoup.parse(new URL(urls), 5000);
//            Logger.e(doc.nodeName());

            Elements es_item = doc.getElementsByClass("item");

            for (int i = 0; i < es_item.size(); i++)
            {
                Element et = es_item.get(i).getElementsByTag("h3").first();
                if (et != null)
                {
                    String title = et.getElementsByTag("b").text();
                    String url = es_item.get(i).select("img").first().attr("src");
                    Log.i("jsoup", title + "\t\t" + url + "\n");
                    list.add(new Gif(title, url));

                }

            }
            Elements es_page = doc.getElementsByClass("page").first().getElementsByTag("select").first().getElementsByTag("option");
            for (int i = 0; i < es_page.size(); i++)
            {
                Element et = es_page.get(i);
                if (et != null)
                {
                    switch (type)
                    {
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

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @param url 每日名言 爬取;
     */
    public static ArrayList<Famous> getFamous(String url)
    {
        ArrayList<Famous> list_famous = new ArrayList<>();
        String Famous[] = new String[0];
        Document docs = null;
        try
        {
            docs = Jsoup.parse(new URL("http://www.diyifanwen.com/tool/jingdianmingyan/1251421170040936.htm"), 5000);
            Element element = docs.getElementById("ArtContent");
            Elements es = element.getElementsByTag("p");

            //  String strs[]=es.get(0).text().split(" ");

            for (int j = 0; j < es.size(); j++)
            {
                for (String str : es.get(j).text().split(" "))
                {
                    com.example.administrator.myapplication.entity.Famous famous = new Famous();
                    if (str.length() <= 16 && str.length() > 6)
                    {
                        famous.setFamous(str);
                        list_famous.add(famous);
                    }
                }
            }

        } catch (IOException e)
        {
            Logger.e("Jsoup->getFamous:", e.getMessage());
            e.printStackTrace();
        }

        return list_famous;
    }

    /**
     * 每日一文。爬取http://w.ihx.cc/meiriyiwen/1434.html
     * http://w.ihx.cc/category/meiriyiwen
     * https://meiriyiwen.com/
     */


    public static ArrayList<Article> getArticle()
    {
        ArrayList<Article> articles = new ArrayList<>();
        Document docs = null;
        try
        {
            docs = Jsoup.parse(new URL("http://w.ihx.cc/category/meiriyiwen/feed"), 10000);
            Elements elements = docs.getElementsByTag("item");
            for (int i = 0; i < elements.size(); i++)
            {
                Article article = new Article();
                //  Logger.e(elements.get(i).getElementsByTag("a").first().attr("href"));
//                Logger.e(elements.get(i).getElementsByTag("title").outerHtml());
//                Logger.e(elements.get(i).getElementsByTag("link").outerHtml());
//                Logger.e(elements.get(i).getElementsByTag("pubDate").outerHtml());
//                Logger.e(elements.get(i).getElementsByTag("description").outerHtml());
//                Logger.e(elements.get(i).getElementsByTag("category").eq(2).outerHtml());

                article.author = elements.get(i).getElementsByTag("category").eq(2).text();
                article.tittle = elements.get(i).getElementsByTag("title").text();
                article.description = elements.get(i).getElementsByTag("description").text();
                article.link = elements.get(i).getElementsByTag("link").text();

                article.position = i;
                String d = elements.get(i).getElementsByTag("pubDate").text();
                article.pubDate = convertDateFormat(d, "EEE, d MMM yyyy HH:mm:ss", "yyyy年MM月dd日 HH:mm");

                articles.add(article);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * 每日文章的 具体内容
     *
     * @param url 文章链接
     * @return f返回文章内容
     */
    public static String[] getArtcleContent(String url)
    {
        String[] content = new String[3];
        Document docs = null;
        try
        {

            docs = Jsoup.parse(new URL(url), 5000);
            Elements elements = docs.getElementsByClass("article_text");

            String tille = docs.getElementsByTag("title").text().toString().trim();
            Logger.e(tille.replace("|", "=").split("=")[0].split("--")[0]);
            content[0] = tille.replace("|", "=").split("=")[0].split("--")[0];
            content[1] = tille.replace("|", "=").split("=")[0].split("--")[1];
            content[2] = elements.outerHtml();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }


    public static String convertDateFormat(String dateTime, String previousFormat,
                                           String destinationFormat)
    {
        String formattedDateTime = null;
        try
        {
            DateFormat dateFormat = new SimpleDateFormat(previousFormat, Locale.getDefault());
            Date date = dateFormat.parse(dateTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(destinationFormat, Locale.getDefault());
            formattedDateTime = simpleDateFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return formattedDateTime;
    }

    /**
     * 爬取小说
     * http://book.zongheng.com/rank/male/r14/c1/q0/1.html  // 小说_ 奇幻玄幻</br>
     * http://book.zongheng.com/rank/male/r7/c3/q0/1.html // 小说_ 武侠仙侠</br>
     * http://book.zongheng.com/rank/male/r7/c6/q0/1.html // 小说_ 历史军事</br>
     * http://book.zongheng.com/rank/male/r7/c9/q0/1.html // 小说_都市娱乐</br>
     */
    public static ArrayList<Story> getStory(String url)
    {
        Document docs = null;
        ArrayList<Story> storyList = new ArrayList<>();
        try
        {
            docs = Jsoup.parse(new URL(url), 5000);
            Elements elts = docs.getElementsByClass("main_con");
//            Logger.e(elts.html());

//            Logger.e("一共有" + elts.get(0).getElementsByTag("li").size()+"个节点");
            for (int i = 0; i < elts.get(0).getElementsByTag("li").size(); i++)
            {
                Element et = elts.get(0).getElementsByTag("li").get(i); // 获取根节点的元素对象
                Story story = new Story();
                if (et.text() == null || "".equals(et.text()))
                {
                    continue;
                } else
                {
                    story.setType(et.getElementsByClass("kind").text());
                    story.setTitle(et.getElementsByClass("chap").get(0).getElementsByClass("fs14").attr("title"));
                    story.setUri(et.getElementsByClass("chap").get(0).getElementsByClass("fs14").attr("href"));
                    story.setContent(et.getElementsByClass("limit").get(0).text());
                    story.setMark(et.getElementsByClass("mark").text());
                    story.setHot(ConvertUtils.strToInt(et.getElementsByClass("bit").text()));
                    story.setAuthor(et.getElementsByClass("author").text());
                    story.setIndex(et.getElementsByClass("author").get(0).getElementsByTag("a").attr("href"));
                    story.setUpdateTime(et.getElementsByClass("time").text());
                    storyList.add(story);
                }
//                Logger.e("猜猜拿到了什么：" + storyList.toString());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return storyList;
    }

    /**
     * 小说概览
     *
     * @param url
     * @return
     */
    public static Story getStoryDetail(String url)
    {
        Document docs = null;
        Story story = new Story();
        try
        {
            docs = Jsoup.parse(new URL(url), 5000);
            Elements elts = docs.getElementsByClass("book_main");
//            Logger.e(elts.outerHtml());
            Element et = elts.get(0).getElementsByTag("p").get(0);
            story.setTitle(et.getElementsByTag("img").get(0).attr("alt"));
            story.setStoryPic(et.getElementsByTag("img").get(0).attr("src"));
            story.setAuthor(elts.get(0).getElementsByClass("booksub").get(0).getElementsByTag("a").get(0).text());
            story.setType(elts.get(0).getElementsByClass("booksub").get(0).getElementsByTag("a").get(1).text());
            story.setWords(elts.get(0).getElementsByClass("booksub").get(0).getElementsByTag("span").text());
            story.setContent(elts.get(0).getElementsByClass("info_con").get(0).getElementsByTag("p").text());
            story.setReadUrl(elts.get(0).getElementsByClass("book_btn").get(0).getElementsByTag("a").get(0).attr("href"));
            story.setCatalogList(getStoryCatalogs(elts.get(0).getElementsByClass("book_btn").get(0).getElementsByClass("btn_as list").get(0).getElementsByTag("a").get(0).attr("href")));
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return story;
    }

    /**
     * 目录
     *
     * @param url
     * @return
     */
    public static ArrayList<Story.StoryCatalog> getStoryCatalogs(String url)
    {
        Document docs = null;
        ArrayList<Story.StoryCatalog> catalogs = new ArrayList<>();
        try
        {
            docs = Jsoup.parse(new URL(url), 5000);
            Elements elts = docs.getElementsByClass("booklist tomeBean");
            Elements catalogList = elts.get(0).getElementsByTag("td");
            for (int i = 0; i < catalogList.size(); i++)
            {
                StoryCatalog catalog = new StoryCatalog();
                catalog.setCatalog(catalogList.get(i).text());
                catalog.setUrl(catalogList.get(i).getElementsByTag("a").get(0).attr("href"));
                catalogs.add(catalog);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
//        Logger.e(elts.outerHtml());
        return catalogs;
    }

    /**
     * 小说正文
     */
    public static Story getStoryText(String url)
    {
        Document docs = null;
        StringBuffer sb = new StringBuffer("\u3000\u3000");
        Story story = new Story();
        try
        {
            docs = Jsoup.parse(new URL(url), 5000);
            Elements els = docs.getElementsByClass("tc quickkey");
            Elements elements = els.get(0).getElementsByTag("a");
            for (int i = 0; i < elements.size(); i++)
            {
                switch (elements.get(i).text())
                {
                    case "上一章":
                        story.setPrevious(elements.get(i).attr("href")); // 上一章
                        break;
                    case "下一章":
                        if (!"javascript:void(0);".equals(elements.get(i).attr("href")) || !elements.get(i).attr("href").contains("javascript"))
                        {
                            story.setNext(elements.get(i).attr("href")); // 下一章
                        }
                        break;
                    default:
                        break;
                }
            }
            // 小说正文
            Element el = docs.getElementById("chapterContent");
            for (int i = 0; i < el.getElementsByTag("p").size(); i++)
            {
                sb.append(el.getElementsByTag("p").get(i).text() + "\n");
            }
            story.setText(sb.toString()); // 小说正文
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return story;
    }
}
