package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.model.HostHolder;
import com.iexample.itoutaio.model.Message;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.model.ViewObject;
import com.iexample.itoutaio.service.MessageService;
import com.iexample.itoutaio.service.UserService;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,@RequestParam("toId")  int toId,@RequestParam("content") String content)
    {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setFromId(fromId);
            message.setToId(toId);
            message.setHasRead(0);
            //message.setConversationId(fromId>toId?String.format("%d_%d",toId,fromId):String.format("%d_%d",fromId,toId));
            messageService.addMessage(message);
            //return ToutiaoUtil.getJSONString(msg.getId());
            return ToutiaoUtil.getJSONString(0,"消息发送成功");
        }catch (Exception e)
        {
            logger.error("消息发送失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"消息发送失败");
        }
    }
    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String conversationList(Model model)     {
        try {
            int localUserId= hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
            for(Message msg:conversationList)
            {
                ViewObject vo = new ViewObject();
                vo.set("conversation",msg);
                int targetId = (msg.getFromId()==localUserId?msg.getToId():msg.getFromId());
                User user  = userService.getUser(targetId);
                vo.set("user",user);
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch (Exception e)
        {
            logger.error("获取站内信列表失败"+e.getMessage());
        }
        return "letter";
     }
    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> conversationDetail = messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> viewObjects = new ArrayList<>();
            for(Message msg :conversationDetail)
            {
                ViewObject viewObject = new ViewObject();
                viewObject.set("message",msg);
                User user = userService.getUser(msg.getFromId());
                if(user == null)
                {
                    continue;
                }
                //viewObject.set("userName",user.getName());
                viewObject.set("userId",user.getId());
                viewObject.set("headUrl",user.getHeadUrl());
                viewObjects.add(viewObject);
            }

            //System.out.println(viewObjects.get(0).toString());
            model.addAttribute("messages",viewObjects);
            return "letterDetail";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letterDetail";
    }
}
