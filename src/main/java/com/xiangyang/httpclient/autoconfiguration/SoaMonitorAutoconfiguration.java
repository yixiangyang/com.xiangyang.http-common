package com.xiangyang.httpclient.autoconfiguration;

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
        return Integer.valueOf(500);
    }

    private Integer getDefaultMaxPerRoute() {
        return  Integer.valueOf(50);
    }

    private Integer getConnectTimeout() {
        return  Integer.valueOf(5000);
    }

    private Integer getReadTimeout() {
        return  Integer.valueOf(5000);
    }

    private Integer getConnectionRequestTimeout() {
        return Integer.valueOf(2000);
    }

    private HashMap<String, Integer> getHostMaxPerRoute() {
        return new HashMap<>();
    }

    private Integer getRetryRequestCount() {
        return Integer.valueOf(3);
    }

    private Boolean getEnableMonitor() {
        return Boolean.TRUE;
    }

    private Boolean getMonitorUseMetric() {
        return Boolean.FALSE;
    }
}
