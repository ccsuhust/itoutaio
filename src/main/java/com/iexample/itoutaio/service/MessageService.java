package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.CommentDao;
import com.iexample.itoutaio.dao.MessageDao;
import com.iexample.itoutaio.model.Comment;
import com.iexample.itoutaio.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;

    public int addMessage(Message message)
    {
        return messageDao.addMessage(message);
    }
    public List<Message> getConversationDetail(String conversationId, int offset,  int limit){
        return messageDao.getConversationDetail( conversationId,  offset,   limit);
    }
    public List<Message> getConversationList(int userId, int offset,  int limit){
        return  messageDao.getConversationList(userId,offset,limit);
    }
    public int getConversationUnreadCount(int userId, String conversationId)
    {
        return messageDao.getConversationUnreadCount(  userId, conversationId);
    }

}
