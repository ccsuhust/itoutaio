package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.LoginTicketDao;
import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.LoginTicket;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao ticketDao;

    Random random = new Random();
    public void logout(String ticket)
    {
        ticketDao.updateStatus(ticket,1);
    }
    public User getUser(int id)
    {
        return userDao.selectUserById(id);
    }
    public Map register(String username, String password)
    {
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(username))
        {
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msgpwd","密码不能为空");
            return map;
        }
        if(userDao.selectUserByName(username)!=null)
        {
            map.put("msgname","用户名已注册");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));/*10000访问非法*/
        user.setSalt(UUID.randomUUID().toString().substring(0,8));//6b388c60-1dee-4665-ad44-6992a75ba3d7格式取前8位作为盐
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);
        //注册成功 直接登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    public Map login(String username, String password)
    {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username))
        {
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user = userDao.selectUserByName(username);
        if(user == null)
        {
            map.put("msgname","用户名不存在");
            return map;
        }

        if((ToutiaoUtil.MD5(password+user.getSalt())).equals(user.getPassword()))
        {
            String ticket = addLoginTicket(user.getId());
            map.put("user",user);
            map.put("ticket",ticket);
            return map;
        }
        else{
            map.put("msgpwd","密码不正确");
            return map;
        }

    }
    private String addLoginTicket(int userID)
    {
        LoginTicket ticket = new LoginTicket();
        Date date = new Date();
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        ticket.setStatus(0);
        ticket.setUserId(userID);
        ticket.setExpired(new Date(date.getTime()+1000*3600*5));
        ticketDao.addTicket(ticket);
        return ticket.getTicket();
    }
}
