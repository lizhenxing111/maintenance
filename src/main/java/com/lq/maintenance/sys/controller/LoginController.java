package com.lq.maintenance.sys.controller;

import com.lq.maintenance.common.util.SecurityUtils;
import com.lq.maintenance.core.AbstractGlobleController;
import com.lq.maintenance.core.vo.HttpResult;
import com.lq.maintenance.core.vo.HttpStatus;
import com.lq.maintenance.core.vo.LoginBean;
import com.lq.maintenance.security.JwtAuthenticatioToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class LoginController extends AbstractGlobleController {
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(LoginBean loginBean, HttpServletRequest request){
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        // 系统登录认证
        JwtAuthenticatioToken token = null;
        try {
            token = SecurityUtils.login(request, username, password, authenticationManager);
        }catch (Exception e){
            e.printStackTrace();
            return  HttpResult.error(HttpStatus.SC_CREATED,e.getMessage());
        }
        return HttpResult.ok(token);
    }
}

