package com.netty.https.sslcontext;

import com.MainApplication;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author: tangJ
 * @Date: 2018/9/27 11:31
 * @description: ssl环境
 */
public class ContextSSLFactory {


    // keystore地址
    private static String keyStorePath = "keystore/test.jks";

    // keystore密码
    private static String keyStorePwd = "123456";

    // ssl协议的版本
    private static String sslVersion = "SSLv3";

    // 随机数种子
    private String seed = "78qr1sf5qwrrwe";

    // 安全随机数
    private SecureRandom secureRandom = new SecureRandom(seed.getBytes());

    // ssl 环境
    private SSLEngine sslEngine = null;


    private static class Inner {
        static ContextSSLFactory instance = new ContextSSLFactory();
    }

    public static ContextSSLFactory getInstance() {
        return Inner.instance;
    }

    private ContextSSLFactory() {
        init();
    }

    /**
     * 获取已经创建好的ssl engine
     * @return
     */
    public SSLEngine getSslEngine() {
        return sslEngine;
    }

    /**
     * 加载ssl 环境
     */
    private void init() {
        try {
            KeyStore keyStore = getKeyStore();
            TrustManager[] trustManagers = getTrustManagers(keyStore);
            KeyManager[] keyManagers = getKeyManagersServer(keyStore);
            SSLContext sslContext = SSLContext.getInstance(sslVersion);

            // 默认不需要随机数种子，特殊情况再加上
            sslContext.init(keyManagers, trustManagers, null);
            sslContext.createSSLEngine().getSupportedCipherSuites();
            sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath() {
        String path = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        return path;

    }
    /**
     * 加载keystore
     *
     * @return
     */
    private KeyStore getKeyStore() {
        FileInputStream is = null;
        KeyStore ks = null;
        try {
            is = new FileInputStream(new File(getFilePath()+keyStorePath));
            ks = KeyStore.getInstance("JKS");
            ks.load(is, keyStorePwd.toCharArray());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ks;
    }

    /**
     * 获取信任参数
     *
     * @return TrustManager[]
     */
    private TrustManager[] getTrustManagers(KeyStore keyStore) {

        TrustManager[] kms = null;
        try {
            TrustManagerFactory keyFac = TrustManagerFactory.getInstance("SunX509");
            keyFac.init(keyStore);
            kms = keyFac.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kms;
    }

    /**
     * 获取服务端信参数
     *
     * @return KeyManager[]
     */
    private KeyManager[] getKeyManagersServer(KeyStore keyStore) {
        KeyManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            KeyManagerFactory keyFac = KeyManagerFactory.getInstance("SunX509");
            keyFac.init(keyStore, keyStorePwd.toCharArray());
            kms = keyFac.getKeyManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kms;
    }
}
