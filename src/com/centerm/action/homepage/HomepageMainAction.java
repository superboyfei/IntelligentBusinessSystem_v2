package com.centerm.action.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.Function;
import com.centerm.entity.User;
import com.centerm.entity.po.BusinessStatisticsPo;
import com.centerm.service.business.StatisticsQueryService;
import com.centerm.service.device.DeviceService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.service.manage.user.AuthorityService;
import com.centerm.vo.BusinessStatisticsVo;
import com.centerm.vo.FunctionVo;

@Controller("homepageMainAction")
@Scope("prototype")
@Namespace("/homepage/main")
@ParentPackage("json-default")
public class HomepageMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(HomepageMainAction.class);

	private String startDate;
	private String endDate;
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<FunctionVo> functionVoList = new ArrayList<FunctionVo>();
	private String count = "";
	private List<BusinessStatisticsVo> businessStatisticsVoList = new ArrayList<BusinessStatisticsVo>();
	private String errorMsg = "";
	
	@Resource(name = "authorityService")
	private AuthorityService authorityService;
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "statisticsQueryService")
	private StatisticsQueryService statisticsQueryService;
	
	@Action
	(
		value = "mainInfo",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/main-ui.jsp")
		}
	)
	public String mainInfo()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入首页");
			return "success";
		}
		
		logger.info("用户" + user.getUid() + "进入首页");
		return "success";
	}
	
	@Action
	(
		value = "loadUserFunction",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, functionVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadUserFunction()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			result = false;
			errorMsg = "获取用户信息错误";
			return "error";
		}
		
		try
		{
			List<?> functionList = authorityService.getUserFunctionList(user.getId());
			if ( functionList == null )
			{
				result = false;
				errorMsg = "获取用户功能列表错误";
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
				functionVoList.add(functionVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取用户功能列表异常", e);
			result = false;
			errorMsg = "获取用户功能列表异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadOutletsCount",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, count"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadOutletsCount()
	{
		try
		{
			int outletsCount = outletsService.getOutletsCountExceptParent();
			if ( outletsCount == -1 )
			{
				result = false;
				errorMsg = "获取网点总数错误";
				return "error";
			}
				
			count = String.valueOf(outletsCount);
		}
		catch(Exception e)
		{
			logger.error("获取网点总数异常", e);
			result = false;
			errorMsg = "获取网点总数异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadDeviceCount",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, count"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadDeviceCount()
	{
		try
		{
			int deviceCount = deviceService.getDeviceCount();
			if ( deviceCount == -1 )
			{
				result = false;
				errorMsg = "获取设备总数错误";
				return "error";
			}
			
			count = String.valueOf(deviceCount);
		}
		catch(Exception e)
		{
			logger.error("获取设备总数异常", e);
			result = false;
			errorMsg = "获取设备总数异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadBusinessCount",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, count"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadBusinessCount()
	{
		try
		{
			int businessCount = businessService.getBusinessCountExceptParent();
			if ( businessCount == -1 )
			{
				result = false;
				errorMsg = "获取业务总数错误";
				return "error";
			}
			
			count = String.valueOf(businessCount);
		}
		catch(Exception e)
		{
			logger.error("获取业务总数异常", e);
			result = false;
			errorMsg = "获取业务总数异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	@Action
	(
		value = "loadBusinessStatistics",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, businessStatisticsVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadBusinessStatistics()
	{
		try
		{
			List<BusinessStatisticsPo> businessStatisticsPoList = statisticsQueryService.getBusinessStatisticsEveryDay(startDate, endDate);
			if ( businessStatisticsPoList == null )
			{
				result = false;
				errorMsg = "获取业务量统计错误";
				return "error";
			}
			
			for ( BusinessStatisticsPo businessStatisticsPo : businessStatisticsPoList )
			{
				BusinessStatisticsVo businessStatisticsVo = new BusinessStatisticsVo();
				businessStatisticsVo.setDate(DateFormatUtils.format(businessStatisticsPo.getDate(), "yyyy-MM-dd"));
				businessStatisticsVo.setCount(businessStatisticsPo.getCount());
				businessStatisticsVoList.add(businessStatisticsVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取业务量统计异常", e);
			result = false;
			errorMsg = "获取业务量统计异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public List<FunctionVo> getFunctionVoList()
	{
		return functionVoList;
	}
	
	public String getCount()
	{
		return count;
	}
	
	public List<BusinessStatisticsVo> getBusinessStatisticsVoList()
	{
		return businessStatisticsVoList;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
