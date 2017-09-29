package com.xm.learn.nio1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { 
    	ByteBuf buf = (ByteBuf) msg;
    	byte[] req = new byte[buf.readableBytes()];
    	buf.readBytes(req);
    	String body = new String(req,"utf-8");
    	System.out.println("server recevie is "+body);
    	ByteBuf resp = Unpooled.copiedBuffer(("server time is "+System.currentTimeMillis()).getBytes());
    	ctx.write(resp);
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}