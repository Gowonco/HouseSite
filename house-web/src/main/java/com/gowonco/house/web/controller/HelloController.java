package com.gowonco.house.web.controller;

import com.gowonco.house.biz.service.UserService;
import com.gowonco.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-17 20:30
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("hello1")
    public  String hello(ModelMap modelMap){
        List<User> list = userService.getUsers();
        User one =list.get(0);
        modelMap.put("user",one);
        return "hello";
    }

    @RequestMapping("hello2")
    public ModelAndView helloTest(){

        List<User> list = userService.getUsers();
        User one =list.get(0);
        if(one != null) {
            throw new IllegalArgumentException();
        }

        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("user",one);
        return mv;
    }

    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("homepage/index");
        return mv;
    }
}
