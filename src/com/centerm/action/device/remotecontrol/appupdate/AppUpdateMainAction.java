package com.centerm.action.device.remotecontrol.appupdate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.centerm.service.device.AppUpdateService;
import com.centerm.vo.AppVo;

@Controller("appUpdateMainAction")
@Scope("prototype")
@Namespace("/device/remotecontrol/appupdate/main")
@ParentPackage("json-default")
public class AppUpdateMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(AppUpdateMainAction.class);
	
	private String outlet;	//网点集合
	private String selectValue;	//发布应用集合
	private Map<String, Object> session;
	
	private boolean result = false;
	private Set<String> deviceAppIdsSet = new HashSet<String>();
	private List<AppVo> appInfoVoList = new ArrayList<AppVo>();
	private String errorMsg = "";
	
	@Resource
	private AppUpdateService appUpdateService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/device/device_app.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("进入应用发布界面");
			logger.debug("用户session为null");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入应用发布界面");
		return "success";
	}

	@Action
	(
		value = "loadDeviceAppByOutlets",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, deviceAppIdsSet.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadDeviceAppByOutlets()
	{
		try
		{
			List<String> deviceAppIds = appUpdateService.getDeviceAppByOutlets(outlet);
			for(String appIds : deviceAppIds)
			{
				String[] ids = appIds.split(",");
				for(String id : ids)
				{
					if(StringUtils.isNotBlank(id))
					{
						deviceAppIdsSet.add(id);
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error("获取网点设备安装的app信息异常", e);
			result = false;
			errorMsg = "获取网点设备安装的app信息失败";
			return "error";
		}
		result = true;
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Action
	(
		value = "loadAllAppInfo",
		results =
		{
			@Result(name = "success", type="json", params={"includeProperties", "result, appInfoVoList.*"}),
			@Result(name = "error", type="json", params={"includeProperties","result, retMsg"})
		}
	)
	public String loadAllAppInfo()
	{
		try
		{
			List<Map<String, Object>> appInfos = (List<Map<String, Object>>) appUpdateService.getAllAppInfo();
			for(Map<String, Object> app : appInfos)
			{
				AppVo vo = new AppVo();
				vo.setId(""+(Integer) app.get("id"));
				vo.setName((String) app.get("name"));
				vo.setIconPath((String) app.get("iconpath"));
				appInfoVoList.add(vo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取所有应用信息异常", e);
			errorMsg = "获取所有应用信息失败";
			result = false;
			return "error";
		}
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "publishApp",
		results =
		{
			@Result(name = "success", type="json", params={"includeProperties", "result, retMsg"}),
			@Result(name = "error", type="json", params={"includeProperties", "result, retMsg"})
		}
	)
	public String publishApp()
	{
		try
		{
			appUpdateService.publishApp(outlet, selectValue);
		}
		catch(Exception e)
		{
			logger.error("应用发布异常", e);
			result = false;
			errorMsg = "应用发布失败";
			return "error";
		}
		
		result = true;
		errorMsg = "应用发布成功";
		return "success";
	}
	
	public void setOutlet(String outlet)
	{
		this.outlet = outlet;
	}
	
	public void setSelectValue(String selectValue)
	{
		this.selectValue = selectValue;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	public boolean getResult()
	{
		return result;
	}
	
	public Set<String> getDeviceAppIdsSet()
	{
		return deviceAppIdsSet;
	}

	public List<AppVo> getAppInfoVoList()
	{
		return appInfoVoList;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
