package com.netty.heartbreak.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: tangJ
 * @Date: 2018/9/26 09:11
 * @description:
 */
public class HeartBeatClient {

    private Random random = new Random(System.currentTimeMillis());

    public Channel channel;

    public Bootstrap bootstrap;

    private static String host = "127.0.0.1";
    private static int port = 9817;

    /**
     * 启动
     *
     * @throws Exception
     */
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HbClientInitializer(HeartBeatClient.this));
            doConncet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 连接服务端
     */
    public void doConncet() {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (channelFuture.isSuccess()) {
                    channel = futureListener.channel();
                    System.out.println("connect server successfully");
                } else {
                    System.out.println("Failed to connect to server, try connect after 10s");
                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConncet();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });

    }


}
