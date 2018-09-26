package com.netty.heartbreak.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: tangJ
 * @Date: 2018/9/26 08:59
 * @description:
 */
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String>{

    private HeartBeatClient client;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
            Charset.forName("gbk")));

    public HeartBeatClientHandler(HeartBeatClient client){
        this.client = client;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equals("Heartbeat")){
            ctx.writeAndFlush("has read message from server");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if (state.equals(IdleState.WRITER_IDLE)){
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 重连线程
     */
    private class ScheduleConnTask implements Runnable{
        @Override
        public void run() {
            client.doConncet();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("客户端与服务端断开连接，断开的时间为："+format.format(new Date()));
        // 定时线程 断线重连
        final EventLoop eventExecutors = ctx.channel().eventLoop();
        eventExecutors.schedule(new ScheduleConnTask(),10, TimeUnit.SECONDS);
    }
}
