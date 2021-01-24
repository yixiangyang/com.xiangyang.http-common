package com.xiangyang.http.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.httpclient.XiangYangHttpClient;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpToolsClient {
    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl) {
        return getStrByGetUrl(requestUrl, null);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, Integer connectTimeOut, Integer readTimeOut) {
        return getStrByGetUrl(requestUrl, null, connectTimeOut, readTimeOut);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, String charset) {
        return getStrByGetUrl(requestUrl, charset, null, null);
    }

    public static HttpResponseResult<String> getStrByGetUrl(String requestUrl, String charset, Integer connectTimeOut, Integer readTimeOut) {
        return getStrByGetParameters(requestUrl, null, charset, connectTimeOut, readTimeOut);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap) {
        return getStrByGetParameters(requestUrl, paramMap, null);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset) {
        return getStrByGetParameters(requestUrl, paramMap, charset, null, null);
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return getStrByGetParameters(requestUrl, paramMap, null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
    }

    public static HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, Integer connectTimeOut, Integer readTimeOut) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet()
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .addUrlParameters(paramMap)
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, String charset, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, charset, null, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut), clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, String charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, charset, null, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Integer connectTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, null, connectTimeOut, readTimeOut, type, features);
    }

    public static <T> ResponseHandler<T> getJsonResponseHandler(final Class<T> clz, Feature... features) {
        return (ResponseHandler<T>)new AbstractResponseHandler<T>() {
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, clz, features);
            }
        };
    }

    public static <T> ResponseHandler<T> getJsonResponseHandler(final TypeReference<T> typeReference, Feature... features) {
        return (ResponseHandler<T>)new AbstractResponseHandler<T>() {
            public T handleEntity(HttpEntity entity) throws IOException {
                String result = EntityUtils.toString(entity);
                return (T)JSON.parseObject(result, typeReference, features);
            }
        };
    }

    public static <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, Integer connectTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(type)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withResponseHandler(getJsonResponseHandler(type, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, charset, null, null, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, null, connectTimeOut, readTimeOut, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForGet(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap) {
        return post(requestUrl, paramMap, (String)null);
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, String charset) {
        return post(requestUrl, paramMap, charset, (Integer)null, (Integer)null);
    }

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return post(requestUrl, paramMap, (String)null, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
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

    public static HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, String charset, Integer connectTimeOut, Integer readTimeOut) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<String> responseResult = new HttpResponseResult();
        try {
            formEntity = new UrlEncodedFormEntity(formParams, charset);
        } catch (UnsupportedEncodingException var9) {
            responseResult.setResultCode(-1);
            return responseResult;
        }
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost()
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, (String)null, type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut, type, features);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        try {
            formEntity = new UrlEncodedFormEntity(formParams, charset);
        } catch (UnsupportedEncodingException var9) {
            responseResult.setResultCode(-1);
            return responseResult;
        }
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(type)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withResponseHandler(getJsonResponseHandler(type, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(Integer.valueOf(connectTimeOut)).readTimeout(Integer.valueOf(readTimeOut)).build())
                .executeForResult());
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return post(requestUrl, paramMap, (String)null, clazz);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, Class<T> clazz) {
        return post(requestUrl, paramMap, charset, (Integer)null, (Integer)null, clazz, new Feature[0]);
    }

    public static <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, Integer connectTimeOut, Integer readTimeOut, Class<T> clazz, Feature... features) {
        UrlEncodedFormEntity formEntity;
        List<NameValuePair> formParams = convertMapToNameValuePair(paramMap);
        HttpResponseResult<T> responseResult = new HttpResponseResult();
        try {
            formEntity = new UrlEncodedFormEntity(formParams, charset);
        } catch (UnsupportedEncodingException var9) {
            responseResult.setResultCode(-1);
            return responseResult;
        }
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)formEntity)
                .withResponseHandler(getJsonResponseHandler(clazz, features))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(connectTimeOut).readTimeout(readTimeOut).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, null, null, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Integer conncetTimeOut, Integer readTimeOut) {
        return getStrByPostRaw(requestUrl, jsonContent, null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null, headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, null, null, headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, null, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), headsMap);
    }

    public static HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap) {
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

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, String charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, charset, null, null, type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, null, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), type, features);
    }

    public static <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, String charset, Integer conncetTimeOut, Integer readTimeOut, TypeReference<T> type, Feature... features) {
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

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, String charset, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, null, conncetTimeOut, readTimeOut, clazz);
    }

    public static <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, String charset, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return HttpResponseResult.transform(XiangYangHttpClient.restForPost(clazz)
                .withUrl(URL.valueOf(requestUrl).setParamCharset(charset).toString())
                .withHttpEntity((HttpEntity)new StringEntity(jsonContent, charset))
                .withConfig(HttpClientFactory.Config.builder().connectTimeout(Integer.valueOf(conncetTimeOut)).readTimeout(Integer.valueOf(readTimeOut)).build())
                .executeForResult());
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap) {
        return getStrByPostFormData(requestUrl, parasmMap, null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, String charset) {
        return getStrByPostFormData(requestUrl, parasmMap, charset, (Integer)null, (Integer)null, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, Integer conncetTimeOut, Integer readTimeOut) {
        return getStrByPostFormData(requestUrl, parasmMap, null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public static HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, String charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap) {
        return getResultByPostFormData(requestUrl, parasmMap, conncetTimeOut, readTimeOut, headsMap, ContentType.APPLICATION_OCTET_STREAM);
    }

    public static HttpResponseResult<String> getStrByPostFormDataAndContentType(String requestUrl, Map<String, Object> parasmMap, String charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap, ContentType contentType) {
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
