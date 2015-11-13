package com.centerm.network.socket.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("channelHandler")
@Scope("prototype")
public class ChannelHandler extends ChannelDuplexHandler
{
	private static final Logger logger = LogManager.getLogger(ChannelHandler.class);
	
	private ByteBuf message;
	private byte[] msg;
	
	public void setMsg(byte[] msg)
	{
		this.msg = msg;
	}
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		message = Unpooled.buffer(msg.length);
		message.writeBytes(msg);
		ctx.writeAndFlush(message);
		ctx.close();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		InetSocketAddress isa = (InetSocketAddress)ctx.channel().remoteAddress();
		logger.debug("处理异常：" + isa.getAddress().toString());
		ctx.close();
	}
}
