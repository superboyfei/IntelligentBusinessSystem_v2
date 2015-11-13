package com.centerm.action.system.user;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.manage.user.UserService;
import com.centerm.vo.UserVo;

@Controller("userAddAction")
@Scope("prototype")
@Namespace("/system/user/add")
@ParentPackage("json-default")
public class UserAddAction
{
	private static final Logger logger = LogManager.getLogger(UserAddAction.class);
	
	private String uid;	//用户账号
	private String name;	//用户昵称
	private String password;	//用户密码
	
	private boolean result = false;
	private UserVo userVo = new UserVo();
	private String message = "";
	private String errorMsg = "";
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "saveUser",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, userVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveUser()
	{
		try
		{
			Integer id = userService.addUser(uid, name, password, 0);
			if ( id == null )
			{
				result = false;
				errorMsg = "新增用户失败";
				return "error";
			}
			
			userVo.setId(String.valueOf(id));
			logger.debug("id：" + userVo.getId());
			userVo.setUid(uid);
			logger.debug("uid：" + userVo.getUid());
			userVo.setName(name);
			logger.debug("name：" + userVo.getName());
			userVo.setIsadmin(false);
		}
		catch(Exception e)
		{
			logger.error("新增用户异常", e);
			result = false;
			errorMsg = "新增用户异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "validateUid",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, message"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validateUid()
	{
		try
		{
			boolean ret = userService.isUidUsed(uid);
			if( ret == true )
			{
				message = "该用户账号已存在，请重新输入";
				result = false;
			}
			else
			{
				message = "该用户账号可以使用";
				result = true;
			}
		}
		catch(Exception e)
		{
			logger.error("检查用户账号异常", e);
			result = false;
			errorMsg = "检查用户账号异常";
			return "error";
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
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public UserVo getUserVo()
	{
		return userVo;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
