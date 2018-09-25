package com.cert;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * @author: tangJ
 * @Date: 2018/9/25 17:14
 * @Description:
 */
public class CertAndKey {
    /**
     * x509cert Ö¤Êé
     */
    private X509Certificate x509Certificate;

    /**
     * Ë½Ô¿
     */
    private PrivateKey privateKey;

    /**
     * ¹«Ô¿
     */
    private PublicKey publicKey;

    public X509Certificate getX509Certificate() {
        return x509Certificate;
    }

    public void setX509Certificate(X509Certificate x509Certificate) {
        this.x509Certificate = x509Certificate;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
