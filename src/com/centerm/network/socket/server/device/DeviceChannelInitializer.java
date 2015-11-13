package com.centerm.network.socket.server.device;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.centerm.util.BeanFactory;

@Component("deviceChannelInitializer")
public class DeviceChannelInitializer extends ChannelInitializer<SocketChannel>
{
	private static final Logger logger = LogManager.getLogger(DeviceChannelInitializer.class);
	
	@Resource(name = "beanFactory")
	private BeanFactory beanFactory;
	
	protected void initChannel(SocketChannel sc) throws Exception
	{
		logger.debug("来自" + sc.remoteAddress().getAddress().toString() + "的连接");
		
		ChannelPipeline cp = sc.pipeline();
		cp.addLast("idleStateHandler", new IdleStateHandler(60, 60, 30, TimeUnit.SECONDS));	//读、写超时60秒，混合超时30秒
		cp.addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));	//基于报文长度的解码器
		cp.addLast("channelHandler", (ChannelHandlerAdapter)beanFactory.getBean("deviceChannelHandler"));
	}
}
