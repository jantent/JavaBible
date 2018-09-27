package com.netty.https.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: tangJ
 * @Date: 2018/9/27 11:18
 * @description:
 */
public class HttpsServer {

    private int port;


    public HttpsServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpsInitializer());

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        System.out.println("server start ,port: " + port);
        // 监听服务器 关闭监听
        channelFuture.channel().closeFuture().sync();
    }
}
