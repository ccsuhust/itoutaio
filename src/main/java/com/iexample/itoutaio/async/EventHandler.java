package com.iexample.itoutaio.async;

import java.util.List;
/*
每个事件handler事件处理是不一样 所以抽象成接口
* */
public interface EventHandler {
    void doHandle(EventModel model);
    //表示关注的EventType接口，所以与此有关的Event都需处理
    List<EventType> getSupportEventTypes();
}
