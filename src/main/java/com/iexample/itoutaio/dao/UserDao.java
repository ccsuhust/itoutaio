package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/*
* CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '',
  `password` varchar(128) NOT NULL DEFAULT '',
  `salt` varchar(32) NOT NULL DEFAULT '',
  `head_url` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL DEFAULT '',
  `link` varchar(256) NOT NULL DEFAULT '',
  `image` varchar(256) NOT NULL DEFAULT '',
  `like_count` int NOT NULL,
  `comment_count` int NOT NULL,
  `created_date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
@Component
@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELD = " name,password,salt,head_url ";
    String SELECT_FIELD = " id,name,password,salt,head_url ";
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,")"," values "," (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Update({"update user set password = #{password} where id = #{id}"})
    int updateUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    User selectUser(int id);

    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    int delectUser(int id);



}
