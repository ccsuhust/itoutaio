package com.iexample.itoutaio.interceptor;

import com.iexample.itoutaio.dao.LoginTicketDao;
import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.HostHolder;
import com.iexample.itoutaio.model.LoginTicket;
import com.iexample.itoutaio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDao ticketDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandle");
        String ticket = null;
        if(httpServletRequest.getCookies()!=null)
        {
            Cookie cookies[] = httpServletRequest.getCookies();
            for(Cookie cookie:cookies)
            {
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }


        if(ticket != null){ //ticket有可能伪造，需要后台查看
            LoginTicket loginTicket = ticketDao.selectByTicket(ticket);
            if(loginTicket == null
            ||loginTicket.getExpired().before(new Date(new Date().getTime()))
            ||loginTicket.getStatus()>0)//后台没有ticket ticket时间过期 ticket状态为非0
            {
                return true;
            }
            //ticket 合法
            System.out.println("preHandle@");
            User user = userDao.selectUserById(loginTicket.getUserId());
            hostHolder.setUser(user);
            //return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
            if(modelAndView!=null&&hostHolder.getUser()!=null )
            {
                System.out.println("postHandle@");
                modelAndView.addObject("user",hostHolder.getUser());
            }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
        hostHolder.clear(); //不然每次登陆都有变量放在内存
    }
}
