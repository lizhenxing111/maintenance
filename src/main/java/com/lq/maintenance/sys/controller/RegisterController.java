package com.lq.maintenance.sys.controller;

import com.lq.maintenance.core.service.UserService;
import com.lq.maintenance.core.vo.HttpResult;
import com.lq.maintenance.core.vo.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public HttpResult register(String username,String password) {
        try {
            userService.registerUser(username,password);
        }catch (Exception e){
           return HttpResult.error(HttpStatus.SC_CREATED,e.getMessage());
        }
        return HttpResult.ok("注册成功");
    }
}

