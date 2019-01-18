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
/*
* InitializingBean接口
InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。

当spring实例化bean时，它会查找类似ApplicationContextAware和的几个接口InitializingBean。如果找到它们，则调用这些方法。例如（非常简化）
Class<?> beanClass = beanDefinition.getClass();
Object bean = beanClass.newInstance();
if (bean instanceof ApplicationContextAware) {
    ((ApplicationContextAware) bean).setApplicationContext(ctx);
}
请注意，在较新的版本中，最好使用注释，而不是实现特定于Spring的接口。现在你可以简单地使用：
@Inject // or @Autowired
private ApplicationContext ctx;
* */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    @Autowired
    JedisAdapter jedisAdapter;

    @Override//初始化工作
    public void afterPropertiesSet() throws Exception {

        //<T> Map<String, T> getBeansOfType(Class<T> var1) throws BeansException;
        //beans = {<"1","likeHandler"> <"2","LoginExceptionHandler">}
        Map<String ,EventHandler> beans =applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null)
        {
            //只关心EventHandler 把之前每个事件处理者（key 如likeHandler） 与 事件类型集合(value 如like like1) 一一对应 转化为 每个事件类型（key 如like） 与 与此相关的事件处理者集合（value 如 likeHandler like2Handler） 一一对应
            //处理原因是因为 redis事件队列保存的每个事件 EventConsumer根据收到的事件类型选取对应相关的事件处理者

            //entry = {<"1","likeHandler"> <"2","LoginExceptionHandler">}
            for(Map.Entry<String,EventHandler> entry:beans.entrySet())
            {
                //eg likeHandler eventTypes = {<like>}
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();//某个具体的EventHandler（如likeHandler）支持的EventType集合（like）
                for(EventType type:eventTypes)//上面的EventType集合中元素作为key ，把所有与此相关的EventHandler放入value结合中
                {
                    if(!config.containsKey(type))
                    {
                        config.put(type,new ArrayList<>());
                    }
                    //config = {<like,[likeHandler ...]>}
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
