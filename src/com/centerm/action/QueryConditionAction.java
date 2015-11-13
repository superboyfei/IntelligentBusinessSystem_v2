package com.centerm.action;

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

import com.centerm.entity.Business;
import com.centerm.entity.DeviceType;
import com.centerm.entity.Outlets;
import com.centerm.service.device.DeviceTypeService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.vo.BusinessVo;
import com.centerm.vo.DeviceTypeVo;
import com.centerm.vo.OutletsVo;

@Controller("queryConditionAction")
@Scope("prototype")
@Namespace("/queryCondition")
@ParentPackage("json-default")
public class QueryConditionAction
{
	private static final Logger logger = LogManager.getLogger(QueryConditionAction.class);
	
	private boolean result = false;
	private List<OutletsVo> outletsVoList = new ArrayList<OutletsVo>();
	private List<BusinessVo> businessVoList = new ArrayList<BusinessVo>();
	private List<DeviceTypeVo> deviceTypeVoList = new ArrayList<DeviceTypeVo>();
	private String errorMsg = "";
	
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "deviceTypeService")
	private DeviceTypeService deviceTypeService;
	
	@Action
	(
		value = "loadOutlets",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, outletsVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadOutlets()
	{
		try
		{
			List<?> outletsList = outletsService.getAllOutlets();
			if ( outletsList == null )
			{
				result = false;
				errorMsg = "获取网点信息错误";
				return "error";
			}
			
			for ( int i = 0; i < outletsList.size(); i++ )
			{
				Outlets outlets = (Outlets)outletsList.get(i);
				OutletsVo outletsVo = new OutletsVo();
				outletsVo.setId(String.valueOf(outlets.getId()));
				logger.debug("id：" + outletsVo.getId());
				outletsVo.setName(outlets.getName());
				logger.debug("name：" + outletsVo.getName());
				outletsVo.setCode(outlets.getCode());
				logger.debug("code：" + outletsVo.getCode());
				if ( outlets.getIsparent() == 1 )
				{
					outletsVo.setIsParent(true);
				}
				else
				{
					outletsVo.setIsParent(false);
				}
				logger.debug("isparent：" + outletsVo.getIsParent());
				outletsVo.setParentid(String.valueOf(outlets.getParentid()));
				logger.debug("parentid：" + outletsVo.getParentid());
				outletsVo.setDescription(outlets.getDescription());
				logger.debug("description：" + outletsVo.getDescription());
				outletsVo.setCode(outlets.getCode());
				outletsVoList.add(outletsVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取网点信息异常", e);
			result = false;
			errorMsg = "获取网点信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadBusiness",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, businessVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadBusiness()
	{
		try
		{
			List<?> businessList = businessService.getAllBusiness();
			if ( businessList == null )
			{
				result = false;
				errorMsg = "获取业务信息错误";
				return "error";
			}
			
			for ( int i = 0; i < businessList.size(); i++ )
			{
				Business business = (Business)businessList.get(i);
				BusinessVo businessVo = new BusinessVo();
				businessVo.setId(String.valueOf(business.getId()));
				logger.debug("id：" + businessVo.getId());
				businessVo.setCode(business.getCode());
				logger.debug("code：" + businessVo.getCode());
				businessVo.setName(business.getName());
				logger.debug("name：" + businessVo.getName());
				businessVo.setParentid(String.valueOf(business.getParentid()));
				logger.debug("parentid：" + businessVo.getParentid());
				if ( business.getIsparent() == 1 )
				{
					businessVo.setIsParent(true);
				}
				else
				{
					businessVo.setIsParent(false);
				}
				logger.debug("isparent：" + businessVo.getIsParent());
				businessVoList.add(businessVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取业务信息异常", e);
			result = false;
			errorMsg = "获取业务信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadDeviceTypes",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, deviceTypeVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadDeviceTypes()
	{
		try
		{
			List<DeviceType> deviceTypeList = deviceTypeService.getAllDeviceType();
			if ( deviceTypeList == null )
			{
				result = false;
				errorMsg = "获取设备类型信息错误";
				return "error";
			}
			
			for ( DeviceType deviceType : deviceTypeList )
			{
				DeviceTypeVo deviceTypeVo = new DeviceTypeVo();
				deviceTypeVo.setId(String.valueOf(deviceType.getId()));
				logger.debug("id：" + deviceTypeVo.getId());
				deviceTypeVo.setName(deviceType.getCode());
				logger.debug("name：" + deviceTypeVo.getName());
				deviceTypeVo.setParentid("0");	//设备类型不是树形结构
				logger.debug("parentid：" + deviceTypeVo.getParentid());
				deviceTypeVoList.add(deviceTypeVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取设备类型信息异常", e);
			result = false;
			errorMsg = "获取设备类型信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public List<OutletsVo> getOutletsVoList()
	{
		return outletsVoList;
	}
	
	public List<BusinessVo> getBusinessVoList()
	{
		return businessVoList;
	}
	
	public List<DeviceTypeVo> getDeviceTypeVoList()
	{
		return deviceTypeVoList;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
