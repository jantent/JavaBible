package com.netty.websocket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author: tangJ
 * @Date: 2018/11/22 11:43
 * @description:
 */
public class WebSocketInitial extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
        cp.addLast("codec",new HttpServerCodec());
        cp.addLast("chunk",new ChunkedWriteHandler());
        cp.addLast("aggregator",new HttpObjectAggregator(4 * 1024 * 1024));
        cp.addLast("webSocket",new WebSocketServerProtocolHandler("/ws"));
        cp.addLast("handler",new WebSocketHandler());
    }
}
