package com.xiangyang.httpclient.model;

import java.io.Serializable;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;

public class HttpResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 8056837669737531875L;

    public static final int RESULT_SUCCESS = 200;

    private int resultCode = 200;

    private String returnMsg;

    private T responseVo;

    private Exception exception;

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getResponseVo() {
        return this.responseVo;
    }

    public void setResponseVo(T responseVo) {
        this.responseVo = responseVo;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public HttpResponseResult<T> resolve(CloseableHttpResponse httpResponse, T vo) {
        StatusLine statusLine = httpResponse.getStatusLine();
        this.resultCode = statusLine.getStatusCode();
        this.responseVo = vo;
        return this;
    }

    public boolean is1XX() {
        return isXXX(100, 200);
    }

    public boolean is2XX() {
        return isXXX(200, 300);
    }

    public boolean is3XX() {
        return isXXX(300, 400);
    }

    public boolean is4XX() {
        return isXXX(400, 500);
    }

    public boolean is5XX() {
        return isXXX(500, 600);
    }

    private boolean isXXX(int minxVal, int maxVal) {
        return (this.resultCode >= minxVal && this.resultCode < maxVal);
    }

    public String toString() {
//        ZipkinContext context = ZipkinContext.getContext();
        return "HttpResponseResult [responseVo=" + this.responseVo + ", monitorTrackId=" + "" + "]";
    }
}
