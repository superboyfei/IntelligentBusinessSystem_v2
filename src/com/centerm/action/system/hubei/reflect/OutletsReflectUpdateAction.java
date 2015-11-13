package com.centerm.action.system.hubei.reflect;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.business.hubei.OutletsReflectService;
import com.centerm.vo.hubei.OutletsReflectVo;

@Controller("outletsReflectUpdateAction")
@Scope("prototype")
@Namespace("/system/hubei/reflect/update")
@ParentPackage("json-default")
public class OutletsReflectUpdateAction 
{
	private static final Logger logger = LogManager.getLogger(OutletsReflectUpdateAction.class);
	
	private String id;
	private String city;
	private String outlets;
	private String number;
	private String startIP;
	private String endIP;
	private String submask;
	
	private OutletsReflectVo reflectVo = new OutletsReflectVo();
	private boolean result = false;
	private boolean exist = false;
	private String errorMsg = "";
			
	@Resource(name = "outletsReflectService")
	private OutletsReflectService outletsReflectService;
	
	@Action
	(
		value = "updateReflect",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, reflectVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String updateReflect() 
	{
		
		try
		{
			boolean ret = outletsReflectService.updateReflectById(id, city, outlets, number, startIP, endIP, submask);
			if ( ret == false ) 
			{
				result = false;
				errorMsg = "更新网点IP映射错误";
				return "error";
			}
			
			reflectVo.setId(String.valueOf(id));
			logger.debug("id：" + reflectVo.getId());
			reflectVo.setCity(city);
			logger.debug("city：" + reflectVo.getCity());
			reflectVo.setOutlets(outlets);
			logger.debug("outlets：" + reflectVo.getOutlets());
			reflectVo.setNumber(number);
			logger.debug("number：" + reflectVo.getNumber());
			reflectVo.setStartIp(startIP);
			logger.debug("startIP：" + reflectVo.getStartIp());
			reflectVo.setEndIp(endIP);
			logger.debug("endIP：" + reflectVo.getEndIp());
			reflectVo.setSubmask(submask);
			logger.debug("submask：" + reflectVo.getSubmask());
			
		} 
		catch(Exception e) 
		{
			
			logger.error("更新网点IP映射异常", e);
			result = false;
			errorMsg = "更新网点IP映射异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	public boolean isResult() 
	{
		return result;
	}

	public boolean isExist() 
	{
		return exist;
	}

	public String getErrorMsg() 
	{
		return errorMsg;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public void setCity(String city) 
	{
		this.city = city;
	}

	public void setOutlets(String outlets) 
	{
		this.outlets = outlets;
	}

	public void setNumber(String number) 
	{
		this.number = number;
	}

	public void setStartIP(String startIP)
	{
		this.startIP = startIP;
	}

	public void setEndIP(String endIP) 
	{
		this.endIP = endIP;
	}

	public void setSubmask(String submask) 
	{
		this.submask = submask;
	}
	
	public OutletsReflectVo getReflectVo() 
	{
		return reflectVo;
	}
	
}
