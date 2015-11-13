package com.centerm.action.device.remotecontrol.counterappupdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.centerm.entity.CounterAppType;
import com.centerm.entity.User;
import com.centerm.service.device.CounterAppService;
import com.centerm.vo.CounterAppTypeVo;

@Controller("counterAppUpdateMainAction")
@Scope("prototype")
@Namespace("/device/remotecontrol/counterappupdate/main")
@ParentPackage("json-default")
public class CounterAppUpdateMainAction implements SessionAware {
	private static final Logger logger = LogManager.getLogger(CounterAppUpdateMainAction.class);
	
	private Integer counterAppTypeId;	//柜员应用类型id
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<CounterAppTypeVo> counterAppTypeVoList = new ArrayList<CounterAppTypeVo>();
	private String counterAppInfo = "";
	private String errorMsg = "";
	
	@Resource
	private CounterAppService counterAppService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/device/counter_app.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("进入柜员应用更新界面");
			logger.debug("用户session为null");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入柜员应用更新界面");
		return "success";
	}
	
	@Action
	(
		value = "loadCounterAppInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, counterAppTypeVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadCounterAppInfo()
	{
		try
		{
			List<CounterAppType> counterAppTypeList = counterAppService.getAllCounterAppType();
			if ( counterAppTypeList == null )
			{
				result = false;
				errorMsg = "获取柜员应用类型错误";
				return "error";
			}
			
			for ( CounterAppType counterAppType : counterAppTypeList )
			{
				CounterAppTypeVo counterAppTypeVo = new CounterAppTypeVo();
				counterAppTypeVo.setId(String.valueOf(counterAppType.getId()));
				logger.debug("id：" + counterAppTypeVo.getId());
				counterAppTypeVo.setCode(String.valueOf(counterAppType.getCode()));
				logger.debug("code：" + counterAppTypeVo.getCode());
				counterAppTypeVo.setName(counterAppType.getName());
				logger.debug("name：" + counterAppTypeVo.getName());
				counterAppTypeVo.setVersion(counterAppType.getVersion());
				logger.debug("version：" + counterAppTypeVo.getVersion());
				counterAppTypeVo.setUpdatetime(DateFormatUtils.format(counterAppType.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
				logger.debug("updatetime：" + counterAppTypeVo.getUpdatetime());
				String[] desStr = counterAppType.getDescription().split("%");
				counterAppTypeVo.setDescription(desStr[1]);	//截取最后一次更新的日志信息
				logger.debug("description：" + counterAppTypeVo.getDescription());
				counterAppTypeVoList.add(counterAppTypeVo);
			}
		}
		catch (Exception e)
		{
			logger.error("获取柜员应用类型异常", e);
			result = false;
			errorMsg = "获取柜员应用类型异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "lookCounterAppUpdateInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, counterAppInfo"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String lookCounterAppUpdateInfo()
	{
		try
		{
			counterAppInfo = counterAppService.getDescriptionById(this.counterAppTypeId);
			if ( counterAppInfo == null )
			{
				result = false;
				errorMsg = "获取柜员应用更新日志错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("获取柜员应用更新日志错误", e);
			result = false;
			errorMsg = "获取柜员应用更新日志错误";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "deleteCounterAppInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String deleteCounterAppInfo()
	{
		try
		{
			boolean ret = counterAppService.deleteCounterAppTypeById(this.counterAppTypeId);
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除柜员应用信息错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("删除柜员应用信息异常", e);
			result = false;
			errorMsg = "删除柜员应用信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public boolean isResult() {
		return result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public List<CounterAppTypeVo> getCounterAppTypeVoList() {
		return counterAppTypeVoList;
	}

	public String getCounterAppInfo() {
		return counterAppInfo;
	}

	public void setCounterAppTypeId(Integer counterAppTypeId) {
		this.counterAppTypeId = counterAppTypeId;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
