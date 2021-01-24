package com.xiangyang.http.client.model;


import java.io.Serializable;
public class HttpResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -2205856269177925903L;
    public static final int RESULT_SUCCESS = 0;

    public static final int RESULT_FAIL = -1;

    private int resultCode = 0;

    private String returnMsg;

    private T responseVo;

    private String monitorTrackId;

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

    public String getMonitorTrackId() {
        return this.monitorTrackId;
    }

    public void setMonitorTrackId(String monitorTrackId) {
        this.monitorTrackId = monitorTrackId;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String toString() {
        return "HttpResponseResult [responseVo=" + this.responseVo + ", monitorTrackId=" + this.monitorTrackId + "]";
    }

    public static <T> HttpResponseResult<T> transform(com.xiangyang.httpclient.model.HttpResponseResult<T> result) {
        HttpResponseResult<Object> httpResponseResult = new HttpResponseResult();
        httpResponseResult.setResultCode((result.getResultCode() == 200) ? 0 : result.getResultCode());
        httpResponseResult.setReturnMsg(result.getReturnMsg());
        httpResponseResult.setResponseVo(result.getResponseVo());
//        httpResponseResult.setMonitorTrackId(ZipkinContext.getContext().getMonitorTrackId());
        httpResponseResult.setException(result.getException());
        return (HttpResponseResult)httpResponseResult;
    }
}
