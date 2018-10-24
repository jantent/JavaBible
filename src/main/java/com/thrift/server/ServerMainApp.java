package com.thrift.server;

/**
 * @author: tangJ
 * @Date: 2018/10/24 16:42
 * @description:
 */
public class ServerMainApp {
    public static void main(String args[]){
        ThriftServer thriftServer = ThriftServer.getInstance();
        thriftServer.start();
    }
}
