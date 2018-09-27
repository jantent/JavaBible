package com.netty.https;

import com.netty.https.server.HttpsServer;

/**
 * @author: tangJ
 * @Date: 2018/9/27 13:20
 * @description:
 */
public class MainApp {

    private static int port = 8443;

    public static void main(String args[]) throws Exception{
        HttpsServer httpsServer = new HttpsServer(port);
        httpsServer.start();
    }
}
