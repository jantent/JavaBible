package com.netty.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: tangJ
 * @Date: 2018/11/22 13:19
 * @description:
 */
public class WbServer {
    static int threadNum = Runtime.getRuntime().availableProcessors();
    static int port = 5879;

    public static void main(String args[]) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(threadNum * 2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(threadNum * 4);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketInitial());

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        System.out.println("server start ,port: " + port);
        // 监听服务器 关闭监听
        channelFuture.channel().closeFuture().sync();
    }
}
