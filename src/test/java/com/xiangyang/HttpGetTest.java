package com.xiangyang;

import com.alibaba.fastjson.JSON;
import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpGetTest {
    public static void main(String[] args) throws URISyntaxException {
        URIBuilder uriBuilder=new URIBuilder("http://httpbin.org/get");
        uriBuilder.setParameter("aa","中文");
        uriBuilder.setCharset(Charset.forName("utf-8"));
        HttpResponseResult<String> str=HttpToolsClient.getStrByGetUrl(uriBuilder.build().toString());
//        HttpResponseResult<String> str=HttpToolsClient.getStrByGetUrl("https://www.baidu.com");
//        Map<String ,Object> map = new HashMap<>();
//        map.put("aa","这个是中文");
//        HttpResponseResult<String> str= HttpToolsClient.getStrByGetParameters("http://httpbin.org/get",map, Charset.forName("UTF-8"));
        System.out.println(JSON.parse(str.getResponseVo()));


    }
}
