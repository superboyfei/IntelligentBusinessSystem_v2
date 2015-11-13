package com.centerm.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("welcomeAction")
@Scope("prototype")
@Namespace("")
@ParentPackage("struts-default")
public class WelcomeAction implements ServletRequestAware 
{
	private static final Logger logger = LogManager.getLogger(WelcomeAction.class);
	
	private HttpServletRequest request;
	
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	@Action
	(
		value = "welcome",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/login.jsp")
		}
	)
	public String welcome()
	{
		logger.debug("来自" + request.getRemoteAddr() + "的访问");
		return "success";
	}

}
