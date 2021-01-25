package com.xiangyang.httpclient.client;

import com.alibaba.fastjson.TypeReference;
import java.util.Map;

import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.entity.StringEntity;

public class PostClient<T> extends AbstractHttpClient<T> {
    private HttpPost httpPost;

    private HttpEntity httpEntity;

    private boolean forJson = true;

    public PostClient() {
        new PostClient((Class)String.class);
    }

    public PostClient(Class<T> clz) {
        super.setResponseClass(clz);
    }

    public PostClient(TypeReference<T> typeReference) {
        super.setTypeReference(typeReference);
    }

    public PostClient<T> withConfig(HttpClientFactory.Config config) {
        super.setConfig(config);
        return this;
    }

    public PostClient<T> addHeader(String key, String value) {
        super.addHeader(key, value);
        return this;
    }

    public PostClient<T> addHeaders(Map<String, String> headers) {
        super.addHeaders(headers);
        return this;
    }

    public PostClient<T> addUrlParameter(String key, Object value) {
        super.addUrlParameter(key, value);
        return this;
    }

    public PostClient<T> addUrlParameters(Map<String, Object> parameters) {
        super.addUrlParameters(parameters);
        return this;
    }

    public PostClient<T> with(String url, Class<T> clz) {
        return with(url, null, clz);
    }

    public PostClient<T> with(String url, Object requestBody) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        return this;
    }

    public PostClient<T> with(String url, Object requestBody, Class<T> clz) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        super.setResponseClass(clz);
        return this;
    }

    public PostClient<T> withForJson(boolean forJson) {
        this.forJson = forJson;
        return this;
    }

    public PostClient<T> withHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
        return this;
    }

    public PostClient<T> withHttpPost(HttpPost httpPost) {
        this.httpPost = httpPost;
        return this;
    }

    public PostClient<T> withUrl(String url) {
        super.setUrl(url);
        super.setUrlObj(URL.valueOf(url));
        return this;
    }

    public PostClient<T> withHeaders(Map<String, String> headers) {
        super.setHeaders(headers);
        return this;
    }

    public PostClient<T> withRequestBodyObj(Object requestBodyObj) {
        super.setRequestBodyObj(requestBodyObj);
        return this;
    }

    public PostClient<T> withResponseClass(Class<T> responseClass) {
        super.setResponseClass(responseClass);
        return this;
    }

    public PostClient<T> withResponseHandler(HttpClientResponseHandler<T> responseHandler) {
        super.setResponseHandler(responseHandler);
        return this;
    }

    protected HttpUriRequest getHttpUriRequest() {
        StringEntity stringEntity=null;
        HttpPost httpPost = this.httpPost;
        if (httpPost == null) {
            String url = super.getUrl();
            if (StringUtils.isBlank(url))
                throw new IllegalArgumentException("Url must not be null.");
            httpPost = new HttpPost(url);
        }
        resolveHttpHeader((HttpUriRequest)httpPost);
        if (this.forJson)
            httpPost.addHeader("Content-Type", "application/json");
        HttpEntity httpEntity = this.httpEntity;
        if (httpEntity == null) {
            Object requestBodyObj = getRequestBodyObj();
            if (requestBodyObj != null) {
                StringEntity stringEntry = getStringEntry(requestBodyObj);
                if (this.forJson)
//                    stringEntity = new StringEntity()
//                    stringEntry.setContentType("application/json");
                stringEntity = stringEntry;
            }
        }
        httpPost.setEntity((HttpEntity)stringEntity);
        return (HttpUriRequest)httpPost;
    }
}
