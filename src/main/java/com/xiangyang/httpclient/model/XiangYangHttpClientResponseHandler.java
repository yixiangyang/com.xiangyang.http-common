//package com.xiangyang.httpclient.model;
//
//import org.apache.hc.client5.http.ClientProtocolException;
//import org.apache.hc.core5.http.ClassicHttpResponse;
//import org.apache.hc.core5.http.ContentType;
//import org.apache.hc.core5.http.HttpEntity;
//import org.apache.hc.core5.http.HttpException;
//import org.apache.hc.core5.http.io.HttpClientResponseHandler;
//import org.apache.hc.core5.http.io.entity.EntityUtils;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//public class XiangYangHttpClientResponseHandler implements HttpClientResponseHandler<T> {
//    @Override
//    public T handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
//        HttpEntity entity = response.getEntity();
//        if (entity == null) {
//            throw new ClientProtocolException("Response contains no content");
//        }
//        entity.getContentEncoding()
//
//        // 读取返回内容
//
//        ContentType contentType = ContentType.getOrDefault(entity);
//        Charset charset = contentType.getCharset();
//        return EntityUtils.toString(entity, charset == null ? Charset.forName("utf-8") : charset);
//    }
//}
