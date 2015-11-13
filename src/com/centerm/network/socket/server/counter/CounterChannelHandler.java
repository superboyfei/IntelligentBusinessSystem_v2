package com.centerm.network.socket.server.counter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.centerm.msghandler.IMessageAnalyzer;

@Component("counterChannelHandler")
@Scope("prototype")
public class CounterChannelHandler extends ChannelDuplexHandler
{
	private static final Logger logger = LogManager.getLogger(CounterChannelHandler.class);
	
	private boolean readOver = false;	//是否读取完毕
	private byte[] msgBody;	//报文体
	
	@Resource(name = "counterAnalyzer")
	private IMessageAnalyzer messageAnalyzer;
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		ByteBuf buf = (ByteBuf)msg;
		msgBody = new byte[buf.readableBytes()];
		buf.readBytes(msgBody);
		readOver = true;
		
		super.channelRead(ctx, msg);
	}
	
	public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception
	{
		if ( readOver )	//读取完毕则进行解析
		{
			InetSocketAddress isa = (InetSocketAddress)ctx.channel().remoteAddress();
			messageAnalyzer.addClientIp(isa.getAddress().toString());
			byte[] retMsg = messageAnalyzer.analyzeMessage(msgBody);
			
			ByteBuf retBuf = Unpooled.copiedBuffer(retMsg);
			final ChannelFuture f = ctx.writeAndFlush(retBuf); 
			// 监听操作是否完成
	        f.addListener(new ChannelFutureListener() {
	            @Override
	            public void operationComplete(ChannelFuture future) {
	                assert f == future;
	                ctx.close();
	            }
	        });
		}
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
	{
		InetSocketAddress isa = (InetSocketAddress)ctx.channel().remoteAddress();
		
		if ( evt instanceof IdleStateEvent )
		{
			 IdleStateEvent ise = (IdleStateEvent)evt;
			 
             if ( ise.state() == IdleState.READER_IDLE )
             {
            	 logger.debug("读取超时：" + isa.getAddress().toString());
             }
             else if ( ise.state() == IdleState.WRITER_IDLE )
             {
            	 logger.debug("写入超时：" + isa.getAddress().toString());
             }
             else if ( ise.state() == IdleState.ALL_IDLE )
             {
            	 logger.debug("混合超时：" + isa.getAddress().toString());
             }
		}
		
		ctx.close();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		InetSocketAddress isa = (InetSocketAddress)ctx.channel().remoteAddress();
		logger.debug("处理异常：" + isa.getAddress().toString());
		ctx.close();
	}
}
