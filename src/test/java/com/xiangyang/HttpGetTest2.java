package com.xiangyang;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class HttpGetTest2 {
    public static void main(String[] args) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            Header header = new BasicHeader("Accept", "text/plain;charset=utf-8");
            httpGet.setHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));

            httpGet.setHeader(header);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                System.out.println("ggg"+response1.toString());
                HttpEntity entity1 = response1.getEntity();
                System.out.println(response1.getEntity());
                System.out.println("gg"+EntityUtils.toString(entity1, "UTF-8"));;

                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
            } catch (Exception e) {
                e.printStackTrace();
            }


//            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
//            List<NameValuePair> nvps = new ArrayList<>();
//            nvps.add(new BasicNameValuePair("username", "vip"));
//            nvps.add(new BasicNameValuePair("password", "secret"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//            try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
//                System.out.println(response2.getCode() + " " + response2.getReasonPhrase());
//                HttpEntity entity2 = response2.getEntity();
//                // do something useful with the response body
//                // and ensure it is fully consumed
//                EntityUtils.consume(entity2);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
