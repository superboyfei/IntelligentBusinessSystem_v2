package com.centerm.action.system.user;

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
import com.centerm.vo.UserVo;

@Controller("userUpdateAction")
@Scope("prototype")
@Namespace("/system/user/update")
@ParentPackage("json-default")
public class UserUpdateAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(UserUpdateAction.class);
	
	private String id;	//用户id
	private String uid;	//用户账号
	private String name;	//用户昵称
	private String newPass;	//新密码
	private Map<String, Object> session;
	
	private boolean result = false;
	private UserVo userVo = new UserVo();
	private String errorMsg = "";
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "updateUser",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, userVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String updateUser()
	{
		try
		{
			boolean ret = userService.updateUserByUid(uid, name, newPass);
			if ( ret == false )
			{
				result = false;
				errorMsg = "更新用户错误";
				return "error";
			}
			
			User user = (User)session.get("user");
			if ( user != null )
			{
				if ( user.getUid().equals(uid) )	//如果修改的是当前用户，则需要更新session信息
				{
					user.setName(name);
					if ( StringUtils.isNotBlank(newPass) )
					{
						String keyPassword = UserPasswordCrypter.crypt(newPass);
						user.setPassword(keyPassword);
					}
				}
			}
			
			userVo.setId(id);
			logger.debug("id：" + userVo.getId());
			userVo.setUid(uid);
			logger.debug("uid：" + userVo.getUid());
			userVo.setName(name);
			logger.debug("name：" + userVo.getName());
		}
		catch(Exception e)
		{
			logger.error("更新用户异常", e);
			result = false;
			errorMsg = "更新用户异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setNewPass(String newPass)
	{
		this.newPass = newPass;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public UserVo getUserVo()
	{
		return userVo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
