package com.iexample.itoutaio.async;

/**
 * 0 表示喜欢
 * 1表示评论
 * 2 登录
 * 3 邮件
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);
    private int value;
    EventType(int value){this.value = value;}
    public int getValue(){return value;}
}
