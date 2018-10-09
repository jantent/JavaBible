package com;

import com.netty.https.server.HttpsServer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author: tangJ
 * @Date: 2018/10/9 18:26
 * @description:
 */
public class MainApplication {
    private static int port = 8443;

    public static void main(String args[]) throws Exception{
        HttpsServer httpsServer = new HttpsServer(port);
        httpsServer.start();
    }


}
