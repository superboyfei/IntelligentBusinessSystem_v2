package com.centerm.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("sessionListener")
public class SessionListener implements HttpSessionListener
{
	private static final Logger logger = LogManager.getLogger(SessionListener.class);
	
	private static Map<String, Long> sessionMap = new HashMap<String, Long>();
	
	/*
	 * session创建事件
	 */
	public void sessionCreated(HttpSessionEvent hse)
	{
		HttpSession session = hse.getSession();
		sessionMap.put(session.getId(), session.getCreationTime());
		
		logger.debug("会话创建，id：" + session.getId());
	}
	
	/*
	 * session销毁事件
	 */
	public void sessionDestroyed(HttpSessionEvent hse)
	{
		HttpSession session = hse.getSession();
		sessionMap.remove(session.getId());
		
		logger.debug("会话销毁，id：" + session.getId());
	}
}
