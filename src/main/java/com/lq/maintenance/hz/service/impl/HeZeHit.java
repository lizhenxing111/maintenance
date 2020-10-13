package com.lq.maintenance.hz.service.impl;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
public class HeZeHit {
    public static void crawData() {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            for (int i = 0; i < 30; i++) {
                String UniqueID = UUID.randomUUID().toString().replaceAll("-", "");
                HttpGet httpget = new HttpGet("http://yurenhao.sizhengwang.cn/zcms/front/stat/dealer?SiteID=143&CatalogInnerCode=002738000002000003000001" );
                httpget.setHeader("UniqueID",UniqueID);
                httpget.setHeader("LeafID","606276");
                httpget.setHeader("Type","Article");
                httpget.setHeader("vq","2");
                CloseableHttpResponse response = httpclient.execute(httpget);
                Header[] allHeaders1 = response.getAllHeaders();
                for (Header header : allHeaders1) {
                    System.out.println(header.getName());
                }
                Header[] allHeaders = response.getHeaders("Set-Cookie");
                String s = allHeaders[0].getValue().split(";")[0];
                String[] split = s.split("\\|");
                Long end = Long.valueOf(split[1]) + 60L;
                String cookie = split[0] + "|" + end.toString() + "|" + split[2];
                HttpGet httpget1 = new HttpGet("http://yurenhao.sizhengwang.cn/zcms/front/recommends/dealer?SiteID=143&CatalogInnerCode=002738000002000003000001" );
                httpget1.setHeader(allHeaders[0].getName(), cookie);
                httpget1.setHeader("Referer", "http://yurenhao.sizhengwang.cn/a/hzxysxytjxyxsdzb/200908/606276.shtml");
                httpget1.setHeader("Sites", "_143");
                httpget1.setHeader("UniqueID", UniqueID);
                httpget1.setHeader("143_vq", "2");
                CloseableHttpResponse response1 = httpclient.execute(httpget1);
                response.close();
                response1.close();
                System.out.println(response1.getStatusLine());
            }
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

    private static String getRandomNum(int num) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
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
        for (int i = 0; i < 29; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static void main(String[] args) {
        crawData();
    }
}
