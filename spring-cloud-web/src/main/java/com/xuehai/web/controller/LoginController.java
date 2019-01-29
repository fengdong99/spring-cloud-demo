package com.xuehai.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;

/**
 * Created by 39450 on 2018/10/1.
 */
//@RestController
@Controller
public class LoginController {


    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("id","123456");
        return "main/index";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("id","123456");
        return "login";
    }

    @RequestMapping("/login2")
    public String login2(Model model){
        model.addAttribute("id","123456");
        return "index";
    }

    @RequestMapping("/login1")
    public ModelAndView login1(Model model){
        model.addAttribute("id","123456");
        return new ModelAndView("login");
    }

    @RequestMapping({"/index/{id}"})
    @ResponseBody
    public String index(@PathVariable  String id){
        return "hi! " + id;
    }
}
