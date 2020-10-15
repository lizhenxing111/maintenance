package com.lq.maintenance.sys.controller;

import com.lq.maintenance.core.AbstractGlobleController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController extends AbstractGlobleController {
    @PostMapping("/login")
    public HashMap<String, Object> queryMenuByLoginUser(String username,String password) {
        HashMap<String, Object> menuData=new HashMap<>();
        return this.success(menuData);
    }
}

