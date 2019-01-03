package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.async.EventModel;
import com.iexample.itoutaio.async.EventProducer;
import com.iexample.itoutaio.async.EventType;
import com.iexample.itoutaio.model.EntityType;
import com.iexample.itoutaio.model.HostHolder;
import com.iexample.itoutaio.service.LikeService;
import com.iexample.itoutaio.service.NewsService;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LikeController {
    @Autowired HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    NewsService newsService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path = {"/like"} ,method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId)
    {
        System.out.println("like controller");
        int userId = hostHolder.getUser().getId();
        //该资讯喜欢集合加入该用户 redis操作
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateNewsLike_count(newsId,(int) likeCount);


        /*异步处理框架*/
        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
        .setEntityId(newsId)
        .setEntityType(EntityType.ENTITY_NEWS)
        .setEntityOwnerId(newsService.getById(newsId).getUserId()));


        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
    @RequestMapping(path = {"/dislike"} ,method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId)
    {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.disLike(userId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateNewsLike_count(newsId,(int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
