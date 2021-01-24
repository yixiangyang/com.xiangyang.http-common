package com.xiangyang.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.net.URI;

import com.xiangyang.httpclient.client.DeleteClient;
import com.xiangyang.httpclient.client.GetClient;
import com.xiangyang.httpclient.client.PostClient;
import com.xiangyang.httpclient.client.PutClient;
import com.xiangyang.httpclient.model.HttpResponseResult;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

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

    public static <T> ResponseHandler<T> getJsonResponseHandler(final Class<T> clz) {
        return (ResponseHandler<T>)new AbstractResponseHandler<T>() {
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, clz);
            }
        };
    }

    public static <T> ResponseHandler<T> getJsonResponseHandler(final TypeReference<T> typeReference) {
        return (ResponseHandler<T>)new AbstractResponseHandler<T>() {
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, typeReference, new com.alibaba.fastjson.parser.Feature[0]);
            }
        };
    }

    public static <T> HttpResponseResult<T> execute(HttpUriRequest request, Object input, ResponseHandler<T> responseHandler) throws IOException {
        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
        return execute(httpClient, request, input, responseHandler);
    }

    public static <T> HttpResponseResult<T> execute(HttpUriRequest request, Object input, ResponseHandler<T> responseHandler, HttpClientFactory.Config config) throws IOException {
        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(config);
        return execute(httpClient, request, input, responseHandler);
    }

    private static <T> HttpResponseResult<T> execute(CloseableHttpClient httpClient, HttpUriRequest request, Object input, ResponseHandler<T> responseHandler) throws IOException {
        CloseableHttpResponse result = null;
        String url = getUrl((HttpRequest)request);
//        Transaction transaction = Cat.newTransaction("HttpClientService", url);
        long startTime = System.currentTimeMillis();
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        try {
            URI uri = request.getURI();
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
            T t = (T)responseHandler.handleResponse((HttpResponse)result);
            System.out.println("gggg======"+t.toString());
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
                int statusCode = result.getStatusLine().getStatusCode();
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
        String uri = request.getRequestLine().getUri();
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
