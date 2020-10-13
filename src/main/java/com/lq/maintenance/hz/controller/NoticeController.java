package com.lq.maintenance.hz.controller;

import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: maintenance
 * @description: 发布文章管理
 * @author: lzx
 * @create: 2020-10-13 22:38
 **/
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @RequestMapping(value = "list")
    public List<HzNotice> queryAllNotice(){
        List<HzNotice> hzNotices = noticeService.queryNoticeAll();
        return hzNotices;
    }
}
