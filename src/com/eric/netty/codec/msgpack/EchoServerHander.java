package com.eric.netty.codec.msgpack;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class EchoServerHander extends ChannelHandlerAdapter {

	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
		 System.out.println("Server receive the msgpack message : " + msg);
		 ctx.write(msg);
	}	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
		ctx.flush();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
     cause.printStackTrace();
     ctx.close();
	}
}
