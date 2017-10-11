package com.eumji.zblog.controller.admin;
import com.eumji.zblog.service.UserService;
import com.eumji.zblog.vo.PhotoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * 上传图片的controller
 * Created by eumji on 17-5-31.
 */
@RestController
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(AdminArticleController.class);


    @Resource
    private UserService userService;



    @RequestMapping("/imageUpload")
    public PhotoResult imageUpload(@RequestParam(value = "editormd-image-file",required = true) MultipartFile file){
        PhotoResult result = null;
        //设置filename
        // String filename = new Random().nextInt(10000)+file.getOriginalFilename();
        try {
            File files = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")+file.getOriginalFilename());
            file.transferTo(files);

            return result;
        } catch (IOException e) {
           logger.error(e.getMessage());
           return new PhotoResult(0,"","IO异常上传失败！！！");
        }
    }

    /**
     * 头像修改
     * @param request 获取session的request
     * @param avatar_src 图片路径
     * @param avatar_data 图片裁剪的内容
     * @param file 图片
     * @return
     */

}
