import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testReadFolder {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpget = new HttpGet();
                StringBuilder startUrl = new StringBuilder("http://yurenhao.sizhengwang.cn/zcms/front/stat/dealer?");
                startUrl.append("SiteID=143");
                startUrl.append("&CatalogInnerCode=002738000002000003000483");
                startUrl.append("&LeafID="+660503);
                startUrl.append("&Type=Article");
                startUrl.append("&Title="+ URLEncoder.encode("第四届“不忘初心、牢记使命”微党课大赛讲稿（九）", "utf-8"));
                startUrl.append("&URL="+"https://yurenhao.sizhengwang.cn/a/hzxysxytjxyxsdzb/201225/660503.shtml");
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
                System.out.println(stringBuilderCookie.toString());
                httpget1.setHeader("cookie", stringBuilderCookie.toString());
                CloseableHttpResponse response1 = httpclient.execute(httpget1);
                response.close();
                response1.close();
                System.out.println("返回值:" + response1.getStatusLine());
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
    }
}
