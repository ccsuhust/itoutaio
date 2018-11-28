package com.iexample.itoutaio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SettingController {
    @RequestMapping("/setting")
    public String Setting(Model model)
    {
        model.addAttribute("test","Setting");
        return  "test";
    }
}
