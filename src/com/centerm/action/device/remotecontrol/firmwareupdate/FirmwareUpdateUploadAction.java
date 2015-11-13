package com.centerm.action.device.remotecontrol.firmwareupdate;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.po.FirmwareXmlPo;
import com.centerm.service.device.FirmwareService;
import com.centerm.vo.FirmwareTypeVo;

@Controller("firmwareUpdateUploadAction")
@Scope("prototype")
@Namespace("/device/remotecontrol/firmwareupdate/upload")
@ParentPackage("json-default")
public class FirmwareUpdateUploadAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(FirmwareUpdateUploadAction.class);
	
	private File firmwareFile;	//上传固件文件
	private String firmwareFileFileName;	//上传固件文件名
	private Map<String, Object> session;
	
	private boolean result = false;
	private FirmwareTypeVo firmwareTypeVo = new FirmwareTypeVo();
	private String errorMsg = "";
	private String errorLevel = "";
	
	@Resource(name = "firmwareService")
	private FirmwareService firmwareService;
	
	@Action
	(
		value = "uploadFirmwareFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, firmwareTypeVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorLevel, errorMsg"})
		}
	)
	public String uploadFirmwareFile()
	{
		try
		{
			FirmwareXmlPo firmwareXmlPo = firmwareService.parseDatFile(firmwareFile, firmwareFileFileName);
			if ( firmwareXmlPo == null )
			{
				result = false;
				errorLevel = "error";
				errorMsg = "固件包解析错误";
				return "error";
			}
			if ( firmwareXmlPo.getResult() == false )
			{
				result = false;
				errorMsg = firmwareXmlPo.getErrorMsg();
				if(errorMsg.indexOf("error_") >= 0){
					errorLevel = "error";
					errorMsg = errorMsg.replace("error_", "");
				} else {
					errorLevel = "warning";
					errorMsg = errorMsg.replace("warning_", "");
				}
				return "error";
			}
			
			firmwareTypeVo.setName(firmwareXmlPo.getName());
			firmwareTypeVo.setVersion(firmwareXmlPo.getVersion());
			firmwareTypeVo.setDescription(firmwareXmlPo.getDescription());
			session.put("firmwareXmlPo", firmwareXmlPo);	//将固件dat信息暂时保存在session中
		}
		catch (Exception e)
		{
			logger.error("固件包解析异常", e);
			result = false;
			errorLevel = "error";
			errorMsg = "固件包解析异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "saveFirmwareInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveFirmwareInfo()
	{
		try
		{
			boolean ret = firmwareService.saveFirmwareInfo();
			if ( ret == false )
			{
				result = false;
				errorMsg = "保存固件信息失败";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("保存固件信息异常", e);
			result = false;
			errorMsg = "保存固件信息异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	@Action
	(
		value = "cancelSaveFirmwarInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result"})
		}
	)
	public String cancelSaveFirmwarInfo()
	{
		try
		{
			firmwareService.deleteTmpFirmwareFile();
		}
		catch (Exception e)
		{
			logger.error("删除临时固件文件异常", e);
			result = false;
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setFirmwareFile(File firmwareFile)
	{
		this.firmwareFile = firmwareFile;
	}

	public void setFirmwareFileFileName(String firmwareFileFileName)
	{
		this.firmwareFileFileName = firmwareFileFileName;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	public boolean getResult()
	{
		return result;
	}

	public FirmwareTypeVo getFirmwareTypeVo()
	{
		return firmwareTypeVo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
	
	public String getErrorLevel()
	{
		return errorLevel;
	}
}
