package com.lq.maintenance.hz.service;
/**
 * @author lzx
 * @title RemoteDataServiceImpl
 * @Description 从远程网站网站爬取数据
 * @Date 2020/10/13 22:20
 * @Copyright
 */
public interface RemoteDataService {
    /**
     * @description 从网站爬取数据存入本地数据库
     * @author lzx
     * @date 2020年10月13日 22:36
     */
    void crawRemoteData();
    /**
     * @description 获取一个ID 随机选择次数
     * @author lzx
     * @date 2020年10月13日 22:36
     */
    void randomLike(Integer contentId,int randomNum);
    /**
     * @description 随机产生浏览量
     * @author lzx
     * @date 2020年11月12日 22:06
     */
    void randomHit(Integer noticeId,String title,String link,Integer hitTime);

}
