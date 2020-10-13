package com.lq.maintenance.hz.service;

import com.lq.maintenance.core.model.HzNotice;

import java.util.List;

/**
 * @program: maintenance
 * @description: 文章服务
 * @author: lzx
 * @create: 2020-10-13 22:40
 **/
public interface NoticeService {
    /**
     * @description 获取所有的文章列表
     * @author lzx
     * @date 2020年10月13日 22:41
     */
    public List<HzNotice> queryNoticeAll();
}
