package com.xiangyang.httpclient;

import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpclientMonitor {
    private final boolean useMetric;

    private final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    private volatile boolean run;

    public HttpclientMonitor(boolean useMetric, PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        this.run = true;
        this.useMetric = useMetric;
        this.poolingHttpClientConnectionManager = poolingHttpClientConnectionManager;
    }

    public void start() {
        Thread thread = new Thread(new PoolMonitor());
        thread.start();
    }

    public void stop() {
        this.run = false;
    }

    private class PoolMonitor implements Runnable {
        private PoolMonitor() {}

        private void log2cat(String host, PoolStats poolStats) {
            String availableName = (host == null) ? "TotalPoolAvailable" : ("PoolAvailable/" + host);
            String leasedName = (host == null) ? "TotalPoolLeased" : ("PoolLeased/" + host);
            String maxName = (host == null) ? "TotalPoolMax" : ("PoolMax/" + host);
            String pendingName = (host == null) ? "TotalPoolPending" : ("PoolPending/" + host);
            if (HttpclientMonitor.this.useMetric) {
//                Cat.logMetricForCount(availableName, poolStats.getAvailable());
//                Cat.logMetricForCount(leasedName, poolStats.getLeased());
//                Cat.logMetricForCount(maxName, poolStats.getMax());
//                Cat.logMetricForCount(pendingName, poolStats.getPending());
            } else {
//                Cat.logEvent("HttpClientMonitor", availableName, "0", String.valueOf(poolStats.getAvailable()));
//                Cat.logEvent("HttpClientMonitor", leasedName, "0", String.valueOf(poolStats.getLeased()));
//                Cat.logEvent("HttpClientMonitor", maxName, "0", String.valueOf(poolStats.getMax()));
//                Cat.logEvent("HttpClientMonitor", pendingName, "0", String.valueOf(poolStats.getPending()));
            }
        }

        public void run() {
            while (HttpclientMonitor.this.run) {
                try {
                    TimeUnit.MINUTES.sleep(1L);
                    PoolStats totalStats = HttpclientMonitor.this.poolingHttpClientConnectionManager.getTotalStats();
                    if (null != totalStats)
                        log2cat(null, totalStats);
                    Set<HttpRoute> httpRoutesSet = HttpclientMonitor.this.poolingHttpClientConnectionManager.getRoutes();
                    if (null != httpRoutesSet && httpRoutesSet.size() > 0)
                        for (HttpRoute httpRoute : httpRoutesSet) {
                            String host = httpRoute.getTargetHost().getHostName();
                            PoolStats poolStats = HttpclientMonitor.this.poolingHttpClientConnectionManager.getStats(httpRoute);
                            if (poolStats != null)
                                log2cat(host, totalStats);
                        }
                } catch (Exception exception) {}
            }
        }
    }
}
