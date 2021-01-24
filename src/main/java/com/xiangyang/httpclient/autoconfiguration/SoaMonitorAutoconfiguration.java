package com.xiangyang.httpclient.autoconfiguration;

import com.xiangyang.httpclient.ApolloConfigUtil;
import com.xiangyang.httpclient.utils.HttpClientFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class SoaMonitorAutoconfiguration {
    @PostConstruct
    public void init() {
        HttpClientFactory.Config config = HttpClientFactory.Config.builder().maxTotal(getMaxTotal()).defaultMaxPerRoute(getDefaultMaxPerRoute()).connectTimeout(getConnectTimeout()).readTimeout(getReadTimeout()).connectionRequestTimeout(getConnectionRequestTimeout()).hostMaxPerRoute(getHostMaxPerRoute()).retryRequestCount(getRetryRequestCount()).enableMonitor(getEnableMonitor()).monitorUseMetric(getMonitorUseMetric()).build();
        HttpClientFactory.httpClientScheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        HttpClientFactory.setGlobalConfigAndInitManager(config);
    }

    private Integer getMaxTotal() {
        return ApolloConfigUtil.getIntegerConfig("maxTotal", Integer.valueOf(500));
    }

    private Integer getDefaultMaxPerRoute() {
        return ApolloConfigUtil.getIntegerConfig("defaultMaxPerRoute", Integer.valueOf(50));
    }

    private Integer getConnectTimeout() {
        return ApolloConfigUtil.getIntegerConfig("connectTimeout", Integer.valueOf(5000));
    }

    private Integer getReadTimeout() {
        return ApolloConfigUtil.getIntegerConfig("readTimeout", Integer.valueOf(5000));
    }

    private Integer getConnectionRequestTimeout() {
        return ApolloConfigUtil.getIntegerConfig("connectionRequestTimeout", Integer.valueOf(2000));
    }

    private HashMap<String, Integer> getHostMaxPerRoute() {
        return ApolloConfigUtil.getMapConfig("hostMaxPerRoute");
    }

    private Integer getRetryRequestCount() {
        return ApolloConfigUtil.getIntegerConfig("retryRequestCount", Integer.valueOf(3));
    }

    private Boolean getEnableMonitor() {
        return Boolean.valueOf(ApolloConfigUtil.getBooleanConfig("enableMonitor", true));
    }

    private Boolean getMonitorUseMetric() {
        return Boolean.valueOf(ApolloConfigUtil.getBooleanConfig("monitorUseMetric", false));
    }
}
