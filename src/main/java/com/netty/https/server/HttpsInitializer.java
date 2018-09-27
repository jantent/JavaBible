package com.netty.https.server;

import com.netty.https.sslcontext.ContextSSLFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author: tangJ
 * @Date: 2018/9/27 11:25
 * @description: chanell initial
 */
public class HttpsInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        // ssl 环境
        SSLEngine sslEngine = ContextSSLFactory.getInstance().getSslEngine();
        p.addLast("ssl",new SslHandler(sslEngine));

        p.addLast("codec",new HttpServerCodec());

        p.addLast("aggregator",new HttpObjectAggregator(1024 * 1024));

        p.addLast("handler",new HttpsHandler());

    }
}
