package com.centerm.action.system.user;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.centerm.entity.Authority;
import com.centerm.entity.Function;
import com.centerm.entity.User;
import com.centerm.service.manage.user.AuthorityService;
import com.centerm.service.manage.user.UserService;
import com.centerm.vo.FunctionVo;

@Controller("userAuthorityAction")
@Scope("prototype")
@Namespace("/system/user/authority")
@ParentPackage("json-default")
public class UserAuthorityAction
{
	private static final Logger logger = LogManager.getLogger(UserAuthorityAction.class);
	
	private String userid;	//用户id
	private String functionids;	//功能id
	
	private boolean result = false;
	private List<FunctionVo> functionVoList = new ArrayList<FunctionVo>();
	private String errorMsg = "";
	
	@Resource(name = "authorityService")
	private AuthorityService authorityService;
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "showAuthListByUserid",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, functionVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String showAuthListByUserid()
	{
		try
		{
			List<?> functionList = authorityService.getAllFunction();
			if ( functionList == null )
			{
				result = false;
				errorMsg = "获取功能列表错误";
				return "error";
			}
			
			User user = userService.getUserById(Integer.parseInt(userid));
			if ( user == null )
			{
				result = false;
				errorMsg = "获取用户信息错误";
				return "error";
			}
			
			List<?> authorityList = authorityService.getAuthorityListByUserid(Integer.parseInt(userid));
			if ( authorityList == null )
			{
				result = false;
				errorMsg = "获取权限索引列表错误";
				return "error";
			}
			
			for ( int i = 0; i < functionList.size(); i++ )
			{
				Function function = (Function)functionList.get(i);
				FunctionVo functionVo = new FunctionVo();
				functionVo.setId(String.valueOf(function.getId()));
				logger.debug("id：" + functionVo.getId());
				functionVo.setParentid(String.valueOf(function.getParentid()));
				logger.debug("pid：" + functionVo.getParentid());
				functionVo.setName(function.getName());
				logger.debug("name：" + functionVo.getName());
				if ( function.getIsparent() == 1 )
				{
					functionVo.setIsparent(true);
				}
				else
				{
					functionVo.setIsparent(false);
				}
				logger.debug("isparent：" + functionVo.getIsparent());
				functionVo.setUrl(function.getUrl());
				logger.debug("url：" + functionVo.getUrl());
				functionVo.setIcon(function.getIcon());
				logger.debug("icon：" + functionVo.getIcon());
				functionVo.setTarget(function.getTarget());
				logger.debug("target：" + functionVo.getTarget());
				if ( user.getIsadmin() == 1 )	//用户为管理员，不可进行勾选框编辑
				{
					functionVo.setChkDisabled(true);
				}
				else
				{
					functionVo.setChkDisabled(false);
				}
				logger.debug("chkdisable：" + functionVo.getChkDisabled());
				
				functionVo.setChecked(false);	//预置为不勾选
				for ( int j = 0; j < authorityList.size(); j++ )
				{
					Authority authority = (Authority)authorityList.get(j);
					if ( authority.getFunctionid().equals(function.getId()) )	//该用户有权限访问当前功能
					{
						functionVo.setChecked(true);
						break;
					}
				}
				logger.debug("checked：" + functionVo.getChecked());
				functionVoList.add(functionVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取功能列表异常", e);
			result = false;
			errorMsg = "获取功能列表异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "saveOrUpdateAuthority",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveOrUpdateAuthority()
	{
		try
		{
			boolean ret = authorityService.updateUserAuthority(Integer.parseInt(userid), functionids);
			if ( ret == false )
			{
				result = false;
				errorMsg = "保存用户权限错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("保存用户权限异常", e);
			result = false;
			errorMsg = "保存用户权限异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	
	public void setFunctionids(String functionids)
	{
		this.functionids = functionids;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public List<FunctionVo> getFunctionVoList()
	{
		return functionVoList;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}	
}
