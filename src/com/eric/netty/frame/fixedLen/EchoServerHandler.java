package com.eric.netty.frame.fixedLen;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		System.out.println("Receive client : ["+msg+"]");
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		cause.printStackTrace();
		ctx.close();
	}
}
