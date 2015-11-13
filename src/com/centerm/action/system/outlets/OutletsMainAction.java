package com.centerm.action.system.outlets;

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

import com.centerm.entity.Outlets;
import com.centerm.entity.User;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.vo.OutletsVo;

@Controller("outletsMainAction")
@Scope("prototype")
@Namespace("/system/outlets/main")
@ParentPackage("json-default")
public class OutletsMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(OutletsMainAction.class);

	private Integer id;	//网点id
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<OutletsVo> outletsVoList = new ArrayList<OutletsVo>();
	private OutletsVo outletsVo = new OutletsVo();
	private String errorMsg = "";
	
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	
	@Action
    (
        value = "toUI",
        results =
        {
            @Result(name = "success", location = "/WEB-INF/jsp/outlets/outlets_manage.jsp"),
        }
    )
    public String toUI()
    {
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入网点管理界面");
			return "success";
		}
		
		logger.info("用户" + user.getUid() + "进入网点管理界面");
		return "success";
    }
	
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
		value = "loadOutletsInfoById",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, outletsVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
    public String loadOutletsInfoById()
    {
    	try
		{
    		Outlets outlets = outletsService.getOutletsById(id);
    		if ( outlets == null )
    		{
    			result = false;
    			errorMsg = "获取网点信息错误";
    			return "error";
    		}
    		
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
	
	public List<OutletsVo> getOutletsVoList()
	{
		return outletsVoList;
	}
	
	public OutletsVo getOutletsVo()
	{
		return outletsVo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
