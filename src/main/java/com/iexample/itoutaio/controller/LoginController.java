package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.async.EventModel;
import com.iexample.itoutaio.async.EventProducer;
import com.iexample.itoutaio.async.EventType;
import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.model.ViewObject;
import com.iexample.itoutaio.service.NewsService;
import com.iexample.itoutaio.service.UserService;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    EventProducer eventProducer;


    /*注册方法
    * 参数：用户名 密码 是否记住密码
    * 返回map<String ,String>*/
    @RequestMapping(path = {"/reg"})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rember" ,defaultValue = "0")  int rememberme,
                      HttpServletResponse response)
    {
        try {
            Map<String,Object> map = new HashMap<>();
            map = userService.register(username,password);
            /*注册成功*/
            if(map.containsKey("ticket"))
            {
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){//记住密码 如不写 浏览器关闭后结束
                    cookie.setMaxAge(1000*3600*24);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0,"注册成功");
            }
            else {
                StringBuilder sb = new StringBuilder();
                if(map.get("msgname")!=null)
                {
                    sb.append(map.get("msgname"));
                }
                if(map.get("msgpwd")!=null)
                {
                    sb.append(map.get("msgpwd"));
                }
                return ToutiaoUtil.getJSONString(1,"注册异常@" + sb.toString());
            }
        }catch (Exception e){/*系统异常导致*/
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJSONString(2,"注册异常");
        }

    }
    /*登录方法
     * 参数：用户名 密码 是否记住密码
     * 返回map<String ,String>*/
    @RequestMapping(path = {"/login"})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rember" ,defaultValue = "0")  int rememberme,HttpServletResponse response) {
        try {
            Map<String, Object> map ;
            map = userService.login(username, password);//service登录成功 ，返回带ticket的map
            /*登录成功*/
            if (map.containsKey("ticket")) {
               // model.addAttribute("user",map.get("user"));
                //System.out.println(map.get("user"));
                //model.addAttribute("test","hello");
                //return ToutiaoUtil.getJSONString(0, "登录成功","user",(User)map.get("user"));
                //return ToutiaoUtil.getJSONString(0, "登录成功","user",(User)map.get("user"))
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){//记住密码 如不写 浏览器关闭后结束
                    cookie.setMaxAge(1000*3600*24);
                }
                response.addCookie(cookie);

                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setActorId((int)map.get("userId"))
                        .setExt("username",username).setExt("email","**@qq.com"));//发送邮件的目标地址

                return ToutiaoUtil.getJSONString(0, "登录成功");
                //return "home";
            } else {
                //return ToutiaoUtil.getJSONString(1,"登录异常@");
                StringBuilder msg = new StringBuilder();
                if (map.containsKey("msgname")) {
                    msg.append("用户名error ");
                    msg.append(map.get("msgname"));
                    String str =msg.toString();
                    return ToutiaoUtil.getJSONString(1, str);
                }
                if (map.containsKey("msgpwd")) {
                    msg.append("密码error ");
                    return ToutiaoUtil.getJSONString(1, msg.append(map.get("msgpwd")).toString());
                }
                return ToutiaoUtil.getJSONString(1, "LoginController");
            }
        } catch (Exception e) {/*系统异常导致*/
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(2, "登录异常%");
        }
    }
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
