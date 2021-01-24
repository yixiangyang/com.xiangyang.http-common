package com.xiangyang.httpclient.client;

import com.alibaba.fastjson.TypeReference;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.Map;

public class GetClient<T> extends AbstractHttpClient<T> {
    private HttpGet httpGet;

    private boolean forJson = true;

    public GetClient() {
        new GetClient((Class)String.class);
    }

    public GetClient(Class<T> clz) {
        super.setResponseClass(clz);
    }

    public GetClient(TypeReference<T> typeReference) {
        super.setTypeReference(typeReference);
    }

    public GetClient<T> withConfig(HttpClientFactory.Config config) {
        super.setConfig(config);
        return this;
    }

    public GetClient<T> addHeader(String key, String value) {
        super.addHeader(key, value);
        return this;
    }

    public GetClient<T> addHeaders(Map<String, String> headers) {
        super.addHeaders(headers);
        return this;
    }

    public GetClient<T> addUrlParameter(String key, Object value) {
        super.addUrlParameter(key, value);
        return this;
    }

    public GetClient<T> addUrlParameters(Map<String, Object> parameters) {
        super.addUrlParameters(parameters);
        return this;
    }

    public GetClient<T> with(String url, Class<T> clz) {
        return with(url, null, clz);
    }

    public GetClient<T> with(String url, Object requestBody) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        return this;
    }

    public GetClient<T> with(String url, Object requestBody, Class<T> clz) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        super.setResponseClass(clz);
        return this;
    }

    public GetClient<T> withForJson(boolean forJson) {
        this.forJson = forJson;
        return this;
    }

    public GetClient<T> withHttpGet(HttpGet httpGet) {
        this.httpGet = httpGet;
        return this;
    }

    public GetClient<T> withUrl(String url) {
        super.setUrl(url);
        super.setUrlObj(URL.valueOf(url));
        return this;
    }

    public GetClient<T> withHeaders(Map<String, String> headers) {
        super.setHeaders(headers);
        return this;
    }

    public GetClient<T> withRequestBodyObj(Object requestBodyObj) {
        super.setRequestBodyObj(requestBodyObj);
        return this;
    }

    public GetClient<T> withResponseClass(Class<T> responseClass) {
        super.setResponseClass(responseClass);
        return this;
    }

    public GetClient<T> withResponseHandler(ResponseHandler<T> responseHandler) {
        super.setResponseHandler(responseHandler);
        return this;
    }

    protected HttpUriRequest getHttpUriRequest() {
        HttpGet httpGet = this.httpGet;
        if (httpGet == null) {
            String url = super.getUrl();
            if (StringUtils.isBlank(url))
                throw new IllegalArgumentException("Url must not be null.");
            httpGet = new HttpGet(url);
        }
        resolveHttpHeader((HttpUriRequest)httpGet);
        if (this.forJson)
            httpGet.addHeader("Content-Type", "application/json");
        return (HttpUriRequest)httpGet;
    }
}
