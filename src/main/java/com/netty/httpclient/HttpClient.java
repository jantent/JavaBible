package com.netty.httpclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author: tangJ
 * @Date: 2018/9/26 18:16
 * @description:
 */
public class HttpClient {

    public Channel channel;

    public Bootstrap bootstrap;

    public URI uri;

    public void connect(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitialize());
            channel = bootstrap.connect(new InetSocketAddress(uri)).channel();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
