package com.centerm.action.system.outlets;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.manage.outlets.OutletsService;

@Controller("outletsUpdateAction")
@Scope("prototype")
@Namespace("/system/outlets/update")
@ParentPackage("json-default")
public class OutletsUpdateAction
{
	private static final Logger logger = LogManager.getLogger(OutletsUpdateAction.class);
	
	private Integer id;	//网点id
	private String code;	//网点号
	private String name;	//网点名
	private boolean isParent;	//是否为父网点
	private Integer parentid; 	//父网点id
	private String description;	//网点描述
	
	private boolean result = false;
	private boolean exist = false;
	private String errorMsg = "";
	
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	
	@Action
    (
		value = "existName",
		results =
    	{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, exist"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"}),
    	}
	)
	public String existName()
	{
		try
		{
			boolean ret = outletsService.isOutletsExist(code, isParent, parentid, name);
			if ( ret == true )
			{
				exist = true;
			}
			else
			{
				exist = false;
			}
		}
		catch(Exception e)
		{
			logger.error("检测网点异常", e);
			result = false;
    		errorMsg = "检测网点异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "updateOutlets",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String updateOutlets()
	{
		try
		{
			boolean ret = outletsService.updateOutlets(id, code, name, description);
			if ( ret == false )
			{
				result = false;
				errorMsg = "更新网点错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("更新网点异常", e);
			result = false;
    		errorMsg = "更新网点异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setIsParent(boolean isParent)
	{
		this.isParent = isParent;
	}
	
	public void setParentid(Integer parentid)
	{
		this.parentid = parentid;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public boolean getExist()
	{
		return exist;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
