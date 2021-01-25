package com.xiangyang.httpclient.client;

import com.alibaba.fastjson.TypeReference;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.xiangyang.httpclient.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.util.Map;

public class DeleteClient<T> extends AbstractHttpClient<T> {
    private HttpDelete httpDelete;

    private boolean forJson = Boolean.TRUE;

    public DeleteClient() {
        new DeleteClient((Class)String.class);
    }

    public DeleteClient(Class<T> clz) {
        super.setResponseClass(clz);
    }

    public DeleteClient(TypeReference<T> typeReference) {
        super.setTypeReference(typeReference);
    }

    public DeleteClient<T> withConfig(HttpClientFactory.Config config) {
        super.setConfig(config);
        return this;
    }

    public DeleteClient<T> addHeader(String key, String value) {
        super.addHeader(key, value);
        return this;
    }

    public DeleteClient<T> addHeaders(Map<String, String> headers) {
        super.addHeaders(headers);
        return this;
    }

    public DeleteClient<T> addUrlParameter(String key, Object value) {
        super.addUrlParameter(key, value);
        return this;
    }

    public DeleteClient<T> addUrlParameters(Map<String, Object> parameters) {
        super.addUrlParameters(parameters);
        return this;
    }

    public DeleteClient<T> with(String url, Class<T> clz) {
        return with(url, null, clz);
    }

    public DeleteClient<T> with(String url, Object requestBody) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        return this;
    }

    public DeleteClient<T> with(String url, Object requestBody, Class<T> clz) {
        super.setUrl(url);
        super.setRequestBodyObj(requestBody);
        super.setResponseClass(clz);
        return this;
    }

    public DeleteClient<T> withForJson(boolean forJson) {
        this.forJson = forJson;
        return this;
    }

    public DeleteClient<T> withHttpDelete(HttpDelete httpDelete) {
        this.httpDelete = httpDelete;
        return this;
    }

    public DeleteClient<T> withUrl(String url) {
        super.setUrl(url);
        super.setUrlObj(URL.valueOf(url));
        return this;
    }

    public DeleteClient<T> withHeaders(Map<String, String> headers) {
        super.setHeaders(headers);
        return this;
    }

    public DeleteClient<T> withRequestBodyObj(Object requestBodyObj) {
        super.setRequestBodyObj(requestBodyObj);
        return this;
    }

    public DeleteClient<T> withResponseClass(Class<T> responseClass) {
        super.setResponseClass(responseClass);
        return this;
    }

    public DeleteClient<T> withResponseHandler(HttpClientResponseHandler<T> responseHandler) {
        super.setResponseHandler(responseHandler);
        return this;
    }

    protected HttpUriRequest getHttpUriRequest() {
        HttpDelete httpDelete = this.httpDelete;
        if (httpDelete == null) {
            String url = super.getUrl();
            if (StringUtils.isBlank(url))
                throw new IllegalArgumentException("Url must not be null.");
            httpDelete = new HttpDelete(url);
        }
        resolveHttpHeader((HttpUriRequest)httpDelete);
        if (this.forJson)
            httpDelete.addHeader("Content-Type", "application/json");
        return (HttpUriRequest)httpDelete;
    }
}
