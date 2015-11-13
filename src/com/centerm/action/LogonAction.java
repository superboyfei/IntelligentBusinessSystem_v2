package com.centerm.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.User;
import com.centerm.service.manage.user.UserService;
import com.centerm.util.UserPasswordCrypter;
import com.opensymphony.xwork2.ActionSupport;

@Controller("logonAction")
@Scope("prototype")
@Namespace("")
@ParentPackage("json-default")
public class LogonAction extends ActionSupport implements SessionAware
{
	private static final long serialVersionUID = 6473585621724667329L;
	
	private static final Logger logger = LogManager.getLogger(LogonAction.class);
	
	private String uid;	//用户账号
	private String password;	//用户密码
	private Map<String, Object> session;
	
	private boolean result = false;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "login",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/success.jsp"),
			@Result(name = "error", location = "/WEB-INF/jsp/login.jsp")
		}
	)
	public String login()
	{
		User user = (User)session.get("user");	//获得用户session
		
		if ( user == null )	//session不存在user，登录处理
		{
			logger.debug("用户" + uid + "登录");
			
			try
			{
				String keyPassword = UserPasswordCrypter.crypt(password);	//加密用户密码
				boolean ret = userService.validateUser(uid, keyPassword);
				if ( ret == false )
				{
					addFieldError("error", "用户账号或密码有误");
					return "error";
				}
				
				user = userService.getUserByUid(uid);
				if ( user == null )
				{
					addFieldError("error", "记录用户信息失败");
					return "error";
				}
			}
			catch(Exception e)
			{
				logger.error("验证用户异常", e);
				addFieldError("error", "验证用户异常");
				return "error";
			}
		}
		else	//session存在
		{
			if ( user.getUid().equals(uid) )	//相同用户再次登录或刷新
			{
				logger.debug("用户" + user.getUid() + "刷新");
				
				try
				{
					boolean ret = userService.validateUser(user.getUid(), user.getPassword());
					if ( ret == false )
					{
						addFieldError("error", "用户账号或密码已经被修改，请重新登录");
						ServletActionContext.getRequest().getSession().invalidate();	//销毁session
						return "error";
					}
				}
				catch(Exception e)
				{
					session.remove("user");
					logger.error("验证用户异常", e);
					addFieldError("error", "验证用户异常");
					return "error";
				}
			}
			else	//切换用户
			{
				logger.debug("用户" + uid + "登录");
				
				try
				{
					String keyPassword = UserPasswordCrypter.crypt(password);	//加密用户密码
					boolean ret = userService.validateUser(uid, keyPassword);
					if ( ret == false )
					{
						addFieldError("error", "用户账号或密码有误");
						return "error";
					}
					
					user = userService.getUserByUid(uid);
					if ( user == null )
					{
						addFieldError("error", "记录用户信息失败");
						return "error";
					}
				}
				catch(Exception e)
				{
					session.remove("user");
					logger.error("验证用户异常", e);
					addFieldError("error", "验证用户异常");
					return "error";
				}
			}
		}
		
		session.put("user", user);	//保存sesion
		return "success";
	}
	
	@Action
	(
		value = "logout",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/login.jsp")
		}
	)
	public String logout()
	{
		User user = (User)session.get("user");	//获得用户session
		if ( user != null )
		{
			logger.debug("用户" + user.getUid() + "退出登录");
		}
		else
		{
			logger.debug("用户退出登录");
		}
		//由于直接使用invalidate的方法会强制退出，导致退到首页登录可能存在异常，所以采用软注销的方式
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		System.out.println("===>FLy logout: " + session.getId());
//		Enumeration<String> attributeKeys = session.getAttributeNames();
//		while(attributeKeys.hasMoreElements()) {
//			String string = attributeKeys.nextElement();
//			System.out.println("===>Fly logout: attributes : " + string);
//			session.removeAttribute(string);
//		}
		ServletActionContext.getRequest().getSession().invalidate();	//销毁session
		return "success";
	}
	
	@Action
	(
		value = "checkState",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result"})
		}
	)
	public String checkState()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			addFieldError("error", "用户账号失效，请重新登录");
			ServletActionContext.getRequest().getSession().invalidate();	//销毁session
			result = false;
			return "error";
		}
		
		boolean ret = userService.validateUser(user.getUid(), user.getPassword());
		if ( ret == false )
		{
			addFieldError("error", "用户账号或密码已经被修改，请重新登录");
			ServletActionContext.getRequest().getSession().invalidate();	//销毁session
			result = false;
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "success",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/main.jsp"),
			@Result(name = "error", location = "/WEB-INF/jsp/login.jsp")
		}
	)
	public String success()
	{
		User user = (User)session.get("user");
		if(user == null)
		{
			return "error";
		} 
		else
		{
			if ( user.getUid().equals(uid) )	//相同用户刷新
			{
				logger.debug("用户" + user.getUid() + "刷新");
				try
				{
					boolean ret = userService.validateUser(user.getUid(), user.getPassword());
					if ( ret == false )
					{
						addFieldError("error", "用户账号或密码已经被修改，请重新登录");
						ServletActionContext.getRequest().getSession().invalidate();	//销毁session
						return "error";
					}
				}
				catch(Exception e)
				{
					logger.error("验证用户异常", e);
					addFieldError("error", "验证用户异常");
					return "error";
				}
			}
		}
		return "success";
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}
}
