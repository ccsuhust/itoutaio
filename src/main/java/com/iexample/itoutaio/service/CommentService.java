package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.CommentDao;
import com.iexample.itoutaio.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    public int addComment(Comment comment)
    {
        return commentDao.addComment(comment);
    }
    public List<Comment> selectbyEntity(int entity_id,int entity_type)
    {
        return commentDao.selectbyEntity(entity_id,entity_type);
    }
    public int getCommentCount(int entity_id,int entity_type)
    {
        return commentDao.getCommentCount(entity_id,entity_type);
    }
    //List<Comment> selectbyEntity(@Param("entity_id") int entity_id, @Param("entity_type") int entity_type);
    //int getCommentCount(@Param("entity_id") int entity_id,@Param("entity_type") int entity_type);
}
