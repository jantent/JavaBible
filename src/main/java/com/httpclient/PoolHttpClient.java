package com.httpclient;

import com.httpclient.sslcontext.ClientSslFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author: tangJ
 * @Date: 2018/9/27 09:33
 * @description:
 */
public class PoolHttpClient {

    // 池中最大连接数
    private static final int DEFAULT_POOL_MAX_TOTAL = 50;
    // 最大路由配置数量
    private static final int DEFAULT_POOL_MAX_PER_ROUTE = 200;

    // 默认连接超时时间
    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    // 默认从连接池获取连接超时时间
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 10 * 1000;
    // 默认TCP传输 超时时间
    private static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;


    // 连接池管理器
    private PoolingHttpClientConnectionManager gcm = null;

    private CloseableHttpClient httpClient = null;

    // 连接清理线程
    private IdleConnectionMonitorThread idleThread = null;

    // 连接池的最大连接数
    private final int maxTotal;
    // 连接池按route配置的最大连接数
    private final int maxPerRoute;

    // tcp connect的超时时间
    private final int connectTimeout;
    // 从连接池获取连接的超时时间
    private final int connectRequestTimeout;
    // tcp io的读写超时时间
    private final int socketTimeout;


    public PoolHttpClient() {
        this(
                PoolHttpClient.DEFAULT_POOL_MAX_TOTAL,
                PoolHttpClient.DEFAULT_POOL_MAX_PER_ROUTE,
                PoolHttpClient.DEFAULT_CONNECT_TIMEOUT,
                PoolHttpClient.DEFAULT_CONNECT_REQUEST_TIMEOUT,
                PoolHttpClient.DEFAULT_SOCKET_TIMEOUT
        );
    }

    public PoolHttpClient(int maxTotal, int maxPerRoute, int connectTimeout, int connectRequestTimeout, int socketTimeout) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.connectTimeout = connectTimeout;
        this.connectRequestTimeout = connectRequestTimeout;
        this.socketTimeout = socketTimeout;

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", ClientSslFactory.getInstance().getSslFac())
                .build();

        this.gcm = new PoolingHttpClientConnectionManager(registry);
        this.gcm.setMaxTotal(maxTotal);
        this.gcm.setDefaultMaxPerRoute(maxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时
                .setConnectTimeout(this.connectTimeout)
                // 设置读取超时
                .setSocketTimeout(this.socketTimeout)
                // 设置从连接池获取连接实例的超时
                .setConnectionRequestTimeout(this.connectRequestTimeout)
                .build();

        HttpClientBuilder builder = HttpClients.custom();
        httpClient = builder
                .setConnectionManager(this.gcm)
                .setDefaultRequestConfig(requestConfig)
                .build();

        idleThread = new IdleConnectionMonitorThread(this.gcm);
        idleThread.start();
    }

    public String doGet(String url) {
        return this.doGet(url, Collections.EMPTY_MAP, Collections.EMPTY_MAP);
    }

    public String doGet(String url, Map<String, Object> params) {
        return this.doGet(url, Collections.EMPTY_MAP, params);
    }

    public String doGet(String url,
                        Map<String, String> headers,
                        Map<String, Object> params
    ) {

        // *) 构建GET请求头
        String apiUrl = getUrlWithParams(url, params);
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

        // *) 设置header信息
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return handlerResp(httpGet);
    }

    /**
     * 发送post 请求
     *
     * @param apiUrl url地址
     * @param content content内容
     * @return 响应内容
     */
    public String doPost(String apiUrl,String content) {

        HttpPost httpPost = new HttpPost(apiUrl);

        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

        HttpEntity entityReq = new StringEntity(content,Charset.forName("utf-8"));
        httpPost.setEntity(entityReq);
        return handlerResp(httpPost);
    }

    private String handlerResp(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            if (response == null || response.getStatusLine() == null) {
                return null;
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entityRes = response.getEntity();
                if (entityRes != null) {
                    return EntityUtils.toString(entityRes, "UTF-8");
                }
            }
            return null;
        } catch (IOException e) {
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    private String getUrlWithParams(String url, Map<String, Object> params) {
        boolean first = true;
        StringBuilder sb = new StringBuilder(url);
        for (String key : params.keySet()) {
            char ch = '&';
            if (first == true) {
                ch = '?';
                first = false;
            }
            String value = params.get(key).toString();
            try {
                String sval = URLEncoder.encode(value, "UTF-8");
                sb.append(ch).append(key).append("=").append(sval);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return sb.toString();
    }
}
