package com.centerm.action.homepage;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Controller("homepageUserAction")
@Scope("prototype")
@Namespace("/homepage/user")
@ParentPackage("json-default")
public class HomepageUserAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(HomepageUserAction.class);
	
	private String uid;	//用户账号
	private String name;	//用户名
	private String password;	//用户密码
	private Map<String, Object> session;
	private boolean result = false;
	private String errorMsg = "";
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "validatePwd",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validatePwd()
	{
		try
		{
			boolean ret = userService.validatePassword(uid, password);
			if( ret == false )
			{
				result = false;
				errorMsg = "用户原密码输入错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("检查原密码异常", e);
			result = false;
			errorMsg = "检查原密码异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "updateCurrentUser",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String updateCurrentUser()
	{
		try
		{
			boolean ret = userService.updateUserByUid(uid, name, password);
			if ( ret == false )
			{
				result = false;
				errorMsg = "更新当前用户信息错误";
				return "error";
			}
			
			User user = (User)session.get("user");
			if( user != null )	//更新session信息
			{
				user.setName(name);
				if ( StringUtils.isNotBlank(password) )
				{
					String keyPassword = UserPasswordCrypter.crypt(password);
					user.setPassword(keyPassword);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("更新当前用户信息异常", e);
			result = false;
			errorMsg = "更新当前用户信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	public void setName(String name)
	{
		this.name = name;
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
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
