package com.centerm.action.system.business;

import java.io.File;
import java.util.List;
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

import com.centerm.entity.App;
import com.centerm.entity.po.AppXmlPo;
import com.centerm.service.device.AppService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.vo.AppUploadInfoVo;
import com.centerm.vo.BusinessVo;

@Controller("businessUpdateAction")
@Scope("prototype")
@Namespace("/system/business/update")
@ParentPackage("json-default")
public class BusinessUpdateAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(BusinessAddAction.class);
	
	private Integer id;	//业务id
	private String code;	//业务编号
	private String name;	//业务名
	private boolean isParent;	//是否为父业务
	private Integer parentid; 	//父业务id
	private File appFile;	//上传应用文件
	private Map<String, Object> session;
	
	private boolean result = false;
	private boolean exist = false;
	private BusinessVo businessVo = new BusinessVo();
	private AppUploadInfoVo appUploadInfoVo = new AppUploadInfoVo();
	private String errorMsg = "";
	private String errorLevel = "";
	
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "appService")
	private AppService appService;
	
	@Action
    (
		value = "existBusinessGroup",
		results =
    	{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, exist"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"}),
    	}
	)
	public String existBusinessGroup()
	{
		try
		{
			boolean ret = businessService.isBusinessExist(code, isParent, parentid, name);
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
			logger.error("检测业务组异常", e);
			result = false;
    		errorMsg = "检测业务组异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
    (
		value = "updateBusinessGroup",
		results =
    	{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"}),
    	}
	)
	public String updateBusinessGroup()
	{
		try
		{
			boolean ret = businessService.updateBusinessGroup(id, name, name);
			if ( ret == false )
			{
				result = false;
				errorMsg = "更新业务组错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("更新业务组异常", e);
			result = false;
    		errorMsg = "更新业务组异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "uploadAppFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, appUploadInfoVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg, errorLevel"})
		}
	)
    public String uploadAppFile()
    {
		try
		{
			AppXmlPo appXmlPo = appService.parseDatFile(appFile);
			if ( appXmlPo == null )
			{
				result = false;
				errorLevel = "error";
    			errorMsg = "上传的文件非APK版本文件";
    			return "error";
			}
			
			if(appXmlPo.getDescription().getBytes("GBK").length > 512) {
				result = false;
				errorLevel = "warning";
				errorMsg = "上传的文件更新说明必须少于512个字符(256个汉字)";
				return "error";
			}
			
			boolean ret = businessService.isSameBusiness(id, appXmlPo.getCode());
			if ( ret == false )
			{
				result = false;
				errorLevel = "error";
    			errorMsg = "上传的业务版本信息与待更新的业务版本信息不符";
    			return "error";
			}
			
			ret = businessService.isNeedUpdate(id, appXmlPo.getVersion());
			if ( ret == false )
			{
				result = false;
				errorLevel = "warning";
    			errorMsg = appXmlPo.getName()+"版本已存在，无需重复更新";
    			return "error";
			}
			
			appUploadInfoVo.setName(appXmlPo.getName());
			logger.debug("name：" + appUploadInfoVo.getName());
			appUploadInfoVo.setCode(appXmlPo.getCode());
			logger.debug("code：" + appUploadInfoVo.getCode());
			appUploadInfoVo.setVersion(appXmlPo.getVersion());
			logger.debug("version：" + appUploadInfoVo.getVersion());
			appUploadInfoVo.setDescription(appXmlPo.getDescription());
			logger.debug("description：" + appUploadInfoVo.getDescription());
			List<?> appList = (List<?>)session.get("appList");
			String iconPath = ((App)appList.get(0)).getIconpath();
			appUploadInfoVo.setIconPath(iconPath);
			logger.debug("iconPath：" + appUploadInfoVo.getIconPath());
			session.put("appXmlPo", appXmlPo);	//将应用dat信息暂时保存在session中
		}
		catch(Exception e)
		{
			logger.error("上传应用包异常", e);
			result = false;
			errorLevel = "error";
			errorMsg = "上传应用包异常";
			return "error";
		}
		
		result = true;
		return "success";
    }
	
	@Action
	(
		value = "updateBusiness",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String updateBusiness()
	{
		try
		{
			boolean ret = businessService.updateBusiness(id);
			if ( ret == false )
			{
				result = false;
				errorMsg = "更新业务错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("更新业务异常", e);
			result = false;
			errorMsg = "更新业务异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "cancelSaveAppInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String cancelSaveAppInfo()
	{
		try
		{
			boolean ret = appService.deleteTmpAppFile();
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除临时应用文件错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("删除临时应用文件异常", e);
			result = false;
			errorMsg = "删除临时应用文件异常";
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
	
	public void setAppFile(File appFile)
	{
		this.appFile = appFile;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public boolean getExist()
	{
		return exist;
	}
	
	public BusinessVo getBusinessVo()
	{
		return businessVo;
	}
	
	public AppUploadInfoVo getAppUploadInfoVo()
	{
		return appUploadInfoVo;
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
