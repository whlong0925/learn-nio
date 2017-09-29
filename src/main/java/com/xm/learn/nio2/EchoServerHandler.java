package com.xm.learn.nio2;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private int counter;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { 
//    	ByteBuf buf = (ByteBuf) msg;
//    	byte[] req = new byte[buf.readableBytes()];
//    	buf.readBytes(req);
//    	String body = new String(req,"utf-8");
    	String body = (String)msg;
    	System.out.println("The time server receive order : " +body +" ;the counter is :"+ ++counter);
    	String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
    	currentTime = currentTime + System.getProperty("line.separator");
    	ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
    	ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}