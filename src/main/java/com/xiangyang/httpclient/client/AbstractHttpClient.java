package com.xiangyang.httpclient.client;

import com.alibaba.fastjson.JSON;
import com.xiangyang.httpclient.model.HttpResponseResult;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import com.alibaba.fastjson.TypeReference;
import com.xiangyang.httpclient.XiangYangHttpClient;
import com.xiangyang.httpclient.exception.HttpClientException;
import com.xiangyang.httpclient.utils.URL;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.apache.hc.core5.http.ContentType.APPLICATION_FORM_URLENCODED;

abstract class AbstractHttpClient<T> {
    protected static final String HEADER_CONTENT_TYPE_APPLICATION_JONS = "application/json";

    private String url;

    private URL urlObj;

    private HttpEntity httpEntity;

    private Map<String, String> headers;

    private Object requestBodyObj;

    private Class<T> responseClass;

    private TypeReference<T> typeReference;

    private HttpClientResponseHandler<T> responseHandler;

    private HttpClientFactory.Config config;


    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlObj(URL urlObj) {
        this.urlObj = urlObj;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setRequestBodyObj(Object requestBodyObj) {
        this.requestBodyObj = requestBodyObj;
    }

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public void setTypeReference(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    public void setResponseHandler(HttpClientResponseHandler<T> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void setConfig(HttpClientFactory.Config config) {
        this.config = config;
    }

    public String getUrl() {
        return this.url;
    }

    public URL getUrlObj() {
        return this.urlObj;
    }

    public HttpEntity getHttpEntity() {
        return this.httpEntity;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Object getRequestBodyObj() {
        return this.requestBodyObj;
    }

    public Class<T> getResponseClass() {
        return this.responseClass;
    }

    public TypeReference<T> getTypeReference() {
        return this.typeReference;
    }

    public HttpClientResponseHandler<T> getResponseHandler() {
        return this.responseHandler;
    }

    public HttpClientFactory.Config getConfig() {
        return this.config;
    }

    protected void resolveHttpHeader(HttpUriRequest request) {
        if (MapUtils.isNotEmpty(this.headers))
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(key))
                    request.addHeader(key, value);
            }
    }

    public T execute() {
        HttpResponseResult<T> responseResult = executeForResult();
        return (T)responseResult.getResponseVo();
    }

    public HttpResponseResult<T> executeForResult() {
        try {
            if (this.config == null)
                return XiangYangHttpClient.execute(getHttpUriRequest(), this.requestBodyObj, resolveResponseHander());
            return XiangYangHttpClient.execute(getHttpUriRequest(), this.requestBodyObj, resolveResponseHander(), this.config);
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }

    public AbstractHttpClient<T> addHeader(String key, String value) {
        Map<String, String> headers = this.headers;
        if (this.headers == null)
            this.headers = new HashMap<>();
        this.headers.put(key, value);
        return this;
    }

    public AbstractHttpClient<T> addHeaders(Map<String, String> headers) {
        if (headers != null && headers.size() > 0)
            for (Map.Entry<String, String> entry : headers.entrySet())
                addHeader(entry.getKey(), entry.getValue());
        return this;
    }

    public AbstractHttpClient<T> addUrlParameter(String key, Object value) {
        if (this.urlObj == null) {
            if (this.url == null)
                throw new IllegalArgumentException("Url can't bu null when addUrlParameter.");
            this.urlObj = URL.valueOf(this.url);
        }
        addParameter(key, value);
        this.url = this.urlObj.toString();
        return this;
    }

    private void addParameter(String key, Object value) {
        if (value == null) {
            this.urlObj.addParameter(key, "");
        } else {
            this.urlObj.addParameter(key, value.toString());
        }
    }

    public AbstractHttpClient<T> addUrlParameters(Map<String, Object> parameters) {
        if (this.urlObj == null) {
            if (this.url == null)
                throw new IllegalArgumentException("Url can't bu null when addUrlParameter.");
            this.urlObj = URL.valueOf(this.url);
        }
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, Object> entry : parameters.entrySet())
                addParameter(entry.getKey(), entry.getValue());
            this.url = this.urlObj.toString();
        }
        return this;
    }

    protected HttpClientResponseHandler<T> resolveResponseHander() {
        HttpClientResponseHandler<T> responseHander = this.responseHandler;
        if (responseHander == null) {
            Class<T> responseClass = this.responseClass;
            if (responseClass == null && this.typeReference == null)
                throw new IllegalArgumentException("Can't found responseHandler.");
            if (this.typeReference != null) {
                responseHander = XiangYangHttpClient.getJsonResponseHandler(this.typeReference);
            } else if (String.class.equals(responseClass)) {
                BasicHttpClientResponseHandler basicResponseHandler = new BasicHttpClientResponseHandler();
                responseHander = (HttpClientResponseHandler<T>) basicResponseHandler;
            } else {
                responseHander = XiangYangHttpClient.getJsonResponseHandler(responseClass);
            }
        }
        return responseHander;
    }

    public static StringEntity getStringEntry(Object obj) {
        ContentType contentType = ContentType.create("application/x-www-form-urlencoded","UTF-8");
        StringEntity ee = new StringEntity(JSON.toJSONString(obj),contentType,"UTF-8",false);
//        StringEntity entity = new StringEntity(JSON.toJSONString(obj), Charset.forName("UTF-8"));
//        entity.setContentEncoding("UTF-8");
        return entity;
    }

    @Deprecated
    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    protected abstract HttpUriRequest getHttpUriRequest();
}
