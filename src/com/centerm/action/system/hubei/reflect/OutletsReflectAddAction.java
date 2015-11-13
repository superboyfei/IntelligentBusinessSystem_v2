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

@Controller("outletsReflectAddAction")
@Scope("prototype")
@Namespace("/system/hubei/reflect/add")
@ParentPackage("json-default")
public class OutletsReflectAddAction {
	
	private static final Logger logger = LogManager.getLogger(OutletsReflectAddAction.class);
	
	private String city;
	private String outlets;
	private String number;
	private String startIP;
	private String endIP;
	private String submask;
	
	private boolean result = false;
	private String message = "";
	private String errorMsg = "";
	private OutletsReflectVo reflectVo = new OutletsReflectVo();
	
	@Resource(name = "outletsReflectService")
	private OutletsReflectService outletsReflectService;
	
	
	@Action
	(
		value = "saveReflect",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, reflectVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveReflect(){
		try
		{
			Integer id = outletsReflectService.addReflect(city, outlets, number, startIP, endIP, submask);
			if ( id == null )
			{
				result = false;
				errorMsg = "新增网点IP映射失败";
				return "error";
			}
			
			reflectVo.setId("" + id);
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
			logger.error("新增网点IP映射异常", e);
			result = false;
			errorMsg = "新增网点IP映射异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "validateOutlets",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, message"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validateOutlets()
	{
		try
		{
			boolean ret = outletsReflectService.isOutletsExist(city, outlets);
			if( ret == true )
			{
				message = "该网点名称已存在，请重新输入";
				result = false;
			}
			else
			{
				message = "该网点名称可以使用";
				result = true;
			}
		}
		catch(Exception e)
		{
			logger.error("检查网点名称异常", e);
			result = false;
			errorMsg = "检查网点名称异常";
			return "error";
		}
		
		return "success";
	}
	
	@Action
	(
		value = "validateNumber",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, message"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validateNumber()
	{
		try
		{
			boolean ret = outletsReflectService.isCodeExist(number);
			if( ret == true )
			{
				message = "该网点机构号已存在，请重新输入";
				result = false;
			}
			else
			{
				message = "该网点机构号可以使用";
				result = true;
			}
		}
		catch(Exception e)
		{
			logger.error("检查网点机构号异常", e);
			result = false;
			errorMsg = "检查网点机构号异常";
			return "error";
		}
		
		return "success";
	}
	
	@Action
	(
		value = "validateIp",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, message"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validateIp()
	{
		try
		{
			int startip = Integer.parseInt(startIP.substring(startIP.lastIndexOf(".") + 1));
			int endip = Integer.parseInt(endIP.substring(endIP.lastIndexOf(".") + 1));
			if(startip > endip)
			{
				message = "开始IP必须小与或等于结束IP";
				result = false;
			} 
			else
			{
				boolean ret = outletsReflectService.isIpUsable(number, startIP, endIP);
				if( ret == false )
				{
					message = "该IP地址范围有重复，请重新输入";
					result = false;
				}
				else
				{
					message = "该IP地址范围可以使用";
					result = true;
				}
			}
		}
		catch(Exception e)
		{
			logger.error("检查IP地址范围异常", e);
			result = false;
			errorMsg = "检查IP地址范围异常";
			return "error";
		}
		
		return "success";
	}
	
	@Action
	(
		value = "validateNewIp",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, message"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String validateNewIp()
	{
		try
		{
			int startip = Integer.parseInt(startIP.substring(startIP.lastIndexOf(".") + 1));
			int endip = Integer.parseInt(endIP.substring(endIP.lastIndexOf(".") + 1));
			if(startip > endip)
			{
				message = "开始IP必须小与或等于结束IP";
				result = false;
			} 
			else
			{
				boolean ret = outletsReflectService.isIpUsable(null, startIP, endIP);
				if( ret == false )
				{
					message = "该IP地址范围有重复，请重新输入";
					result = false;
				}
				else
				{
					message = "该IP地址范围可以使用";
					result = true;
				}
			}
		}
		catch(Exception e)
		{
			logger.error("检查IP地址范围异常", e);
			result = false;
			errorMsg = "检查IP地址范围异常";
			return "error";
		}
		
		return "success";
	}

	public boolean isResult() 
	{
		return result;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getErrorMsg() 
	{
		return errorMsg;
	}

	public OutletsReflectVo getReflectVo() 
	{
		return reflectVo;
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
	
}
