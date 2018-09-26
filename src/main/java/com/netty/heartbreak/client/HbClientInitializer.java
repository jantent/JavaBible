package com.netty.heartbreak.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;

/**
 * @author: tangJ
 * @Date: 2018/9/26 18:03
 * @description:
 */
public class HbClientInitializer extends ChannelInitializer<SocketChannel>{
    private HeartBeatClient client;

    public HbClientInitializer(HeartBeatClient client) {
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 10, 0));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("gbk")));
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("gbk")));
        pipeline.addLast("handler",new HeartBeatClientHandler(client));
    }
}
