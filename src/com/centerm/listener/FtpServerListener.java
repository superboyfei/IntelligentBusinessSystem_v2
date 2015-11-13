package com.centerm.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.usermanager.UserFactory;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@Component("ftpServerListener")
public class FtpServerListener implements ServletContextListener
{
	private static final Logger logger = LogManager.getLogger(FtpServerListener.class);
	private static final String FTPSERVER_CONTEXT_NAME = "apache.ftpserver";
	
	/**
	 * FTP用户名
	 */
	public static String USER_NAME = "admin";
	
	/**
	 * FTP密码
	 */
	public static String USER_PASSWORD = "Centerm";
	
	/**
	 * FTP端口
	 */
	public static int PORT;
	
	/**
	 * FTP根目录
	 */
	public static String USER_DIR;
	
	public void contextInitialized(ServletContextEvent contextEvent)
	{
		logger.debug("启动FtpServer");
		
		USER_DIR = contextEvent.getServletContext().getRealPath("/") + "ftp";
		File userDir = new File(USER_DIR);
		if ( !userDir.exists() )	//如果目录不存在，则重新创建
		{
			userDir.mkdir();
		}
		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(contextEvent.getServletContext());
		DefaultFtpServer ftpServer = (DefaultFtpServer)ctx.getBean("ibsFtpServer");
		contextEvent.getServletContext().setAttribute(FTPSERVER_CONTEXT_NAME, ftpServer);
		
		try
		{
			PropertiesUserManager userManager = (PropertiesUserManager)ftpServer.getUserManager();
			PORT = ftpServer.getListener("default").getPort();
			
			UserFactory userFactory = new UserFactory();
			userFactory.setName(USER_NAME);
			userFactory.setPassword(USER_PASSWORD);
			userFactory.setHomeDirectory(USER_DIR);
			User user = userFactory.createUser();
			userManager.save(user);
			
			ftpServer.start();
		}
		catch(Exception e)
		{
			logger.error("FtpServer启动异常", e);
		}
	}
	
	public void contextDestroyed(ServletContextEvent contextEvent)
	{
		DefaultFtpServer ftpServer = (DefaultFtpServer)contextEvent.getServletContext().getAttribute(FTPSERVER_CONTEXT_NAME);
		if ( ftpServer != null )
		{
			logger.debug("关闭FtpServer");
			ftpServer.stop();
			contextEvent.getServletContext().removeAttribute(FTPSERVER_CONTEXT_NAME);
		}
		else
		{
			logger.debug("FtpServer已关闭");
		}
	}
}