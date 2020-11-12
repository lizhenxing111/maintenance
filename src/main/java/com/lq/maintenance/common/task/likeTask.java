package com.lq.maintenance.common.task;

import com.lq.maintenance.common.util.NumberUtils;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.RemoteDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: maintenance
 * @description:
 * @author: lzx
 * @create: 2020-09-29 22:18
 **/
@Component
public class likeTask {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private RemoteDataService remoteDataService;
    @Autowired
    private HzNoticeMapper hzNoticeMapper;
    @Scheduled(cron = "0 26 23 * * ?")
    public void likeTaskMain(){

        remoteDataService.crawRemoteData();
        List<HzNotice> hzNotices = hzNoticeMapper.selectAll();
        int[] ints = NumberUtils.randomCommon(0, hzNotices.size() - 1, 10);
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            HzNotice hzNotice = hzNotices.get(anInt);
            //随机产生数字
            int randomLike = NumberUtils.getRandom(10, 15);
            int randomHit = NumberUtils.getRandom(randomLike, randomLike*2);
            logger.info("文章ID:{},浏览量次数:{},点赞次数:{}",hzNotice.getNoticeId(),randomHit,randomLike);
            remoteDataService.randomLike(hzNotice.getNoticeId(),randomLike);
            remoteDataService.randomHit(hzNotice.getNoticeId(),hzNotice.getNoticeTitle(),hzNotice.getPcLink(),randomHit);
        }
    }
}
