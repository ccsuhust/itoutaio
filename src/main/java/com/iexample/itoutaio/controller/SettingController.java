package com.iexample.itoutaio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SettingController {
    @RequestMapping("/setting")
    @ResponseBody
    public String Setting()
    {
        return  "SettingController";
    }
}
