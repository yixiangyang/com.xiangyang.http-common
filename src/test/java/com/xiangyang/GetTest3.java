package com.xiangyang;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpsParameters;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GetTest3 {
    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建 HttpGet 对象
            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            // 执行 Http Get 请求
            response = httpclient.execute(httpGet);
            // 输出响应内容
            if (response.getEntity() != null) {
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            }
            // 销毁流
            EntityUtils.consume(response.getEntity());
        } catch (IOException | ParseException e) {

        } finally {
            // 释放资源
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {

                }
            }
        }

    }

}