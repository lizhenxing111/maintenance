package com.lq.maintenance.hz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.NoticeService;
import com.lq.maintenance.hz.service.RemoteDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<HzNotice> queryNoticeAll(Integer pageNo, Integer pageSize) {
        remoteDataService.crawRemoteData();
        PageInfo<HzNotice> pageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() -> hzNoticeMapper.selectAll());
        return pageInfo;
    }
}
