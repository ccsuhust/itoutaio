package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.*;
import java.net.HttpCookie;
import java.util.*;

//@Controller
public class indexController {
    private static final Logger logger = LoggerFactory.getLogger(indexController.class);
    @Autowired
    ToutiaoService toutiaoService;


    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(HttpSession hs)
    {


        //return "hello world"+hs.getAttribute("session");
        logger.info("Visit index");
        return  toutiaoService.say();
    }

    @RequestMapping(path = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam(value = "key" ,required = false) String key)
    {
        System.out.println("key");
        if(key!=null&&key.equals("admin"))
        {
            return  "hello admin";
        }
        throw new IllegalArgumentException("key error");

    }
    @ResponseBody
    @ExceptionHandler

    public String error(Exception e)
    {
        return e.getMessage();
    }
    @RequestMapping(value = "/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") String userId,
                          @RequestParam(value = "key" ,defaultValue = "default") Integer key,
                          @RequestParam(value = "type" ,defaultValue = "default") Integer type
    )
    {
        return String.format("%s %s %d %d\n",groupId,userId,key,type);
    }
    @RequestMapping(value = "/vm")
    public String vm(Model M)
    {
        M.addAttribute("val","hello");
        List<String> list = Arrays.asList(new String[]{"11","22","44"});
        Map<String,String> map = new HashMap<>();
//        map.put("a","11");
        map.put("b","22");
        M.addAttribute("list",list);
        //M.addAttribute("map",map);
        M.addAttribute("user",new User("xypmhyt"));
        return "news";
    }
    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response
    )
    {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements())
        {
            String head = headerNames.nextElement();
            String value = request.getHeader(head);
            sb.append(head+":"+value+"<br>");
        }
        //if(request.getCookies()!=null)
        for(Cookie ck :request.getCookies())
        {
            sb.append("Cookie:"+ck.getName()+":"+ck.getValue()+"<br>");
        }
        sb.append("getMethod "+request.getMethod()+"<br>");
        sb.append("getPathInfo "+request.getPathInfo()+"<br>");
        sb.append("getQueryString "+request.getQueryString()+"<br>");
        sb.append("getRequestURI "+request.getRequestURI()+"<br>");

        return sb.toString();
    }
    @RequestMapping(value = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession hs

    )
    {
        /*RedirectView rv = new RedirectView("/",true);
        if(code == 301)
        {
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return  rv;*/
        hs.setAttribute("session","nihao");
        return "redirect:/";
    }

    @RequestMapping(value = "/response")
    @ResponseBody
    public String response(@CookieValue(value = "cook", defaultValue="default") String cook,
                           @RequestParam(value = "key" ,defaultValue="key") String key,
                           @RequestParam(value = "value",defaultValue="value") String value,
                           HttpServletResponse response
    )
    {
        Cookie c = new Cookie(key,value);
        response.addCookie(c);
        response.addHeader(key,value);
        return "from   " + cook;
    }

}
