package com.lq.maintenance.common.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzNotice;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class HeZe {
    @Autowired
    private HzNoticeMapper hzNoticeMapper;

    public HeZe() {
    }


    /*别人 14022*/
    /*菏泽 14972*/
    @Scheduled(cron = "0/5 * * * * *")
    public void crawData() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://yurenhao.sizhengwang.cn/zcms/front/information/studio?pageIndex=0&pageSize=10000&tag=&siteID=143&studioID=14972&v=" + (new Date()).getTime());
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String data = EntityUtils.toString(entity);
                JSONObject jsonData = JSONObject.parseObject(data);
                JSONArray listData = jsonData.getJSONObject("data").getJSONArray("data");
                System.out.println(1);
                CloseableHttpResponse likeCountResponse;
                for(Iterator var8 = listData.iterator(); var8.hasNext(); likeCountResponse.close()) {
                    Object item = var8.next();
                    JSONObject jsonObject = JSONObject.parseObject(item.toString());
                    String title = jsonObject.get("title").toString();
                    Integer noticeId = Integer.valueOf(jsonObject.get("articleID").toString());
                    Integer hitCount = Integer.valueOf(jsonObject.get("hitCount").toString());
                    String publishDate = jsonObject.get("publishDate").toString();
                    String pcLink = jsonObject.get("pcLink").toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    HttpGet likeCountGet = new HttpGet("http://yurenhao.sizhengwang.cn/zcms/front/univs/like/allcount?contentId=" + noticeId);
                    likeCountResponse = httpclient.execute(likeCountGet);
                    HttpEntity likeCountEntity = likeCountResponse.getEntity();
                    String likeCountData = EntityUtils.toString(likeCountEntity);
                    Object like = JSONObject.parseObject(likeCountData).getJSONObject("data").get("likeTotal");
                    Integer likeCount = Integer.valueOf(like.toString());

                    try {
                        Date date = simpleDateFormat.parse(publishDate);
                        HzNotice hzNotice = new HzNotice();
                        hzNotice.setNoticeId(noticeId);
                        HzNotice selectOne = (HzNotice)this.hzNoticeMapper.selectOne(hzNotice);
                        if (selectOne != null) {
                            selectOne.setHitCount(hitCount);
                            selectOne.setLikeCount(likeCount);
                            selectOne.setNoticeTitle(title);
                            selectOne.setPcLink(pcLink);
                            selectOne.setPublishDate(date);
                            this.hzNoticeMapper.updateByPrimaryKeySelective(selectOne);
                        } else {
                            selectOne = new HzNotice();
                            selectOne.setHitCount(hitCount);
                            selectOne.setLikeCount(likeCount);
                            selectOne.setNoticeTitle(title);
                            selectOne.setPublishDate(date);
                            selectOne.setPcLink(pcLink);
                            selectOne.setNoticeId(noticeId);
                            this.hzNoticeMapper.insertSelective(selectOne);
                        }
                    } catch (ParseException var38) {
                        var38.printStackTrace();
                    }
                }
            }

            response.close();
        } catch (ClientProtocolException var39) {
            var39.printStackTrace();
        } catch (org.apache.http.ParseException var40) {
            var40.printStackTrace();
        } catch (IOException var41) {
            var41.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException var37) {
                var37.printStackTrace();
            }

        }
        System.out.println("结束");
    }
}
