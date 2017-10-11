package com.eumji.zblog.controller;

import com.eumji.zblog.service.*;
import com.eumji.zblog.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 首页入口controller
 * FILE: com.eumji.zblog.controller.IndexController.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 * AUTHOR: EumJi
 * DATE: 2017/4/8
 * TIME: 15:19
 */
@Controller
public class  PageController {

    @Resource
    private PartnerService partnerService;  //友情链接的service

    @Resource
    private ArticleService articleService; //分钟信息的service

    @Resource
    private CategoryService categoryService;  //分类的service

    @Resource
    private TagService tagService;  //标签的service

    @Resource
    private UserService userService;

    /**
     * 首页
     * 初始化信息
     * 1.标签，分类，文章数量
     * 2.友情链接
     * 3.分类列表 -> 用于文章分类显示
     * 4.时间归档列表
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String home(Model model){
        List<Partner> partnerList = partnerService.findAll();
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        UserInfo userInfo = userService.getUserInfo();
        int articleCount = articleService.getArticleCount();
        List<Map> archiveList = articleService.articleArchiveList();
        int tagCount = tagService.getTagCount();
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        model.addAttribute("archiveList",archiveList);
        model.addAttribute("userInfo",userInfo);
        return "blog/index";
    }

    /**
     * 获取某个标签的分页文章
     * @param model
     * @param pager
     * @param categoryId
     * @return
     */
    @RequestMapping("/categories/details/{categoryId}")
    public String loadArticleByCategory(Model model, Pager pager, @PathVariable Integer categoryId){
        this.loadCommonInfo(model);
        model.addAttribute("categoryId",categoryId);
        List<ArticleCustom> articleList = categoryService.loadArticleByCategory(pager,categoryId);
        if (!articleList.isEmpty()){
            model.addAttribute("articleList",articleList);
            model.addAttribute("pager",pager);
            model.addAttribute("categoryName",articleList.get(0).getCategoryName());
        }
        return "blog/category";
    }

    /**
     * 通过tag获取文章列表
     * @param pager 分页信息
     * @param tagId 标签id
     * @param model 数据视图
     * @return
     */
    @RequestMapping("tags/details/{tagId}")
    public String loadArticleByTag(Pager pager, @PathVariable Integer tagId, Model model){
       this.loadCommonInfo(model);
        model.addAttribute("tagId",tagId);
        List<ArticleCustom> articleList = tagService.loadArticleByTag(pager,tagId);
        if (!articleList.isEmpty()){
            model.addAttribute("articleList",articleList);
            model.addAttribute("pager",pager);
            //2017-05-07修复获取tag名称错误的问题,不应该从articlelist中取,因为每篇文章可能有多个tag
            model.addAttribute("tagName",tagService.getTagById(tagId).getTagName());
        }

        return "blog/tag";
    }


    /**
     * 文章归档列表
     *
     * 2017.5.29 fixed bug 归档的标题错误问题
     * 设置名称出错
     * @param createTime
     * @param pager
     * @param model
     * @return
     */
    @RequestMapping("/createTime/details/{createTime}")
    public String archiveList(@PathVariable String createTime, Pager pager, Model model){
        this.loadCommonInfo(model);
        List<ArticleCustom> articleList = categoryService.loadArticleByArchive(createTime,pager);
        if (articleList != null && !articleList.isEmpty()) {
            model.addAttribute("articleList", articleList);
            model.addAttribute("pager", pager);
            model.addAttribute("createTime", createTime);
        }
        return "blog/archive";
    }

    /**
     * 分类排序 暂时停用
     * @return
     */
    @RequestMapping("/archives")
    @Deprecated
    public String archivesPage(){
        return "blog/archives";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping("/doLogin")
    public String doLogin( User user ,Model model){
        return "login";
    }

    /**跳转到友链展示页面
     * @return
     */
    @RequestMapping("/partner/list")
    public String partnerPage() {
        return "admin/partner/partnerList";
    }


    /**
     * 关于我跳转
     * @param model
     * @return
     */
    @RequestMapping("/about/me")
    public String aboutMe(Model model){
        List<Partner> partnerList = partnerService.findAll();
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        int articleCount = articleService.getArticleCount();
        int tagCount = tagService.getTagCount();
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        return "blog/aboutMe";
    }

    @RequestMapping("/popular")
    public String popularArticle(Model model){
        this.loadCommonInfo(model);
        List<ArticleCustom> articleList = articleService.popularArticle();
        model.addAttribute("articleList",articleList);
        return "blog/popular";
    }

    @RequestMapping("/thymeleaf")
    public String thymeleafPage(){
        return "blog/thymeleaf";
    }

    private void loadCommonInfo(Model model){

        List<Partner> partnerList = partnerService.findAll();
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        UserInfo userInfo = userService.getUserInfo();
        int articleCount = articleService.getArticleCount();
        List<Map> archiveList = articleService.articleArchiveList();
        int tagCount = tagService.getTagCount();
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        model.addAttribute("archiveList",archiveList);
        model.addAttribute("userInfo",userInfo);
    }
}
