package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpGetTest {
    public static void main(String[] args) {
        HttpResponseResult<String> str=HttpToolsClient.getStrByGetUrl("https://www.baidu.com");
//        Map<String ,Object> map = new HashMap<>();
//        map.put("aa","这个是中文");
//        HttpResponseResult<String> str= HttpToolsClient.getStrByGetParameters("http://httpbin.org/get",map, Charset.forName("UTF-8"));
        System.out.println(str);


    }
}
