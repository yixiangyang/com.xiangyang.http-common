package com.xiangyang.http.client.service;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import java.util.Map;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;
import org.apache.http.entity.ContentType;

public class AbstractHttpService {
    public HttpResponseResult<String> getStrByGetUrl(String requestUrl) {
        return getStrByGetUrl(requestUrl, ((Integer)null).intValue(), ((Integer)null).intValue());
    }

    public HttpResponseResult<String> getStrByGetUrl(String requestUrl, int connectTimeOut, int readTimeOut) {
        return getStrByGetUrl(requestUrl, (String)null, connectTimeOut, readTimeOut);
    }

    public HttpResponseResult<String> getStrByGetUrl(String requestUrl, String charset) {
        return getStrByGetUrl(requestUrl, charset, ((Integer)null).intValue(), ((Integer)null).intValue());
    }

    public HttpResponseResult<String> getStrByGetUrl(String requestUrl, String charset, int connectTimeOut, int readTimeOut) {
        return getStrByGetParameters(requestUrl, (Map<String, Object>)null, charset, connectTimeOut, readTimeOut);
    }

    public <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, (String)null, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, String charset, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByGetUrl(requestUrl, (String)null, connectTimeOut, readTimeOut, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetUrl(String requestUrl, String charset, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, (Map<String, Object>)null, charset, connectTimeOut, readTimeOut, clazz);
    }

    public HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap) {
        return getStrByGetParameters(requestUrl, paramMap, (String)null);
    }

    public HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset) {
        return getStrByGetParameters(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue());
    }

    public HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return getStrByGetParameters(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut);
    }

    public HttpResponseResult<String> getStrByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut) {
        return HttpToolsClient.getStrByGetParameters(requestUrl, paramMap, charset, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
    }

    public <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, (String)null, type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectByGetParameters(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut, type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return HttpToolsClient.getGenericObjectByGetParameters(requestUrl, paramMap, charset, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut), type, features);
    }

    public <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, (String)null, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByGetParameters(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByGetParameters(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return HttpToolsClient.getObjectByGetParameters(requestUrl, paramMap, charset, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut), clazz);
    }

    public HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap) {
        return post(requestUrl, paramMap, (String)null);
    }

    public HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, String charset) {
        return post(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue());
    }

    public HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut) {
        return post(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut);
    }

    public HttpResponseResult<String> post(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut) {
        return HttpToolsClient.post(requestUrl, paramMap, charset, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut));
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, (String)null, type, features);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), type, features);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return post(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut, type, features);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return HttpToolsClient.post(requestUrl, paramMap, charset, connectTimeOut, readTimeOut, type, features);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, Class<T> clazz) {
        return post(requestUrl, paramMap, (String)null, clazz);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, Class<T> clazz) {
        return post(requestUrl, paramMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return post(requestUrl, paramMap, (String)null, connectTimeOut, readTimeOut, clazz);
    }

    public <T> HttpResponseResult<T> post(String requestUrl, Map<String, Object> paramMap, String charset, int connectTimeOut, int readTimeOut, Class<T> clazz) {
        return HttpToolsClient.post(requestUrl, paramMap, charset, Integer.valueOf(connectTimeOut), Integer.valueOf(readTimeOut), clazz, new Feature[0]);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), (Map<String, String>)null);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null, headsMap);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), headsMap);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Map<String, String> headsMap) {
        return getStrByPostRaw(requestUrl, jsonContent, (String)null, conncetTimeOut, readTimeOut, headsMap);
    }

    public HttpResponseResult<String> getStrByPostRaw(String requestUrl, String jsonContent, String charset, int conncetTimeOut, int readTimeOut, Map<String, String> headsMap) {
        return HttpToolsClient.getStrByPostRaw(requestUrl, jsonContent, charset, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), headsMap);
    }

    public <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, (String)null, type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, String charset, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return getGenericObjectPostRaw(requestUrl, jsonContent, (String)null, conncetTimeOut, readTimeOut, type, features);
    }

    public <T> HttpResponseResult<T> getGenericObjectPostRaw(String requestUrl, String jsonContent, String charset, int conncetTimeOut, int readTimeOut, TypeReference<T> type, Feature... features) {
        return HttpToolsClient.getGenericObjectPostRaw(requestUrl, jsonContent, charset, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), type, features);
    }

    public <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, (String)null, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, String charset, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), clazz);
    }

    public <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return getObjectByPostRaw(requestUrl, jsonContent, (String)null, conncetTimeOut, readTimeOut, clazz);
    }

    public <T> HttpResponseResult<T> getObjectByPostRaw(String requestUrl, String jsonContent, String charset, int conncetTimeOut, int readTimeOut, Class<T> clazz) {
        return HttpToolsClient.getObjectByPostRaw(requestUrl, jsonContent, charset, conncetTimeOut, readTimeOut, clazz);
    }

    public HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap) {
        return getStrByPostFormData(requestUrl, parasmMap, (String)null);
    }

    public HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, String charset) {
        return getStrByPostFormData(requestUrl, parasmMap, charset, ((Integer)null).intValue(), ((Integer)null).intValue(), (Map<String, String>)null);
    }

    public HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, int conncetTimeOut, int readTimeOut) {
        return getStrByPostFormData(requestUrl, parasmMap, (String)null, conncetTimeOut, readTimeOut, (Map<String, String>)null);
    }

    public HttpResponseResult<String> getStrByPostFormData(String requestUrl, Map<String, Object> parasmMap, String charset, int conncetTimeOut, int readTimeOut, Map<String, String> headsMap) {
        return HttpToolsClient.getStrByPostFormData(requestUrl, parasmMap, charset, Integer.valueOf(conncetTimeOut), Integer.valueOf(readTimeOut), headsMap);
    }

    public HttpResponseResult<String> getStrByPostFormDataAndContentType(String requestUrl, Map<String, Object> parasmMap, String charset, Integer conncetTimeOut, Integer readTimeOut, Map<String, String> headsMap, ContentType contentType) {
        return HttpToolsClient.getStrByPostFormDataAndContentType(requestUrl, parasmMap, charset, conncetTimeOut, readTimeOut, headsMap, contentType);
    }
}
