package com.centerm.action.system.business;

import java.util.ArrayList;
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
import com.centerm.entity.Business;
import com.centerm.entity.User;
import com.centerm.service.device.AppService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.vo.AppVo;
import com.centerm.vo.BusinessVo;

@Controller("businessMainAction")
@Scope("prototype")
@Namespace("/system/business/main")
@ParentPackage("json-default")
public class BusinessMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(BusinessMainAction.class);
	
	private Integer id;	//业务id
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<BusinessVo> businessVoList = new ArrayList<BusinessVo>();
	private AppVo appVo = new AppVo();
	private String appUpdateInfo = "";
	private String errorMsg = "";
	
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "appService")
	private AppService appService;
	
    @Action
    (
        value = "toUI",
        results =
        {
            @Result(name = "success", location = "/WEB-INF/jsp/business/business_manage.jsp"),
        }
    )
    public String toUI()
    {
    	User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入业务管理界面");
			return "success";
		}
		
		logger.info("用户" + user.getUid() + "进入业务管理界面");
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
		value = "loadAppInfoByBusinessId",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, appVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
    public String loadAppInfoByBusinessId()
    {
    	try
		{
    		App app = appService.getAppByBusinessid(id);
    		if ( app == null )
    		{
    			result = false;
    			errorMsg = "获取应用信息错误";
    			return "error";
    		}
    		
    		appVo.setId(String.valueOf(app.getId()));
    		logger.debug("id：" + appVo.getId());
    		appVo.setName(app.getName());
    		logger.debug("name：" + appVo.getName());
    		appVo.setVersion(app.getVersion());
    		logger.debug("version：" + appVo.getVersion());
    		appVo.setIconPath(app.getIconpath());
    		logger.debug("iconPath：" + appVo.getIconPath());
    		appVo.setDescription(app.getDescription());
    		logger.debug("description：" + appVo.getDescription());
		}
		catch(Exception e)
		{
			logger.error("获取应用信息异常", e);
			result = false;
			errorMsg = "获取应用信息异常";
			return "error";
		}
    	
		result = true;
		return "success";
    }
    
    @Action
	(
		value = "loadAppUpdateInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, appUpdateInfo"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
    public String loadAppUpdateInfo()
    {
    	try
    	{
    		appUpdateInfo = appService.getAppUpdateInfo(id);
    		if ( appUpdateInfo == null )
    		{
    			result = false;
    			errorMsg = "获取应用更新信息错误";
    			return "error";
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error("获取应用更新信息异常", e);
			result = false;
			errorMsg = "获取应用更新信息异常";
			return "error";
    	}
    	
    	result = true;
    	return "success";
    }
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	public boolean getResult()
	{
		return result;
	}

	public List<BusinessVo> getBusinessVoList()
	{
		return businessVoList;
	}

	public AppVo getAppVo()
	{
		return appVo;
	}
	
	public String getAppUpdateInfo()
	{
		return appUpdateInfo;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
