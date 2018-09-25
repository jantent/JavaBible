package com.netty.heartbreak.server;

/**
 * @author tangj
 * @date 2018/9/25 23:36
 * @description:
 */
public class ServerMainApp {
    public static void main(String args[]){
        new HeartBeatServer().start();
    }
}
