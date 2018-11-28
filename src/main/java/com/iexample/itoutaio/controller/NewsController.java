package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.model.HostHolder;
import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.service.NewsService;
import com.iexample.itoutaio.service.TecentService;
import com.iexample.itoutaio.util.ToutiaoUtil;
import jdk.internal.org.objectweb.asm.commons.TryCatchBlockSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@Controller
public class NewsController {
    @Autowired
    NewsService newsService;
    @Autowired
    TecentService tecentService;
    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(path = {"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file")MultipartFile file)
    {
        try {
            //file.transferTo();
           // String fileUrl = newsService.saveImage(file);
            String fileUrl =  tecentService.uploadFile(file);
            //System.out.println(file.getOriginalFilename()+"%"+file.getName()+"%"+file.getContentType());
            if(fileUrl == null)
            {
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);
        }catch (Exception e)
        {
            logger.error("上传图片失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传图片失败");
        }
    }
    /*@RequestMapping(path = {"/downloadImage/"},method = {RequestMethod.GET})
    @ResponseBody
    public String downloadImage()
    {
        try {
            tecentService.downloadFile();
            String fileUrl ="下载图片成功";
            return ToutiaoUtil.getJSONString(0,fileUrl);
        }catch (Exception e)
        {
            logger.error("上传图片失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传图片失败");
        }
    }*/

    @RequestMapping(path = {"/image"},method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name")String imageName,
                           HttpServletResponse response)
    {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR+imageName))
            ,response.getOutputStream());
        }catch (Exception e)
        {
            logger.error("下载图片失败"+e.getMessage());
        }
    }
    @RequestMapping(path = {"/news/{newsId}"},method = {RequestMethod.GET})
    public String newsDetail() {
        {
            return "detail";
        }
    }
    @RequestMapping(path = {"/user/addNews/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("link") String link,
                          @RequestParam("title") String titile,
                          @RequestParam("image") String image)
    {
        try {
            News news = new News();
            news.setImage(image);
            news.setTitle(titile);
            news.setLink(link);
            news.setCreatedDate(new Date());
            if(hostHolder.getUser()!=null)
            {
                news.setUserId(hostHolder.getUser().getId());
            }
            else news.setUserId(1);//默认为1
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);


        }catch (Exception e)
        {
            logger.error("添加资讯失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"发布失败");
        }
    }

}
