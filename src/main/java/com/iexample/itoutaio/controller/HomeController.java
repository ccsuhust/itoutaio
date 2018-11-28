
package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.HostHolder;
import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.model.ViewObject;
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

    /*通过ID得到消息列表 若ID为0 展示所有人的消息*/
    private List<ViewObject>  addAttribute(int userID, int offset, int limit){
        List<ViewObject> vos = new ArrayList<>();
        List<News> lastNews = newsService.getLastNews(userID, 0, 10);
        for(News news:lastNews)
        {
            User user = userService.getUser(news.getUserId());
            ViewObject vo = new ViewObject();
            vo.set("user",user);
            vo.set("news",news);
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

