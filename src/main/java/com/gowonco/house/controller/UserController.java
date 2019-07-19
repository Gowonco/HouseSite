package com.gowonco.house.controller;

import com.gowonco.house.common.model.User;
import com.gowonco.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-16 0:14
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    private List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/str", method = RequestMethod.GET)
    private String getStr(){
        return "ssss";
    }

 }
