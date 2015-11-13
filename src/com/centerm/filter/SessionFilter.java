package com.centerm.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Session过滤器
 */
public class SessionFilter implements Filter
{
	public void destroy()
	{
		
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest servletRequest = (HttpServletRequest)req;
		HttpServletResponse servletResponse = (HttpServletResponse)resp;
		HttpSession session = servletRequest.getSession(false);
		String path = servletRequest.getRequestURI();
		
		//访问首页不需要过滤
		if (path.endsWith("/") || path.endsWith("/welcome.action") || path.endsWith("/login.action"))	
		{
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if (session == null)	//session不存在
		{
			 String ajaxSubmit = servletRequest.getHeader("X-Requested-With");
			 if(ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")){   
				 PrintWriter printWriter = servletResponse.getWriter();   
	             printWriter.print("timeout");   
	             printWriter.flush();   
	             printWriter.close();
			 }else{  
				 //跳转回首页
				 servletResponse.sendRedirect("welcome.action");
			 }
				
		}
		else
		{
			//普通提交
			chain.doFilter(servletRequest, servletResponse);
		}
	}
	
	public void init(FilterConfig arg0) throws ServletException
	{
		
	}
}
