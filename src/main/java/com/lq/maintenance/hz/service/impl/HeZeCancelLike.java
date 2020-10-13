package com.lq.maintenance.hz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lq.maintenance.core.dao.HzLogMapper;
import com.lq.maintenance.core.model.HzLog;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeZeCancelLike {
    @Autowired
    private HzLogMapper hzLogMapper;

    public void crawData() {
        System.out.println("取消赞");
        Example example = new Example(HzLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").asc();
        List<HzLog> hzLogs = hzLogMapper.selectByExample(example);
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            for (HzLog hzLog : hzLogs) {
                try {
                    HttpPost httpPost = new HttpPost("http://yurenhao.sizhengwang.cn/zcms/front/univs/like");
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
                }
            }
            httpclient.close();
        } catch (IOException var37) {
            var37.printStackTrace();
        }
    }

}
