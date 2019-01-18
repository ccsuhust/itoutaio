package com.iexample.itoutaio.controller;
import com.iexample.itoutaio.model.*;
import com.iexample.itoutaio.service.*;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsService newsService;
    @Autowired
    TecentService tecentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;

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
    public String newsDetail(@PathVariable("newsId") int newsId,Model model) {
        {
            int localUserId = hostHolder.getUser()!=null?hostHolder.getUser().getId():0;
            if(localUserId !=0)
            {
                model.addAttribute("like",likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS,newsId));//当前资讯 当前用户看的状态
            }else {
                model.addAttribute("like",0);
            }
            News news = newsService.getById(newsId);
            model.addAttribute("news",news);
            User user = userService.getUser(news.getUserId());
            model.addAttribute("owner",user);
            List<Comment> comments = commentService.selectbyEntity(newsId, EntityType.ENTITY_NEWS);
            List<ViewObject> commentsVOs = new ArrayList<>();
            if(comments!=null)
            {
                for(Comment com :comments){
                    ViewObject viewObject = new ViewObject();
                    viewObject.set("comment",com);
                    viewObject.set("user",userService.getUser(com.getUserId()));
                    commentsVOs.add(viewObject);
                }
                model.addAttribute("comments",commentsVOs);
            }
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
    @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        Comment comment = new Comment();
        try {
           // comment.setId(100);
          comment.setStatus(0);
          comment.setContent(content);
          comment.setCreatedDate(new Date());
          comment.setEntityId(newsId);
          comment.setEntityType(EntityType.ENTITY_NEWS);
          comment.setUserId(hostHolder.getUser().getId());
          System.out.println(comment.toString());
          System.out.println(commentService.addComment(comment));


          //更新评论数量
          int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
          newsService.updateNewsComment_count(comment.getEntityId(),count);
      }catch (Exception e)
      {
          //e.getMessage();
          logger.error("添加评论失败"+e.getMessage());;
      }
      return "redirect:/news/"+comment.getEntityId();
        //return "home";
    }
}
