package com.xiangyang;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;
import sun.misc.IOUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.apache.hc.core5.http.HttpVersion.HTTP;

public class HttpGetTest2 {
    public static void main(String[] args) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder uriBuilder=new URIBuilder("http://httpbin.org/get");
            uriBuilder.setParameter("aa","中文");
            uriBuilder.setCharset(Charset.forName("utf-8"));
            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
//                System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
//                System.out.println("ggg"+response1.toString());
//                HttpEntity entity1 = response1.getEntity();
//                System.out.println(response1.getEntity().getContentEncoding());
//                System.out.println("gg"+EntityUtils.toString(response1.getEntity(), response1.getEntity().getContentEncoding() == null ? Charset.forName("utf-8") : Charset.forName(response1.getEntity().getContentEncoding())));;
                final HttpEntity responseEntity = response1.getEntity();
//                if (responseEntity == null) {
//                    return null;
//                }
                System.out.println( "hhhhhhhhhhhh"+responseEntity.getContentType());;
                System.out.println(ContentType.TEXT_HTML.getMimeType());
                System.out.println(responseEntity.getContentType().equals(ContentType.TEXT_HTML.getMimeType()));
                if(responseEntity.getContentType().equals(ContentType.TEXT_HTML.getMimeType())){
                    String html = EntityUtils.toString(responseEntity, "utf-8");
                    System.out.println(html);
                }
                if(responseEntity.getContentType().equals(ContentType.APPLICATION_JSON.getMimeType())){
                    ObjectMapper objectMapper = new ObjectMapper();
//                    String html = EntityUtils.toString(responseEntity, "utf-8");
                    String jsonString = objectMapper.readTree(responseEntity.getContent()).toString();
                    System.out.println(JSON.parse(jsonString));
                }
//                JsonFactory jsonFactory = new JsonFactory();
//                ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
//                try (InputStream inputStream = responseEntity.getContent()) {
//                    System.out.println(objectMapper.readTree(inputStream));
////                    return ;
//                }
                // do something useful with the response body
                // and ensure it is fully consumed
//                EntityUtils.consume(entity1);
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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
