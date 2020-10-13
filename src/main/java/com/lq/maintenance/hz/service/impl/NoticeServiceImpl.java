package com.lq.maintenance.hz.service.impl;

import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.NoticeService;
import com.lq.maintenance.hz.service.RemoteDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: maintenance
 * @description:
 * @author: lzx
 * @create: 2020-10-13 22:41
 **/
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private RemoteDataService remoteDataService;
    @Autowired
    private HzNoticeMapper hzNoticeMapper;

    @Override
    public List<HzNotice> queryNoticeAll() {
        remoteDataService.crawRemoteData();
        List<HzNotice> hzNotices = hzNoticeMapper.selectAll();
        return hzNotices;
    }
}
