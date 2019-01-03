package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.Comment;
import com.iexample.itoutaio.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*`id` INT NOT NULL AUTO_INCREMENT,
        `content` TEXT NOT NULL,
        `user_id` INT NOT NULL,
        `entity_id` INT NOT NULL,
        `entity_type` INT NOT NULL,
        `created_date` DATETIME NOT NULL,
        `status` INT NOT NULL DEFAULT 0,
        */
@Mapper
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELD = " content,user_id,entity_id,entity_type,created_date,status ";
    String SELECT_FIELD = " id, " + INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD," ) values  (#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    public int addComment(Comment comment);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where entity_id = #{entity_id} and entity_type = #{entity_type} order by created_date desc"})
    public List<Comment> selectbyEntity(@Param("entity_id") int entity_id,@Param("entity_type") int entity_type);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id = #{entity_id} and entity_type = #{entity_type}"})
    public int getCommentCount(@Param("entity_id") int entity_id,@Param("entity_type") int entity_type);
}
