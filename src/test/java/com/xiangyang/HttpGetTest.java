package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpGetTest {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("aa","中文");
        HttpResponseResult<String> str=HttpToolsClient.getStrByGetParameters("http://httpbin.org/get",map, "ISO-8859-1");
        System.out.println(str);
    }
}
