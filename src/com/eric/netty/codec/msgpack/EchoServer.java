package com.eric.netty.codec.msgpack;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

	public void bind(int port)throws Exception{
		//创建服务端的NIO线程组
		EventLoopGroup  bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap b=new  ServerBootstrap();
			b.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
			.childHandler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					  //  ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
	                    ch.pipeline().addLast("msgpack decoder",new MsgPackDecoder());
	                    //在ByteBuf之前增加2个字节的消息长度字段
	                 //  ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2)); 
	                    ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
	                    ch.pipeline().addLast(new EchoServerHander());

				}
			});
			//绑定端口，同步等待成功
			ChannelFuture f=b.bind(port).sync();
			
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int port=8080;
		if(args!=null&&args.length>0){
			try {
				port=Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
		}
      new EchoServer().bind(port);
	}
}
