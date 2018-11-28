package com.iexample.itoutaio.dao;

import com.iexample.itoutaio.model.LoginTicket;
import com.iexample.itoutaio.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
/*
*  private int id;
    private int userId;
    private Date expired;
    private int status;// 0有效，1无效
    private String ticket;*/
@Component
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELD = " user_id,expired,status,ticket ";
    String SELECT_FIELD = " id ,"+ INSERT_FIELD ;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ",SELECT_FIELD," from ", TABLE_NAME , "where ticket = #{ticket}" })
    LoginTicket selectByTicket(String ticket);
    //update user set status = * where
    @Update({"update ",TABLE_NAME," set status= #{status} where ticket = #{ticket}  " })
    void updateStatus(@Param("ticket") String ticket ,@Param("status") int status);
}
