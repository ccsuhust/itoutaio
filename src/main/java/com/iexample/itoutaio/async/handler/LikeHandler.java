package com.iexample.itoutaio.async.handler;

import com.iexample.itoutaio.async.EventHandler;
import com.iexample.itoutaio.async.EventModel;
import com.iexample.itoutaio.async.EventType;
import com.iexample.itoutaio.model.Message;
import com.iexample.itoutaio.service.MessageService;
import com.iexample.itoutaio.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component//spring 起来时候 通过控制反转这些对象可以生成 生成后在EventConsumer中找到所有实现EventHandler对象 这样EventConsumer把他们组织起来，以后有新的Event就知晓用特定的handler处理
public class LikeHandler implements EventHandler {
    @Autowired UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {

        System.out.println("Liked。。。。。。。。。。。。");
        Message message  = new Message();
        message.setFromId(3);//系统管理员发送发
        //message.setToId(model.getActorId());
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setContent("用户"+ userService.getUser(model.getActorId()).getName()+"赞了你的资讯，http://127.0.0.1:8080/new/"+model.getEntityId());
        messageService.addMessage(message);
    }

    //LikeHandler 只关心点赞的事件
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }


}
