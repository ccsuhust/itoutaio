package com.iexample.itoutaio.async.handler;

import com.iexample.itoutaio.async.EventHandler;
import com.iexample.itoutaio.async.EventModel;
import com.iexample.itoutaio.async.EventType;
import com.iexample.itoutaio.model.Message;
import com.iexample.itoutaio.service.MessageService;
import com.iexample.itoutaio.service.UserService;
import com.iexample.itoutaio.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component//spring 起来时候 通过控制反转这些对象可以生成 生成后在EventConsumer中找到所有实现EventHandler对象 这样EventConsumer把他们组织起来，以后有新的Event就知晓用特定的handler处理
public class LoginExceptionHandler implements EventHandler {
    @Autowired UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        if(true)//异常判断
        {
            Message message = new Message();
            message.setFromId(3);
            message.setCreatedDate(new Date());
            message.setToId(model.getActorId());
            message.setContent("登录异常");
            messageService.addMessage(message);
            //Object velocity 渲染可以对于任何对象
            Map<String,Object> map = new HashMap<>();
            map.put("username",model.getExt("username"));
            mailSender.sendWithHTMLTemplate(model.getExt("email"),"登录异常","mails/welcome.html",map);
        }

    }

    //LikeHandler 只关心登录的事件
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
