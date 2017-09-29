package com.xm.learn.nio3;
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
    	String body = (String)msg;
    	System.out.println("this is "+ ++this.counter +" times receive client ：["+body+"]");
    	body += "$_";
    	ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
    	ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}