package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.model.ViewObject;
import com.iexample.itoutaio.service.NewsService;
import com.iexample.itoutaio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class homeController {

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

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

    @RequestMapping(path = {"/","/index"})
    public String index(Model model)
    {

        model.addAttribute("vos",addAttribute(0,0,10));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"})
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
