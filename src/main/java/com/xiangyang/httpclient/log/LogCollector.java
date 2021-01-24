package com.xiangyang.httpclient.log;

public class LogCollector {
//    public static void save(String url, HttpUriRequest request, long startTime, long endTime, Object input, HttpResponseResult result) {
//        try {
//            SpanLog spanLog = getInvokeLogObject(url, request, startTime, endTime, input, result);
//            if (null == spanLog)
//                return;
//            Creeper.logTrace(spanLog);
//        } catch (Exception exception) {}
//    }
//
//    private static SpanLog getInvokeLogObject(String url, HttpUriRequest request, long startTime, long endTime, Object input, HttpResponseResult result) {
//        URI uri = request.getURI();
//        SpanLog spanLog = new SpanLog();
//        String providerApplication = url;
//        int index = url.indexOf("?");
//        if (index > -1)
//            providerApplication = url.substring(0, index);
//        try {
//            ZipkinContext context = ZipkinContext.getContext();
//            String localHost = ServerUtils.getLocalHostIp();
//            String serviceName = uri.getHost();
//            String methodName = uri.getPath();
//            spanLog.setGlobalTicket(context.getGlobalTicket());
//            spanLog.setRpcId(request.getFirstHeader("parentRpcId").getValue());
//            spanLog.setParentRpcId(context.getRpcId());
//            spanLog.setRpcEntryUrl(context.getRpcEntryUrl());
//            spanLog.setConsumerApplication(getApplicationNameByConfig());
//            spanLog.setConsumerHost(localHost);
//            spanLog.setConsumerHostPort(Integer.valueOf(8080));
//            spanLog.setProviderApplication(providerApplication);
//            spanLog.setProviderHost(uri.getHost());
//            spanLog.setProviderHostPort(Integer.valueOf(uri.getPort()));
//            spanLog.setInvokeType("consumer");
//            spanLog.setRpcType("HTTPCLIENT");
//            spanLog.setService(serviceName);
//            spanLog.setMethod(methodName);
//            spanLog.setInvokeTime(new Date(startTime));
//            spanLog.setElapsed(Long.valueOf(endTime - startTime));
//            int resultCode = result.getResultCode();
//            spanLog.setIsSuccess(result.is2XX() ? 1 : resultCode);
//            spanLog.setMonitorTrackId(context.getMonitorTrackId());
//            spanLog.setInput(JSON.toJSONString(input));
//            spanLog.setOutput(result.getResponseVo());
//            spanLog.setReturnCode(String.valueOf(resultCode));
//            spanLog.setReturnMsg(result.getReturnMsg());
//        } catch (Exception exception) {}
//        return spanLog;
//    }
//
//    private static String getApplicationNameByConfig() {
//        String applicationName = HostUtil.getAppId();
//        if (StringUtils.isEmpty(applicationName))
//            applicationName = "unknownApplication";
//        return applicationName;
//    }
}
