package com.imooc.controller;

import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/saveUser")
    public Object saveUser()
    {
        userService.saveUser();
        return "OK";
    }
    @GetMapping("getUser")
    public Object getUser(String id)
    {
        return userService.getUser(id);
    }
    @PostMapping("/updateUser")
    public void updateUser(String id)
    {
        userService.updateUser(id);
    }
}
