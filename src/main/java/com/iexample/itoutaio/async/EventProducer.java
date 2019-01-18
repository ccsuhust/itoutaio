package com.iexample.itoutaio.async;

import com.alibaba.fastjson.JSONObject;
import com.iexample.itoutaio.controller.MessageController;
import com.iexample.itoutaio.util.JedisAdapter;
import com.iexample.itoutaio.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
//因为EventProducer需要给各种各样的业务用 所以是个service
/*
* 把事件序列化后放到redis队列中
* */
@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    JedisAdapter jedisAdapter;/*接收到的事件放到队列中 队列用jedis队列*/
    public boolean fireEvent(EventModel model)
    {
        try {
          String json = JSONObject.toJSONString(model);
          String key = RedisKeyUtil.getEventQueueKey();
          jedisAdapter.lpush(key,json);
          return true;
        }catch (Exception e)
        {
            logger.error("发送事件失败失败"+e.getMessage());
            return false;
        }
    }



}
