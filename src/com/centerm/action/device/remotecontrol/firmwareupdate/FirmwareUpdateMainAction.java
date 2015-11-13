package com.centerm.action.device.remotecontrol.firmwareupdate;

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

import com.centerm.entity.FirmwareType;
import com.centerm.entity.User;
import com.centerm.service.device.FirmwareService;
import com.centerm.vo.FirmwareTypeVo;

@Controller("firmwareUpdateMainAction")
@Scope("prototype")
@Namespace("/device/remotecontrol/firmwareupdate/main")
@ParentPackage("json-default")
public class FirmwareUpdateMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(FirmwareUpdateMainAction.class);
	
	private Integer id;	//固件类型id
	private String code;	//固件类型code
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<FirmwareTypeVo> firmwareTypeVoList = new ArrayList<FirmwareTypeVo>();
	private String firmwareInfo = "";
	private String errorMsg = "";
	
	@Resource(name = "firmwareService")
	private FirmwareService firmwareService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/device/device_rom.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("进入固件升级界面");
			logger.debug("用户session为null");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入固件升级界面");
		return "success";
	}
	
	@Action
	(
		value = "loadFirmwareInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, firmwareTypeVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadFirmwareInfo()
	{
		try
		{
			List<FirmwareType> firmwareTypeList = firmwareService.getAllFirmwareType();
			if ( firmwareTypeList == null )
			{
				result = false;
				errorMsg = "获取固件类型错误";
				return "error";
			}
			
			for ( FirmwareType firmwareType : firmwareTypeList )
			{
				FirmwareTypeVo firmwareTypeVo = new FirmwareTypeVo();
				firmwareTypeVo.setId(String.valueOf(firmwareType.getId()));
				logger.debug("id：" + firmwareTypeVo.getId());
				firmwareTypeVo.setCode(String.valueOf(firmwareType.getCode()));
				logger.debug("code：" + firmwareTypeVo.getCode());
				firmwareTypeVo.setName(firmwareType.getName());
				logger.debug("name：" + firmwareTypeVo.getName());
				firmwareTypeVo.setVersion(firmwareType.getVersion());
				logger.debug("version：" + firmwareTypeVo.getVersion());
				firmwareTypeVo.setUpdatetime(DateFormatUtils.format(firmwareType.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
				logger.debug("updatetime：" + firmwareTypeVo.getUpdatetime());
				String[] desStr = firmwareType.getDescription().split("%");
				firmwareTypeVo.setDescription(desStr[1]);	//截取最后一次更新的日志信息
				logger.debug("description：" + firmwareTypeVo.getDescription());
				firmwareTypeVoList.add(firmwareTypeVo);
			}
		}
		catch (Exception e)
		{
			logger.error("获取固件类型异常", e);
			result = false;
			errorMsg = "获取固件类型异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "lookFirmwareUpdateInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, firmwareInfo"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String lookFirmwareUpdateInfo()
	{
		try
		{
			firmwareInfo = firmwareService.getDescriptionByCode(code);
			if ( firmwareInfo == null )
			{
				result = false;
				errorMsg = "获取固件更新日志错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("获取固件更新日志异常", e);
			result = false;
			errorMsg = "获取固件更新日志异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "deleteFirmwareInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String deleteFirmwareInfo()
	{
		try
		{
			boolean ret = firmwareService.deleteFirmwareTypeById(id);
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除固件信息错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("删除固件信息异常", e);
			result = false;
			errorMsg = "删除固件信息异常";
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

	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}

	public List<FirmwareTypeVo> getFirmwareTypeVoList()
	{
		return firmwareTypeVoList;
	}

	public String getFirmwareInfo()
	{
		return firmwareInfo;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
