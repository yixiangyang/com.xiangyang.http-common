package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HtmlGetTest {
    public static void main(String[] args) throws IOException, ParseException {
//        HttpToolsClient.getstr
        HttpResponseResult<String> str= HttpToolsClient.getStrByGetUrl("https://a.jd.com//ajax/queryServerData.html", Charset.forName("utf-8"));
//        Document document = Jsoup.parse(String.valueOf(str),"utf-8");
        System.out.println(str);
//        HttpEntity entity = new StringEntity("这一个字符串实体", Charset.forName("UTF-8"));
        //内容类型
//        EntityUtils
//        = EntityUtils.getContentCharSet(entity)
//        System.out.println(entity.getContentType());
//        //内容的编码格式
//        System.out.println(entity.getContentEncoding());
    }
}
