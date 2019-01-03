package com.iexample.itoutaio.async;

import java.util.HashMap;
import java.util.Map;

/*表示放在队列里面的类（存放触发现场数据），表示刚刚发生的事情

    事件类型
    事件发起者
    事件触发对象
    事件触发对象拥有者（因为最终交互都是人与人之间的交互，拥有者对象发消息）
    触发事件（触发现场需要保存的数据） 参数 现场
 */
public class EventModel {
    private EventType type;
    private  int actorId;
    private  int entityType;
    private  int entityId;
    private int entityOwnerId;

    private Map<String,String> exts =new HashMap<>();//既然是触发事件，可以触发各种事件  携带触发事件现场的数据 如参数

    //方便指定字段 需要自己写构造函数 和 exts的get set方法
    public EventModel(){}//json串构造出来都需要需要默认的构造函数
    public EventModel(EventType type){this.type = type;}

    public String getExt(String key)
    {
        return exts.get(key);
    }
    public EventModel setExt(String key,String value)
    {
         exts.put(key,value);
         return this;
    }
    ////////////////

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
