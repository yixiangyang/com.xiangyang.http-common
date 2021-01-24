package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;

public class HttpGetTest {
    public static void main(String[] args) {
        HttpResponseResult<String> str=HttpToolsClient.getStrByGetUrl("http://httpbin.org/get");
        System.out.println(str);
    }
}
