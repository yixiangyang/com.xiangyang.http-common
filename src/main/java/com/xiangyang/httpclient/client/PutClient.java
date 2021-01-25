package com.xiangyang.httpclient.client;

import com.alibaba.fastjson.TypeReference;
import java.util.Map;

import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class PutClient<T> extends AbstractHttpClient<T> {
    private HttpPut httpPut;

    private HttpEntity httpEntity;

    private boolean forJson = Boolean.TRUE;

    public PutClient() {
        new PutClient((Class)String.class);
    }

    public PutClient(Class<T> clz) {
        super.setResponseClass(clz);
    }

    public PutClient(TypeReference<T> typeReference) {
        super.setTypeReference(typeReference);
    }

    public PutClient<T> withConfig(HttpClientFactory.Config config) {
        super.setConfig(config);
        return this;
    }

    public PutClient<T> addHeader(String key, String value) {
        super.addHeader(key, value);
        return this;
    }

    public PutClient<T> addHeaders(Map<String, String> headers) {
        super.addHeaders(headers);
        return this;
    }

    public PutClient<T> addUrlParameter(String key, Object value) {
        super.addUrlParameter(key, value);
        return this;
    }

    public PutClient<T> addUrlParameters(Map<String, Object> parameters) {
        super.addUrlParameters(parameters);
        return this;
    }

    public PutClient<T> with(String url, Class<T> clz) {
        return with(url, null, clz);
    }

    public PutClient<T> with(String url, Object requestBody) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        return this;
    }

    public PutClient<T> with(String url, Object requestBody, Class<T> clz) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        super.setResponseClass(clz);
        return this;
    }

    public PutClient<T> withForJson(boolean forJson) {
        this.forJson = forJson;
        return this;
    }

    public PutClient<T> withHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
        return this;
    }

    public PutClient<T> withHttpPut(HttpPut httpPut) {
        this.httpPut = httpPut;
        return this;
    }

    public PutClient<T> withUrl(String url) {
        super.setUrl(url);
        super.setUrlObj(URL.valueOf(url));
        return this;
    }

    public PutClient<T> withHeaders(Map<String, String> headers) {
        super.setHeaders(headers);
        return this;
    }

    public PutClient<T> withRequestBodyObj(Object requestBodyObj) {
        super.setRequestBodyObj(requestBodyObj);
        return this;
    }

    public PutClient<T> withResponseClass(Class<T> responseClass) {
        super.setResponseClass(responseClass);
        return this;
    }

    public PutClient<T> withResponseHandler(HttpClientResponseHandler<T> responseHandler) {
        super.setResponseHandler(responseHandler);
        return this;
    }

    protected HttpUriRequest getHttpUriRequest() {
        StringEntity stringEntity = null;
        HttpPut httpPut = this.httpPut;
        if (httpPut == null) {
            String url = super.getUrl();
            if (StringUtils.isBlank(url))
                throw new IllegalArgumentException("Url must not be null.");
            httpPut = new HttpPut(url);
        }
        resolveHttpHeader((HttpUriRequest)httpPut);
        if (this.forJson)
            httpPut.addHeader("Content-Type", "application/json");
        HttpEntity httpEntity = this.httpEntity;
        if (httpEntity == null) {
            Object requestBodyObj = getRequestBodyObj();
            if (requestBodyObj != null) {
                StringEntity stringEntry = getStringEntry(requestBodyObj);
//                if (this.forJson)
//                    stringEntry.setContentType("application/json");
                stringEntity = stringEntry;
            }
        }
        httpPut.setEntity((HttpEntity)stringEntity);
        return (HttpUriRequest)httpPut;
    }
}
