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
import com.centerm.vo.OutletsVo;

@Controller("outletsAddAction")
@Scope("prototype")
@Namespace("/system/outlets/add")
@ParentPackage("json-default")
public class OutletsAddAction
{
	private static final Logger logger = LogManager.getLogger(OutletsAddAction.class);
	
	private String code;	//网点编号
	private String name;	//网点名
	private boolean isParent;	//是否为父网点
	private Integer parentid; 	//父网点id
	private String description;	//网点描述
	
	private boolean result = false;
	private boolean exist = false;
	private OutletsVo outletsVo = new OutletsVo();
	private String errorMsg = "";
	
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	
	@Action
    (
		value = "existOutletsGroup",
		results =
    	{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, exist"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"}),
    	}
	)
	public String existOutletGroup()
	{
		try
		{
			boolean ret = outletsService.isOutletsExist("-1", isParent, parentid, name);	//网点组编号默认为-1
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
			logger.error("检测网点组异常", e);
			result = false;
    		errorMsg = "检测网点组异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "addOutletsGroup",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, outletsVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String addOutletsGroup()
	{
		try
		{
			Integer id = outletsService.addOutlets(name, "-1", isParent, parentid, description);	//网点组编号默认为-1
			if ( id == null )
			{
				result = false;
    			errorMsg = "新增网点组错误";
    			return "error";
			}
			
			outletsVo.setId(String.valueOf(id));
			logger.debug("id：" + outletsVo.getId());
			outletsVo.setName(name);
			logger.debug("name：" + outletsVo.getName());
			outletsVo.setCode(code);
			logger.debug("code：" + outletsVo.getCode());
			outletsVo.setIsParent(isParent);
			logger.debug("isparent：" + outletsVo.getIsParent());
			outletsVo.setParentid(String.valueOf(parentid));
			logger.debug("parentid：" + outletsVo.getParentid());
			outletsVo.setDescription(description);
			logger.debug("description：" + outletsVo.getDescription());
		}
		catch(Exception e)
		{
			logger.error("新增网点组异常", e);
			result = false;
    		errorMsg = "新增网点组异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
    (
		value = "existOutlets",
		results =
    	{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, exist"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"}),
    	}
	)
	public String existOutlets()
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
		value = "addOutlets",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, outletsVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String addOutlets()
	{
		try
		{
			Integer id = outletsService.addOutlets(name, code, isParent, parentid, description);
			if ( id == null )
			{
				result = false;
    			errorMsg = "新增网点错误";
    			return "error";
			}
			
			outletsVo.setId(String.valueOf(id));
			logger.debug("id：" + outletsVo.getId());
			outletsVo.setName(name);
			logger.debug("name：" + outletsVo.getName());
			outletsVo.setCode(code);
			logger.debug("code：" + outletsVo.getCode());
			outletsVo.setIsParent(isParent);
			logger.debug("isparent：" + outletsVo.getIsParent());
			outletsVo.setParentid(String.valueOf(parentid));
			logger.debug("parentid：" + outletsVo.getParentid());
			outletsVo.setDescription(description);
			logger.debug("description：" + outletsVo.getDescription());
		}
		catch(Exception e)
		{
			logger.error("新增网点异常", e);
			result = false;
    		errorMsg = "新增网点异常";
	    	return "error";
		}
		
		result = true;
		return "success";
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
	
	public OutletsVo getOutletsVo()
	{
		return outletsVo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
