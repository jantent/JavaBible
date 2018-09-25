package com.netty.heartbreak.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Random;

/**
 * @author tangj
 * @date 2018/9/25 23:17
 * @description:
 */
public class HeartbeatServerHandler extends SimpleChannelInboundHandler<String>{

    // 失败计数器：未收到客户端发送的ping
    private int unRecPingTimes = 0;

    // 定义服务端没有收到心跳消息的最大次数
    private static final int MAX_UN_REC_PING_TIMES = 3;

    private Random random = new Random(System.currentTimeMillis());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg !=null && msg.equals("Heartbeat")){
            System.out.println("客户端 "+ctx.channel().remoteAddress()+" 发送心跳 ");
        }else {
            System.out.println("客户端发送请求："+msg);
            String resp = "商品的价格是："+random.nextInt();
            ctx.writeAndFlush(resp);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            System.out.println("服务端----> 未收到心跳"+ unRecPingTimes + "次");
            // 失败次数 大于三次时，关闭连接，等待客户端重连
            if (unRecPingTimes >= MAX_UN_REC_PING_TIMES){
                System.out.println("服务端 超过三次未收到心跳，关闭channel");
                ctx.close();
            }else {
                // 失败计数器加1
                unRecPingTimes++;
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("一个客户端已连接,IP : "+ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("客户端断开连接,IP : "+ctx.channel().remoteAddress().toString());
    }
}
