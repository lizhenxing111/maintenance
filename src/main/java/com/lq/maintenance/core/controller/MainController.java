package com.lq.maintenance.core.controller;

import com.lq.maintenance.common.task.HeZeLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private HeZeLike heZeLike;
    @RequestMapping("/a")
    public void start(){
        heZeLike.crawData();
    }
}
