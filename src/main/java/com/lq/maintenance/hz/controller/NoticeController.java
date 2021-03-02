package com.lq.maintenance.hz.controller;

import com.github.pagehelper.PageInfo;
import com.lq.maintenance.common.util.NumberUtils;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.NoticeService;
import com.lq.maintenance.hz.service.RemoteDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: maintenance
 *
 * @description: 发布文章管理
 * @author: lzx
 * @create: 2020-10-13 22:38
 **/
@RestController
@RequestMapping("notice")
public class NoticeController {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RemoteDataService remoteDataService;
    @RequestMapping(value = "list")
    public PageInfo<HzNotice> queryAllNotice(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,@RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        PageInfo<HzNotice> hzNotices = noticeService.queryNoticeAll(pageNo,pageSize);
        return hzNotices;
    }
    @RequestMapping(value = "a")
    public void start(){
        PageInfo<HzNotice> hzNotices = noticeService.queryNoticeAll(1, 1000);
        for (HzNotice hzNotice : hzNotices.getList()) {
            logger.info("文章ID:{},点赞次数:{}",hzNotice.getNoticeId(),hzNotice.getLikeCount());
            remoteDataService.randomLike(hzNotice.getNoticeId(),hzNotice.getLikeCount());
        }
        System.out.println(111);
//        int[] ints = NumberUtils.randomCommon(0, hzNotices.getList().size() - 1, 5);
//        for (int i = 0; i < ints.length; i++) {
//            int anInt = ints[i];
//            HzNotice hzNotice = hzNotices.getList().get(anInt);
//            //随机产生数字
//            int randomLike = NumberUtils.getRandom(10, 15);
//            int randomHit = NumberUtils.getRandom(randomLike, randomLike*2);
//            logger.info("文章ID:{},浏览量次数:{},点赞次数:{}",hzNotice.getNoticeId(),randomHit,randomLike);
//            remoteDataService.randomLike(hzNotice.getNoticeId(),randomLike);
//            remoteDataService.randomHit(hzNotice.getNoticeId(),hzNotice.getNoticeTitle(),hzNotice.getPcLink(),randomHit);
//        }
    }
}
