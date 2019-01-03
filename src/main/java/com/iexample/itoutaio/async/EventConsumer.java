package com.iexample.itoutaio.async;

import com.alibaba.fastjson.JSON;
import com.iexample.itoutaio.util.JedisAdapter;
import com.iexample.itoutaio.util.RedisKeyUtil;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    @Autowired
    JedisAdapter jedisAdapter;

    @Override//初始化工作
    public void afterPropertiesSet() throws Exception {

        Map<String ,EventHandler> beans =applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null)
        {
            for(Map.Entry<String,EventHandler> entry:beans.entrySet())
            {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType type:eventTypes)
                {
                    if(!config.containsKey(type))
                    {
                        config.put(type,new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        System.out.println("EventConsumer Controller -->afterPropertiesSet");
        //开个线程处理阻塞队列 不断的处理数据（从队列中取事件），然后找到用不同的handler处理，然后处理掉
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0,key);//timeout ：时间延长，一直等待（0）
                    for(String message:events){
                        if(message.equals(key))
                        {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message,EventModel.class);
                        if(!config.containsKey(eventModel.getType()))
                        {
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler handler:config.get(eventModel.getType()))
                        {
                            handler.doHandle(eventModel);
                        }
                    }

                }
            }
        });
        thread.start();
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException  {
        this.applicationContext = applicationContext;
    }
}
