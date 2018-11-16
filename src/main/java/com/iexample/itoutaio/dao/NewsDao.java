package com.iexample.itoutaio.dao;

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
    String SELECT_FIELD = " id,name,password,salt,head_url ";
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,")"," values "," (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);


    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset")int offset, @Param("limit")int limit);
    //News selectByUserIdAndOffset(@Param());

    /*@Update({"update user set password = #{password} where id = #{id}"})
    int updateUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    User selectUser(int id);

    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    int delectUser(int id);*/
}
