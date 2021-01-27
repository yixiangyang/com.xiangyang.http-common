package com.xiangyang.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import com.xiangyang.httpclient.client.DeleteClient;
import com.xiangyang.httpclient.client.GetClient;
import com.xiangyang.httpclient.client.PostClient;
import com.xiangyang.httpclient.client.PutClient;
import com.xiangyang.httpclient.model.HttpResponseResult;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.entity.GzipDecompressingEntity;
import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class XiangYangHttpClient {
    public static <T> PostClient<T> restForPost(Class<T> responseObjClass) {
        return new PostClient(responseObjClass);
    }

    public static <T> PostClient<T> restForPost(TypeReference<T> typeReference) {
        return new PostClient(typeReference);
    }

    public static PostClient<String> restForPost() {
        return restForPost(String.class);
    }

    public static <T> PutClient<T> restForPut(Class<T> responseObjClass) {
        return new PutClient(responseObjClass);
    }

    public static <T> PutClient<T> restForPut(TypeReference<T> typeReference) {
        return new PutClient(typeReference);
    }

    public static PutClient<String> restForPut() {
        return restForPut(String.class);
    }

    public static <T> GetClient<T> restForGet(Class<T> responseObjClass) {
        return new GetClient(responseObjClass);
    }

    public static <T> GetClient<T> restForGet(TypeReference<T> typeReference) {
        return new GetClient(typeReference);
    }

    public static GetClient<String> restForGet() {
        return restForGet(String.class);
    }

    public static <T> DeleteClient<T> restForDelete(Class<T> responseObjClass) {
        return new DeleteClient(responseObjClass);
    }

    public static <T> DeleteClient<T> restForDelete(TypeReference<T> typeReference) {
        return new DeleteClient(typeReference);
    }

    public static DeleteClient<String> restForDelete() {
        return restForDelete(String.class);
    }

    public static <T> HttpClientResponseHandler<T> getJsonResponseHandler(final Class<T> clz) {
        return (HttpClientResponseHandler<T> )new AbstractHttpClientResponseHandler<T>(){

            @SneakyThrows
            @Override
            public T handleEntity(HttpEntity httpEntity) throws IOException {
                String result = EntityUtils.toString(httpEntity);
                return (T)JSON.parseObject(result, clz);
            }
        };
//        return (HttpClientResponseHandler<T>)new AbstractHttpClientResponseHandler<T>() {
//            public T handleEntity(HttpEntity entity) throws IOException {
//                String result = EntityUtils.toString(entity);
//                return (T)JSON.parseObject(result, clz);
//            }
//        };
    }

    public static <T> HttpClientResponseHandler<T> getJsonResponseHandler(final TypeReference<T> typeReference) {
        return (HttpClientResponseHandler<T>)new AbstractHttpClientResponseHandler<T>() {
            @SneakyThrows
            @Override
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, typeReference, new com.alibaba.fastjson.parser.Feature[0]);
            }
        };
    }

    public static <T> HttpResponseResult<T> execute(HttpUriRequest request, Object input, HttpClientResponseHandler<T> responseHandler) throws IOException, URISyntaxException, HttpException {
        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
        return execute(httpClient, request, input, responseHandler);
    }

    public static <T> HttpResponseResult<T> execute(HttpUriRequest request, Object input, HttpClientResponseHandler<T> responseHandler, HttpClientFactory.Config config) throws IOException, URISyntaxException, HttpException {
        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(config);
        return execute(httpClient, request, input, responseHandler);
    }

    private static <T> HttpResponseResult<T> execute(CloseableHttpClient httpClient, HttpUriRequest request, Object input, HttpClientResponseHandler<T> responseHandler) throws IOException, URISyntaxException, HttpException {
        CloseableHttpResponse result = null;
        String url = getUrl((HttpRequest)request);
//        Transaction transaction = Cat.newTransaction("HttpClientService", url);
        long startTime = System.currentTimeMillis();
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        try {
            URI uri = request.getUri();
            String uriStr = uri.toString();
//            String rpcEntryUrl = Tracer.genRpcEntryUrl(uriStr);
//            ZipkinContext zipkinContext = getZipkinContext(rpcEntryUrl);
//            Span span = Tracer.computingCallchain(url, "consumer");
//            transaction.addData("globalTicket", zipkinContext.getGlobalTicket());
//            transaction.addData("monitorTrackId", zipkinContext.getMonitorTrackId());
//            transaction.addData("rpcId", span.getRpcId());
//            transaction.addData("rpcEntryUrl", zipkinContext.getRpcEntryUrl());
//            logZipkinInHttpHeader(request, span);
            result = httpClient.execute(request);
//            Header[] headers = result.getHeaders("Content-Encoding");
//            boolean isGzip = false;
//            for(Header h:headers){
//                System.out.println(h.toString());
//                if(h.getValue().equals("gzip")){
//                    //返回头中含有gzip
//                    isGzip = true;
//                }
//            }
//            if(isGzip){
//                //需要进行gzip解压处理
//                System.out.println(EntityUtils.toString(new GzipDecompressingEntity(result.getEntity())));
////                result = ;
//            }
//            String output = EntityUtils.toString(entity, Charset.forName("UTF-8").name());
//            JSONObject json = JSONObject.parseObject( EntityUtils.toString(result.getEntity()));
//                System.out.println(result.getEntity().getContent());
//            System.out.println(EntityUtils.toString(request.getEntity(),utf-8));
//            System.out.println("gggggggggggggggggg:"+httpEntity);
            T t = (T)responseHandler.handleResponse((ClassicHttpResponse) result);
            responseResult.resolve(result, t);
//            transaction.setStatus("0");
        } catch (Exception e) {
//            transaction.setStatus(e);
//            responseResult.setException(e);
//            responseResult.setReturnMsg(e.getMessage());
            throw e;
        } finally {
            if (result == null) {
//                transaction.setStatus("1");
            } else {
                int statusCode = result.getCode();
                System.out.println("这个是返回的code:"+statusCode);
//                int statusCode = result.getStatusLine().getStatusCode();
//                if (statusCode >= 200 && statusCode < 300) {
//                    transaction.setStatus("0");
//                } else {
//                    transaction.setStatus(String.valueOf(statusCode));
//                }
            }
//            transaction.complete();
//            LogCollector.save(url, request, startTime, System.currentTimeMillis(), input, responseResult);
        }
        return responseResult;
    }

    private static String getUrl(HttpRequest request) {
        String uri = request.getRequestUri();
//        String uri = request.getRequestLine().getUri();
        int index = uri.indexOf("?");
        if (index > -1)
            uri = uri.substring(0, index);
        return uri;
    }

//    private static void logZipkinInHttpHeader(HttpUriRequest request, Span span) {
//        ZipkinContext zipkinContext = ZipkinContext.getContext();
//        request.addHeader("rpcEntryUrl", zipkinContext.getRpcEntryUrl());
//        request.addHeader("globalTicket", zipkinContext.getGlobalTicket());
//        request.addHeader("trace_id", zipkinContext.getGlobalTicket());
//        request.addHeader("parentRpcId", span.getRpcId());
//        CatContext catContext = new CatContext();
//        Cat.logRemoteCallClient((Cat.Context)catContext);
//        request.addHeader("_catChildMessageId", catContext.getProperty("_catChildMessageId"));
//        request.addHeader("_catParentMessageId", catContext.getProperty("_catParentMessageId"));
//        request.addHeader("_catRootMessageId", catContext.getProperty("_catRootMessageId"));
//    }

//    private static ZipkinContext getZipkinContext(String rpcEntryUrl) {
//        ZipkinContext context = ZipkinContext.getContext();
//        if (StringUtils.isBlank(context.getGlobalTicket()))
//            Tracer.genProviderSpan(rpcEntryUrl, null, null);
//        return context;
//    }
}
