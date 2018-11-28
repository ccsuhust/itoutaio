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
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDao ticketDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(hostHolder.getUser()!=null){
            return true;
        }
        else {
            httpServletResponse.sendRedirect("/?pop=1");
            return false ;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
