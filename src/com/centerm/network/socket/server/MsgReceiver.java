package com.centerm.network.socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.centerm.network.socket.server.counter.CounterChannelInitializer;
import com.centerm.network.socket.server.device.DeviceChannelInitializer;

@Component("msgReceiver")
public class MsgReceiver
{
	private static final Logger logger = LogManager.getLogger(MsgReceiver.class);
	
	//private static final String DEVICE_IP = "127.0.0.1";
	private static final int DEVICE_PORT = 3131;
	//private static final String COUNTER_IP = "127.0.0.1";
	private static final int COUNTER_PORT = 5151;
	
	private static ChannelFuture deviceFuture;
	private static ChannelFuture counterFuture;
	private static ServerBootstrap deviceBootstrap;
	private static ServerBootstrap counterBootstrap;
	private static EventLoopGroup deviceBossGroup = new NioEventLoopGroup();
	private static EventLoopGroup deviceWorkerGroup = new NioEventLoopGroup();
	private static EventLoopGroup counterBossGroup = new NioEventLoopGroup();
	private static EventLoopGroup counterWorkerGroup = new NioEventLoopGroup();
	
	@Resource(name = "deviceChannelInitializer")
	private DeviceChannelInitializer deviceChannelInitializer;
	@Resource(name = "counterChannelInitializer")
	private CounterChannelInitializer counterChannelInitializer;
	
	static
	{
		deviceBootstrap = new ServerBootstrap();
		deviceBootstrap.channel(NioServerSocketChannel.class);
		deviceBootstrap.group(deviceBossGroup, deviceWorkerGroup);
		deviceBootstrap.option(ChannelOption.SO_BACKLOG, 1024);	//设置队列长度
		deviceBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);	//心跳检测保持长连接
		
		counterBootstrap = new ServerBootstrap();
		counterBootstrap.channel(NioServerSocketChannel.class);
		counterBootstrap.group(counterBossGroup, counterWorkerGroup);
		counterBootstrap.option(ChannelOption.SO_BACKLOG, 1024);	//设置队列长度
		counterBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);	//心跳检测保持长连接
	}
	
	/**
	 * 开始监听设备数据
	 */
	public void startDeviceReceive()
	{
		logger.debug("启动设备数据监听，监听端口：" + DEVICE_PORT);
		
		try
		{
			deviceBootstrap.childHandler(deviceChannelInitializer);
			deviceFuture = deviceBootstrap.bind(DEVICE_PORT).sync();
			deviceFuture.addListener
			(
				new ChannelFutureListener()
				{
					public void operationComplete(ChannelFuture arg0) throws Exception
					{
						if ( !arg0.isDone() || !arg0.isSuccess() || arg0.isCancelled() )
						{
							logger.debug("设备数据监听未成功执行");
							shutDownDeviceServer();
						}
					}
				}
			);
		}
		catch(Exception e)
		{
			logger.error("启动设备数据监听异常", e);
			shutDownDeviceServer();
		}
	}

	/**
	 * 开始监听柜台数据
	 */
	public void startCounterReceive()
	{
		logger.debug("启动柜台数据监听，监听端口：" + COUNTER_PORT);
		
		try
		{
			counterBootstrap.childHandler(counterChannelInitializer);
			counterFuture = counterBootstrap.bind(COUNTER_PORT).sync();
			
			counterFuture.addListener
			(
				new ChannelFutureListener()
				{
					public void operationComplete(ChannelFuture arg0) throws Exception
					{
						if ( !arg0.isDone() || !arg0.isSuccess() || arg0.isCancelled() )
						{
							logger.debug("柜台数据监听未成功执行");
							shutDownCounterServer();
						}
					}
				}
			);
		}
		catch(Exception e)
		{
			logger.error("启动柜台数据监听异常", e);
			shutDownCounterServer();
		}
	}

	private void shutDownDeviceServer()
	{
		logger.debug("停止设备数据监听");
		
		if ( deviceFuture != null )
		{
			try
			{
				deviceFuture.channel().closeFuture().sync();
				deviceFuture = null;
			}
			catch(Exception e)
			{
				logger.error("停止设备数据监听异常", e);
			}
		}
		
		if ( deviceFuture == null )
		{
			deviceBossGroup.shutdownGracefully();
			deviceWorkerGroup.shutdownGracefully();
		}
	}
	
	private void shutDownCounterServer()
	{
		logger.debug("停止柜台数据监听");
		
		if ( counterFuture != null )
		{
			try
			{
				counterFuture.channel().closeFuture().sync();
				counterFuture = null;
			}
			catch(Exception e)
			{
				logger.error("停止柜台数据监听异常", e);
			}
		}
		
		if ( counterFuture == null )
		{
			counterBossGroup.shutdownGracefully();
			counterWorkerGroup.shutdownGracefully();
		}
	}
}
