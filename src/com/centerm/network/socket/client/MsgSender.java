package com.centerm.network.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.centerm.util.BeanFactory;

@Component("msgSender")
public class MsgSender
{
	private static final Logger logger = LogManager.getLogger(MsgSender.class);
	
	@Resource(name = "beanFactory")
	private BeanFactory beanFacory;
	
	/**
	 * 发送给客户端信息
	 * @param ip	发送ip
	 * @param port	发送端口
	 * @param msg	发送数据
	 */
	public void startMsgSend(String ip, int port, byte[] msg)
	{
		logger.debug("向" + ip + ":" + port + "发送数据");
		
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.group(group);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		
		final ChannelHandler channelHandler = (ChannelHandler)beanFacory.getBean("channelHandler");
		channelHandler.setMsg(msg);
		
		try
		{
			bootstrap.handler
			(
				new ChannelInitializer<SocketChannel>()
				{
					protected void initChannel(SocketChannel sc) throws Exception
					{
						sc.pipeline().addLast("writeTimeoutHandler", new WriteTimeoutHandler(30));	//写超时30秒
						sc.pipeline().addLast("channelHandler", channelHandler);
					}
				}
			);
		
			ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
			channelFuture.addListener
			(
				new ChannelFutureListener()
				{
					public void operationComplete(ChannelFuture arg0) throws Exception
					{
						arg0.channel().closeFuture().sync();
						arg0.channel().eventLoop().shutdownGracefully();
					}
				}
			);
		}
		catch(Exception e)
		{
			logger.error("连接异常", e);
		}
	}
}
