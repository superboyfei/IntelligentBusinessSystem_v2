package com.centerm.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.centerm.task.InitializeTask;

@Component("initializeListener")
public class InitializeListener implements ApplicationListener<ContextRefreshedEvent>
{
	private static final Logger logger = LogManager.getLogger(InitializeListener.class);
	
	@Resource(name = "initializeTask")
	private InitializeTask initializeTask;
	
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if ( event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext") )	//防止被多个容器触发导致执行多次
		{
			logger.debug("开始执行初始化任务");
			
			for ( Method method : initializeTask.getClass().getDeclaredMethods() )
			{
				try
				{
					method.invoke(initializeTask);
				}
				catch(IllegalAccessException e)
				{
					logger.error("初始化异常", e);
				}
				catch(IllegalArgumentException e)
				{
					logger.error("初始化异常", e);
				}
				catch(InvocationTargetException e)
				{
					logger.error("初始化异常", e);
				}
			}
		}
	}
}
