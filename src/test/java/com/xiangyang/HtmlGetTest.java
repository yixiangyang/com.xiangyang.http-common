package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HtmlGetTest {
    public static void main(String[] args) throws IOException, ParseException {
        HttpResponseResult<String> str= HttpToolsClient.getStrByGetUrl("http://www.baidu.com", Charset.forName("utf-8"));
        Document document = Jsoup.parse(String.valueOf(str),"utf-8");
        System.out.println(document);

    }
}
