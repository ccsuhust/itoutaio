
package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.*;
import com.iexample.itoutaio.service.LikeService;
import com.iexample.itoutaio.service.NewsService;
import com.iexample.itoutaio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    NewsService newsService;
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;

    /*通过ID得到消息列表 若ID为0 展示所有人的消息*/
    private List<ViewObject>  addAttribute(int userID, int offset, int limit){
        List<ViewObject> vos = new ArrayList<>();
        int localUserId = hostHolder.getUser()!=null?hostHolder.getUser().getId():0;
        List<News> lastNews = newsService.getLastNews(userID, 0, 10);
        for(News news:lastNews)
        {
            User user = userService.getUser(news.getUserId());
            ViewObject vo = new ViewObject();
            vo.set("user",user);
            vo.set("news",news);
            //登录状态 设置首页喜欢或不喜欢状态
            if(localUserId !=0)
            {
                vo.set("like",likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS,news.getId()));//当前资讯 当前用户看的状态
            }else {
                vo.set("like",0);
            }
            vos.add(vo);
        }
        return vos;
    }

    /*首页 所有ID消息列表*/
    @RequestMapping(path = {"/","/index"})
    public String index(Model model, @RequestParam(value = "pop",defaultValue = "0") int pop)
    {
        /*将*/
        model.addAttribute("vos",addAttribute(0,0,10));
        model.addAttribute("pop",pop);//前端传过来
        return "home";
    }
    /*首页 指定ID消息列表*/
    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET})
    public String user(Model model,
                       @PathVariable("userId") int userID)
    {
        model.addAttribute("vos",addAttribute(userID,0,10));

        return "home";
    }
//    public String index(int userId,int offset,int limit)
//    {
//        return "head";
//    }
}

