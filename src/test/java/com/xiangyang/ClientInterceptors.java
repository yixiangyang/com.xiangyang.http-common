package com.xiangyang;

import org.apache.hc.client5.http.classic.ExecChain;
import org.apache.hc.client5.http.classic.ExecChainHandler;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.ChainElement;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

public class ClientInterceptors {
    public static void main(String[] args) {
        try {
            final CloseableHttpClient httpclient = HttpClients.custom()
                    .addRequestInterceptorFirst(new HttpRequestInterceptor() {
                        private final AtomicLong count = new AtomicLong(0);
                        @Override
                        public void process(HttpRequest request, EntityDetails entity, HttpContext context) throws HttpException, IOException {
                            request.setHeader("request-id", Long.toString(count.incrementAndGet()));
                        }
                    })
                    .addExecInterceptorAfter(ChainElement.PROTOCOL.name(), "custom", new ExecChainHandler() {

                        @Override
                        public ClassicHttpResponse execute(ClassicHttpRequest classicHttpRequest, ExecChain.Scope scope, ExecChain execChain) throws IOException, HttpException {
                            final Header idHeader = classicHttpRequest.getFirstHeader("request-id");
                                if (idHeader != null && "13".equalsIgnoreCase(idHeader.getValue())) {
                                    final ClassicHttpResponse response = new BasicClassicHttpResponse(HttpStatus.SC_NOT_FOUND, "Oppsie");
                                    response.setEntity(new StringEntity("bad luck", ContentType.TEXT_PLAIN));
                                    return response;
                                    } else {
                                    return execChain.proceed(classicHttpRequest, scope);
                                }
                        }
                    }).build();
                for (int i = 0; i < 20; i++) {
                    final HttpGet httpget = new HttpGet("http://httpbin.org/get");
                    System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri());

                    try (final CloseableHttpResponse response = httpclient.execute(httpget)) {
                        System.out.println("----------------------------------------");
                        System.out.println(response.getCode() + " " + response.getReasonPhrase());
                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }
                }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
