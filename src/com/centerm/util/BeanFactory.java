package com.centerm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("beanFactory")
public class BeanFactory implements ApplicationContextAware
{
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}
	
	public Object getBean(String key)
	{
		return applicationContext.getBean(key);
	}
}
