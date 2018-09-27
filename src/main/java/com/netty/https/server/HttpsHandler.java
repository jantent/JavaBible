package com.netty.https.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.UUID;


/**
 * @author: tangJ
 * @Date: 2018/9/27 12:57
 * @description:
 */
public class HttpsHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        try {
            String uri = request.uri();
            System.out.println("请求中的uri为：  "+uri);
            byte[] reqContent = getContentBytes(request);
            System.out.println(" content 为： "+ new String(reqContent,"utf-8"));

            String resp = "订单号为： "+UUID.randomUUID().toString();
            ByteBuf bufRep = Unpooled.wrappedBuffer(resp.getBytes("utf-8"));
            writeResponse(request,HttpResponseStatus.OK,ctx,bufRep);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回消息
     *
     * @param request
     * @param respStatus
     * @param ctx
     * @param bufRep
     */
    private void writeResponse(FullHttpRequest request, HttpResponseStatus respStatus, ChannelHandlerContext ctx,
                               ByteBuf bufRep) {
        if (request == null || bufRep == null) {
            // 该消息不需要响应
            ctx.close();
        } else {
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, respStatus, bufRep);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                response.headers().set(HttpHeaderNames.CONNECTION,"keepalive");
                ctx.writeAndFlush(response);
            } else {
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    /**
     * 从请求中获取 content bytes
     *
     * @param request http请求
     * @return 请求中的content bytes，如果请求中content为空，则返回null
     */
    private byte[] getContentBytes(FullHttpRequest request) {
        // 请求的content部分的bytes
        byte[] reqBytes = null;
        // 请求的content部分 ByteBuf
        ByteBuf bufReq = request.content();
        if (bufReq.readableBytes() > 0) {
            // 如果有内容
            int length = bufReq.readableBytes();
            reqBytes = new byte[length];
            if ((length != 0) && (bufReq.isReadable()))
                bufReq.getBytes(0, reqBytes, 0, length);
        }

        return reqBytes;
    }
}
