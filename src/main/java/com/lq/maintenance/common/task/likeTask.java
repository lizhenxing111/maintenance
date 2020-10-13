package com.lq.maintenance.common.task;

import com.lq.maintenance.common.util.NumberUtils;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.controller.service.imp.HeZe;
import com.lq.maintenance.hz.controller.service.imp.HeZeLike;
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

    @Autowired
    private HeZe heZe;
    @Autowired
    private HeZeLike heZeLike;
    @Autowired
    private HzNoticeMapper hzNoticeMapper;

    @Scheduled(cron = "0 0 22 * * ?")
    public void likeTaskMain(){
        heZe.crawData();
        List<HzNotice> hzNotices = hzNoticeMapper.selectAll();
        int[] ints = NumberUtils.randomCommon(0, hzNotices.size() - 1, 10);
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            HzNotice hzNotice = hzNotices.get(anInt);
            heZeLike.crawData(hzNotice.getNoticeId());
        }
    }
}
