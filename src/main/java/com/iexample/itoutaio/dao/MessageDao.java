package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.Comment;
import com.iexample.itoutaio.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELD = " from_id,to_id,content,created_date,has_read,conversation_id ";
    String SELECT_FIELD = " id, " + INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD," ) values  (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    public int addMessage(Message message);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where conversation_id = #{conversationId}  order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from "+TABLE_NAME+" where has_read = 0 and to_id = #{userId} and conversation_id = #{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    //select * ,count(id) from (select * from message where from_id=11 or to_id=11 ) tt  group by conversation_id ORDER BY conversation_id desc limit #{offset},#{limit}
    //@Select({"select ",SELECT_FIELD," count(id) as id from (select * from "+TABLE_NAME+" where from_id=#{userId} or to_id=#{userId} order by id desc)  tt group by conversation_id ORDER BY conversation_id desc limit #{offset},#{limit} "})
    @Select({"select "+INSERT_FIELD+" ,count(id) as id from (select * from "+TABLE_NAME+" where from_id=#{userId} or to_id=#{userId} ) tt  group by conversation_id ORDER BY conversation_id desc limit #{offset},#{limit} "})
    List<Message> getConversationList(@Param("userId") int userId,@Param("offset")int offset,@Param("limit")int limit);


    /*@Select({"select count(id) from ",TABLE_NAME," where entity_id = #{entity_id} and entity_type = #{entity_type}"})
    public int getCommentCount(@Param("entity_id") int entity_id,@Param("entity_type") int entity_type);*/
}
