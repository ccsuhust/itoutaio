package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
/*
* private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
* */
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
    User selectUserById(int id);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where name = #{name}"})
    User selectUserByName(String name);

    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    int delectUser(int id);



}
