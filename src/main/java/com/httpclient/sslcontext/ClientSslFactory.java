package com.httpclient.sslcontext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author: tangJ
 * @Date: 2018/9/27 14:00
 * @description: 客户端ssl环境
 */
public class ClientSslFactory {

    // 随机数种子
    private String seed = "78qr1sf5qwrrwe";

    // 安全随机数
    private SecureRandom secureRandom = new SecureRandom(seed.getBytes());

    private static class Inner {
        static ClientSslFactory instance = new ClientSslFactory();
    }

    private SSLConnectionSocketFactory sslsf = null;

    private ClientSslFactory() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClientSslFactory getInstance() {
        return Inner.instance;
    }

    private void init() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();

        // 默认不需要随机数，特殊情况再加上
        builder.setSecureRandom(null);
        // 全部信任，不对服务端的证书进行校验
        builder.loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                for (int i= 0;i<x509Certificates.length;i++){
                    System.out.println("服务端的证书为： "+x509Certificates[i].toString());
                }
                return true;
            }
        });

        sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
    }

    /**
     * 获取ssl环境
     *
     * @return
     */
    public SSLConnectionSocketFactory getSslFac() {
        return sslsf;
    }
}
