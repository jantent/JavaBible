package com.netty.https.sslcontext;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

/**
 * @author: tangJ
 * @Date: 2018/9/27 11:31
 * @description: ssl环境
 */
public class ContextSSLFactory {


    // keystore地址
    private static String keyStorePath = "./src/main/resources/keystore/keystore.jks";

    // keystore密码
    private static String keyStorePwd = "123456";

    private static final SSLContext SSL_CONTEXT_S;


    // 加载ssl环境
    static {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSLv3");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        try {
            if (getKeyManagersServer() != null && getTrustManagers() != null) {
                sslContext.init(getKeyManagersServer(), getTrustManagers(), null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sslContext.createSSLEngine().getSupportedCipherSuites();
        SSL_CONTEXT_S = sslContext;
    }

    public static SSLContext getSslContext() {
        return SSL_CONTEXT_S;
    }

    /**
     * 加载keystore
     * @return
     */
    private static KeyStore getKeyStore(){
        FileInputStream is = null;
        KeyStore ks = null;
        try {
            is = new FileInputStream(new File(keyStorePath));
            ks = KeyStore.getInstance("JKS");
            ks.load(is, keyStorePwd.toCharArray());

        }catch (Exception e){
           e.printStackTrace();
        }finally {
            try {
                if (is!=null){
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
     * @return TrustManager[]
     */
    private static TrustManager[] getTrustManagers() {

        TrustManager[] kms = null;
        try {
            TrustManagerFactory keyFac = TrustManagerFactory.getInstance("SunX509");
            keyFac.init(getKeyStore());
            kms = keyFac.getTrustManagers();
        }catch (Exception e){
            e.printStackTrace();
        }
        return kms;
    }

    /**
     * 获取服务端信参数
     * @return KeyManager[]
     */
    private static KeyManager[]  getKeyManagersServer(){
        KeyManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            KeyManagerFactory keyFac = KeyManagerFactory.getInstance("SunX509");
            keyFac.init(getKeyStore(),keyStorePwd.toCharArray());
            kms = keyFac.getKeyManagers();
        }catch (Exception e){
            e.printStackTrace();
        }
        return kms;
    }
}
