package com.centerm.action.device.remotecontrol.counterappupdate;

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

import com.centerm.entity.po.CounterAppXmlPo;
import com.centerm.service.device.CounterAppService;
import com.centerm.vo.CounterAppTypeVo;

@Controller("counterAppUpdateUploadAction")
@Scope("prototype")
@Namespace("/device/remotecontrol/counterappupdate/upload")
@ParentPackage("json-default")
public class CounterAppUpdateUploadAction implements SessionAware {

	private static final Logger logger = LogManager.getLogger(CounterAppUpdateUploadAction.class);
	
	private File counterAppFile;	//上传柜员应用文件
	private String counterAppFileFileName;	//上传柜员应用文件名
	private Map<String, Object> session;
	
	private boolean result = false;
	private CounterAppTypeVo counterAppTypeVo = new CounterAppTypeVo();
	private String errorMsg = "";
	private String errorLevel = "";
	
	@Resource(name = "counterAppService")
	private CounterAppService counterAppService;
	@Action
	(
		value = "uploadCounterAppFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, counterAppTypeVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorLevel, errorMsg"})
		}
	)
	public String uploadCounterAppFile()
	{
		try
		{
			CounterAppXmlPo counterAppXmlPo = counterAppService.parseDatFile(counterAppFile, counterAppFileFileName);
			if ( counterAppXmlPo == null )
			{
				result = false;
				errorLevel = "error";
				errorMsg = "柜员应用包解析错误";
				return "error";
			}
			if ( counterAppXmlPo.getResult() == false )
			{
				result = false;
				errorMsg = counterAppXmlPo.getErrorMsg();
				if(errorMsg.indexOf("error_") >= 0){
					errorLevel = "error";
					errorMsg = errorMsg.replace("error_", "");
				} else {
					errorLevel = "warning";
					errorMsg = errorMsg.replace("warning_", "");
				}
				return "error";
			}
			
			counterAppTypeVo.setName(counterAppXmlPo.getName());
			counterAppTypeVo.setVersion(counterAppXmlPo.getVersion());
			counterAppTypeVo.setDescription(counterAppXmlPo.getDescription());
			session.put("counterAppXmlPo", counterAppXmlPo);	//将柜员应用dat信息暂时保存在session中
		}
		catch (Exception e)
		{
			logger.error("柜员应用包解析异常", e);
			result = false;
			errorLevel = "error";
			errorMsg = "柜员应用包解析异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "saveCounterAppInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveCounterAppInfo()
	{
		try
		{
			boolean ret = counterAppService.saveCounterAppInfo();
			if ( ret == false )
			{
				result = false;
				errorMsg = "保存柜员应用信息失败";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("保存柜员应用信息异常", e);
			result = false;
			errorMsg = "保存柜员应用信息异常";
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
			counterAppService.deleteTmpCounterAppFile();
		}
		catch (Exception e)
		{
			logger.error("删除临时柜员应用文件异常", e);
			result = false;
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public boolean isResult() {
		return result;
	}

	public CounterAppTypeVo getCounterAppTypeVo() {
		return counterAppTypeVo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String getErrorLevel() {
		return errorLevel;
	}

	public void setCounterAppFile(File counterAppFile) {
		this.counterAppFile = counterAppFile;
	}

	public void setCounterAppFileFileName(String counterAppFileFileName) {
		this.counterAppFileFileName = counterAppFileFileName;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
