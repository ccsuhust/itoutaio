package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.Message;
import com.iexample.itoutaio.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* public class News {

  private int id;

  private String title;

  private String link;

  private String image;

  private int likeCount;

  private int commentCount;

  private Date createdDate;

  private int userId;*/
@Component
@Mapper
public interface NewsDao {
    String TABLE_NAME = " news ";
    String INSERT_FIELD = " title,link,image,like_count,comment_count,created_date,user_id ";
    String SELECT_FIELD = " id, " + INSERT_FIELD;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,")"," values "," (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);


    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset")int offset, @Param("limit")int limit);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    News selectNews(int id);

    @Update({"update   ",TABLE_NAME," set comment_count=#{comment_count}  where id = #{id}"})
    int updateNewsComment_count(@Param("id") int id,@Param("comment_count") int comment_count);
    @Update({"update   ",TABLE_NAME," set like_count=#{like_count}  where id = #{id}"})
    int updateNewsLike_count(@Param("id") int id,@Param("like_count")int like_count);
    //News selectByUserIdAndOffset(@Param());

    /*@Update({"update user set password = #{password} where id = #{id}"})
    int updateUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    User selectUser(int id);

    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    int delectUser(int id);*/
}
