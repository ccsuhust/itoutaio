package com.iexample.itoutaio.controller;

import com.iexample.itoutaio.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class indexController {

    @RequestMapping(path = {"/","index"})
    @ResponseBody
    public String index()
    {
        return "hello world";
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
    public String request()
    {

        return "news";
    }
}
