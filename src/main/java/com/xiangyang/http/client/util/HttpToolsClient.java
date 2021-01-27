package com.xiangyang.http.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.httpclient.XiangYangHttpClient;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.ContentBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

public class HttpToolsClient {
    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl) {
        return getStrByGetUrl(requestUrl, null);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, Integer connectTimeOut, Integer readTimeOut) {
        return getStrByGetUrl(requestUrl, null, connectTimeOut, readTimeOut);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, Charset charset) {
        return getStrByGetUrl(requestUrl, charset, null, null);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, Charset charset, Integer connectTimeOut, Integer readTimeOut) {
        return getStrByGetParameters(requestUrl, null, charset, connectTimeOut, readTimeOut);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap) {
        return getStrByGetParameters(requestUrl, paramMap, null);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset) {
        return getStrByGetParameters(requestUrl, paramMap, charset, null, null);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return getStrByGetParameters(requestUrl, paramMap, null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset, Integer connectTimeOut, Integer readTimeOut) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet()
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withResponseHandler(getStringResponseHandler())
                .addUrlParameters(paramMap)
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl,  null, null, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, Charset charset, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, charset, null, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut), clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, Charset charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withResponseHandler(getInputStreamResponseHandler())
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, charset, null, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Integer connectTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, null, connectTimeOut, readTimeOut, type, features);
    }

    /**
     * 将返回对象根据对应编码格式做相应处理
     * @param <String>
     * @return
     */
    public static <String> HttpClientResponseHandler<String> getStringResponseHandler() {
        return (HttpClientResponseHandler<String>)new AbstractHttpClientResponseHandler<String>() {
            @SneakyThrows
            @Override
            public String handleEntity(HttpEntity httpEntity) throws IOException {
                System.out.println("处理的编码格式:"+httpEntity.getContentEncoding());
                return (String) EntityUtils.toString(httpEntity, httpEntity.getContentEncoding() == null ? Charset.forName("utf-8") : Charset.forName(httpEntity.getContentEncoding()));
            }
        };
    }

    /**
     * 将返回对象根据对应编码格式做相应处理
     * @param <String>
     * @return
     */
    public static <T> HttpClientResponseHandler<T> getInputStreamResponseHandler() {
        return (HttpClientResponseHandler<T>)new AbstractHttpClientResponseHandler<T>() {
            @SneakyThrows
            @Override
            public T handleEntity(HttpEntity httpEntity) throws IOException {
                if(httpEntity.getContentType().equals(ContentType.TEXT_HTML.getMimeType())){
                    String html = EntityUtils.toString(httpEntity, "utf-8");
                    System.out.println(html);
                    return (T) html;
                }
                if(httpEntity.getContentType().equals(ContentType.APPLICATION_JSON.getMimeType())){
                    ObjectMapper objectMapper = new ObjectMapper();
//                    String html = EntityUtils.toString(responseEntity, "utf-8");
                    String jsonString = objectMapper.readTree(httpEntity.getContent()).toString();
                    System.out.println(JSON.parse(jsonString));
                }
                if(httpEntity.getContentType().equals(ContentType.IMAGE_JPEG.getMimeType())){
//                    InputStream inputStream=entity.getContent();
//                    FileUtils.copyToFile(httpEntity.getContent(), new File("D://logo.jpeg"));
//                    FormatTools.getInstance().InputStream2Drawable(responseEntity.getContent());
                    return (T)httpEntity.getContent();
                }
                System.out.println("处理的编码格式:"+httpEntity.getContentEncoding());
                return (T) EntityUtils.toString(httpEntity, httpEntity.getContentEncoding() == null ? Charset.forName("utf-8") : Charset.forName(httpEntity.getContentEncoding()));
            }
        };
    }
    public static <T> HttpClientResponseHandler<T> getJsonResponseHandler(final Class<T> clz, Feature... features) {
        return (HttpClientResponseHandler<T>)new AbstractHttpClientResponseHandler<T>() {
            @SneakyThrows
            @Override
            public T handleEntity(HttpEntity httpEntity) throws IOException {
                String result = EntityUtils.toString(httpEntity);
                return (T)JSON.parseObject(result, clz, features);
            }

//            public T handleEntity(HttpEntity entity) throws IOException {
//                String result = EntityUtils.toString(entity);
//                return (T)JSON.parseObject(result, clz, features);
//            }
        };
    }

    public static <T> HttpClientResponseHandler<T> getJsonResponseHandler(final TypeReference<T> typeReference, Feature... features) {
        return (HttpClientResponseHandler<T>)new AbstractHttpClientResponseHandler<T>() {
            @SneakyThrows
            @Override
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, typeReference, features);
            }
        };
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset, Integer connectTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(type)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withResponseHandler(getJsonResponseHandler(type, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, charset, null, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, null, connectTimeOut, readTimeOut, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Charset charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap) {
        return post(requestUrl, paramMap, (Charset) null);
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, Charset charset) {
        return post(requestUrl, paramMap, charset, (TypeReference<String>) null, null);
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return post(requestUrl, paramMap, null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
    }

    public static List<NameValuePair> convertMapToNameValuePair(Map<String, Object> requestParams) {
        if (requestParams != null && !requestParams.isEmpty()) {
            List<NameValuePair> formParams = new ArrayList<>();
            Iterator i$ = requestParams.entrySet().iterator();
            for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                if (null != entry.getValue()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    continue;
                }
                formParams.add(new BasicNameValuePair(entry.getKey(), null));
            }
            return formParams;
        }
        return null;
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, Charset charset, Integer connectTimeOut, Integer readTimeOut) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<String> responseResult = new HttpResponseResult();
        formEntity = new UrlEncodedFormEntity(formParams, charset);
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost()
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, null, type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Charset charset, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, null, connectTimeOut, readTimeOut, type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Charset charset, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        formEntity = new UrlEncodedFormEntity(formParams, charset);
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(type)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withResponseHandler(getJsonResponseHandler(type, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(Integer.valueOf(connectTimeOut)).readTimeout(Integer.valueOf(readTimeOut)).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return post(requestUrl, paramMap, null, clazz);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Charset charset, Class<T> clazz) {
        return post(requestUrl, paramMap, charset, (Integer)null, (Integer)null, clazz, new Feature[0]);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Charset charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz, Feature... features) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        formEntity = new UrlEncodedFormEntity(formParams, charset);
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withResponseHandler(getJsonResponseHandler(clazz, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent) {
        return getStrByPostRaw(requestUrl, jsonContent, (Charset) null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Charset charset) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, null, null, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Integer conncetTimeOut, Integer readTimeOut) {
        return getStrByPostRaw(requestUrl, jsonContent, null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, null, headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Charset charset, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, null, null, headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, null, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Charset charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost()
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .addHeaders(headsMap)
                .withHttpEntity((HttpEntity)new StringEntity(jsonContent, charset))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(conncetTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, Charset charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, charset, null, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, null, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, Charset charset, Integer conncetTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
//        HttpResponseResult.transform(XiangYangHttpClient.restForPost(type)
//                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString()));
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(type)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)new StringEntity(jsonContent, charset))
                .withResponseHandler(getJsonResponseHandler(type, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(conncetTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, Charset charset, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, null, conncetTimeOut, readTimeOut, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, Charset charset, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)new StringEntity(jsonContent, charset))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(Integer.valueOf(conncetTimeOut)).readTimeout(Integer.valueOf(readTimeOut)).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap) {
        return getStrByPostFormData(requestUrl, parasmMap, null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, Charset charset) {
        return getStrByPostFormData(requestUrl, parasmMap, charset, (Integer)null, (Integer)null, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, Integer conncetTimeOut, Integer readTimeOut) {
        return getStrByPostFormData(requestUrl, parasmMap, null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, Charset charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap) {
        return getResultByPostFormData(requestUrl, parasmMap, conncetTimeOut, readTimeOut, headsMap, ContentType.APPLICATION_OCTET_STREAM);
    }

    public static HttpResponseResult<String> getStrByPostFormDataAndContentType(String requestUrl, Map<String, Object> parasmMap, Charset charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap, ContentType contentType) {
        return getResultByPostFormData(requestUrl, parasmMap, conncetTimeOut, readTimeOut, headsMap, contentType);
    }

    private static HttpResponseResult<String> getResultByPostFormData(String requestUrl, Map<String, Object> parasmMap, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap, ContentType contentType) {
        HttpEntity entity = null;
        if (parasmMap != null && !parasmMap.isEmpty()) {
            MultipartEntityBuilder muBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, Object> entry : parasmMap.entrySet()) {
                if (null != entry.getValue()) {
                    if (entry.getValue() instanceof File) {
                        muBuilder.addBinaryBody(entry.getKey(), (File)entry.getValue());
                        continue;
                    }
                    if (entry.getValue() instanceof String) {
                        StringBody stringBody = new StringBody(entry.getValue().toString(), ContentType.TEXT_PLAIN);
                        muBuilder.addPart(entry.getKey(), (ContentBody)stringBody);
                        continue;
                    }
                    if (entry.getValue() instanceof Map) {
                        Map<String, Object> dataMap = (Map<String, Object>)entry.getValue();
                        InputStream inputStream = (InputStream)dataMap.get("fileContent");
                        String fileName = (String)dataMap.get("fileName");
                        muBuilder.addBinaryBody(entry.getKey(), inputStream, contentType, fileName);
                        continue;
                    }
                    if (entry.getValue() instanceof InputStream)
                        muBuilder.addBinaryBody(entry.getKey(), (InputStream)entry.getValue());
                }
            }
            entity = muBuilder.build();
        }
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost()
                .withForJson(false)
                .withUrl(requestUrl)
                .withHttpEntity(entity)
                .addHeaders(headsMap)
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(conncetTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }
}
