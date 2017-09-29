package com.xm.learn.nio2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	private byte[] req;

	/**
	 * Creates a client-side handler.
	 */
	public EchoClientHandler() {
		this.req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ByteBuf message = null;
		for(int i=0;i<100;i++){
			message = Unpooled.buffer(this.req.length);
			message.writeBytes(this.req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req,"utf-8");
		String body = (String)msg;
		System.out.println("Now is "+body+" ; the counter is :"+ ++this.counter);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}