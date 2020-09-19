package com.lq.maintenance.common.task;

import com.alibaba.fastjson.JSONObject;
import com.lq.maintenance.core.dao.HzLogMapper;
import com.lq.maintenance.core.dao.HzNoticeMapper;
import com.lq.maintenance.core.model.HzLog;
import com.lq.maintenance.core.model.HzNotice;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HeZeLike {
    @Autowired
    private HzNoticeMapper hzNoticeMapper;
    @Autowired
    private HzLogMapper hzLogMapper;

    public void crawData() {
        System.out.println("-----------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://yurenhao.sizhengwang.cn/zcms/front/univs/like");
        List<HzLog> hzLogs = hzLogMapper.selectAll();
        for (HzLog hzLog : hzLogs) {
            try {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("type", "N"));
                nvps.add(new BasicNameValuePair("contentID", hzLog.getHzClientId().toString()));
                nvps.add(new BasicNameValuePair("clientID", hzLog.getClientId()));
                HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
                httpPost.setEntity(httpEntity);
                CloseableHttpResponse response = httpclient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonData = JSONObject.parseObject(data);
                    System.out.println(jsonData);
                }
                response.close();
            } catch (ClientProtocolException var39) {
                var39.printStackTrace();
            } catch (org.apache.http.ParseException var40) {
                var40.printStackTrace();
            } catch (IOException var41) {
                var41.printStackTrace();
            } finally {

            }
        }
        try {
            httpclient.close();
        } catch (IOException var37) {
            var37.printStackTrace();
        }
        System.out.println("结束------------------------------------------------");
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
