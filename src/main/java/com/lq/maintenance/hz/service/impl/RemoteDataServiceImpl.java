package com.lq.maintenance.hz.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lq.maintenance.core.dao.HzLogMapper;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzLog;
import com.lq.maintenance.core.model.HzNotice;
import com.lq.maintenance.hz.service.RemoteDataService;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RemoteDataServiceImpl implements RemoteDataService {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private HzNoticeMapper hzNoticeMapper;
    @Autowired
    private HzLogMapper hzLogMapper;
    /*菏泽 14972*/
    /*武汉东湖学院文法学 15190*/

    @Override
    public void crawRemoteData() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://yurenhao.sizhengwang.cn/zcms/front/information/studio?pageIndex=0&pageSize=10000&tag=&siteID=143&studioID=14332&v=" + (new Date()).getTime());
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String data = EntityUtils.toString(entity);
                JSONObject jsonData = JSONObject.parseObject(data);
                JSONArray listData = jsonData.getJSONObject("data").getJSONArray("data");
                System.out.println(1);
                CloseableHttpResponse likeCountResponse;
                for (Iterator var8 = listData.iterator(); var8.hasNext(); likeCountResponse.close()) {
                    Object item = var8.next();
                    JSONObject jsonObject = JSONObject.parseObject(item.toString());
                    String title = jsonObject.get("title").toString();
                    Integer noticeId = Integer.valueOf(jsonObject.get("articleID").toString());
                    Integer hitCount = Integer.valueOf(jsonObject.get("hitCount").toString());
                    String publishDate = jsonObject.get("publishDate").toString();
                    String pcLink = jsonObject.get("pcLink").toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    HttpGet likeCountGet = new HttpGet("https://yurenhao.sizhengwang.cn/zcms/front/univs/like/allcount?contentId=" + noticeId);
                    likeCountResponse = httpclient.execute(likeCountGet);
                    HttpEntity likeCountEntity = likeCountResponse.getEntity();
                    String likeCountData = EntityUtils.toString(likeCountEntity);
                    Object like = JSONObject.parseObject(likeCountData).getJSONObject("data").get("likeTotal");
                    Integer likeCount = Integer.valueOf(like.toString());

                    try {
                        Date date = simpleDateFormat.parse(publishDate);
                        HzNotice hzNotice = new HzNotice();
                        hzNotice.setNoticeId(noticeId);
                        HzNotice selectOne = this.hzNoticeMapper.selectOne(hzNotice);
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
    }

    @Override
    public void randomLike(Integer contentId,int randomNum) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://yurenhao.sizhengwang.cn/zcms/front/univs/like");
        for (int i = 0; i < randomNum; i++) {
            try {
                String shortUuid = generateShortUuid();
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("type", "Y"));
                nvps.add(new BasicNameValuePair("contentID", contentId.toString()));
                nvps.add(new BasicNameValuePair("clientID", shortUuid));
                HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
                httpPost.setEntity(httpEntity);
                CloseableHttpResponse response = httpclient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonData = JSONObject.parseObject(data);
                    HzLog hzLog = new HzLog();
                    hzLog.setClientId(shortUuid);
                    hzLog.setGenerateDate(new Date());
                    hzLog.setHzClientId(contentId);
                    hzLogMapper.insertSelective(hzLog);
                }
                response.close();
            } catch (ClientProtocolException var39) {
                var39.printStackTrace();
            } catch (org.apache.http.ParseException var40) {
                var40.printStackTrace();
            } catch (IOException var41) {
                var41.printStackTrace();
            }
        }
        try {
            httpclient.close();
        } catch (IOException var37) {
            var37.printStackTrace();
        }
    }

    @Override
    public void randomHit(Integer noticeId, String title, String link, Integer hitTime) {
        System.out.println("["+noticeId+":"+hitTime+"]");
        for (int i = 0; i < hitTime; i++) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpget = new HttpGet();
                StringBuilder startUrl = new StringBuilder("http://yurenhao.sizhengwang.cn/zcms/front/stat/dealer?");
                startUrl.append("SiteID=143");
                startUrl.append("&CatalogInnerCode=002738000002000003000483");
                startUrl.append("&LeafID="+noticeId);
                startUrl.append("&Type=Article");
                startUrl.append("&Title="+ URLEncoder.encode(title, "utf-8"));
                startUrl.append("&URL="+link);
                startUrl.append("&Host=yurenhao.sizhengwang.cn");
                httpget.setURI(URI.create(startUrl.toString()));
                CloseableHttpResponse response = httpclient.execute(httpget);


                HttpGet httpget1 = new HttpGet("https://yurenhao.sizhengwang.cn/zcms/front/recommends/dealer?Event=KeepAlive&SiteID=143");
                Header[] allHeaders = response.getHeaders("Set-Cookie");

                StringBuilder stringBuilderCookie = new StringBuilder();
                for (Header allHeader : allHeaders) {
                    if (allHeader.getValue().contains("SERVERID")) {
                        String s = allHeader.getValue().split(";")[0];
                        String[] split = s.split("\\|");
                        Long end = Long.valueOf(split[1]) + 60L;
                        String cookie = split[0] + "|" + end.toString() + "|" + split[2];
                        stringBuilderCookie.append(cookie + ";");
                    } else {
                        String s = allHeader.getValue().split(";")[0];
                        stringBuilderCookie.append(s + ";");
                    }
                }
                httpget1.setHeader("cookie", stringBuilderCookie.toString());
                CloseableHttpResponse response1 = httpclient.execute(httpget1);
                response.close();
                response1.close();
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
        }
        logger.info("浏览结束");
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
}
