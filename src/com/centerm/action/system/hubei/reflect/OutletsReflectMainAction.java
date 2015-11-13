package com.centerm.action.system.hubei.reflect;

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

import com.centerm.entity.User;
import com.centerm.entity.hubei.OutletsReflect;
import com.centerm.service.business.hubei.OutletsReflectService;
import com.centerm.vo.TableVo;
import com.centerm.vo.hubei.OutletsReflectVo;

@Controller("outletsReflectMainAction")
@Scope("prototype")
@Namespace("/system/hubei/reflect/main")
@ParentPackage("json-default")
public class OutletsReflectMainAction implements SessionAware 
{
	private static final Logger logger = LogManager.getLogger(OutletsReflectMainAction.class);
	
	private Map<String, Object> session;
	private List<OutletsReflectVo> outletsReflectVoList = new ArrayList<OutletsReflectVo>();
	private TableVo tableVo = new TableVo();
	
	private String id;
	private Integer page;	//当前页码
	private Integer rows;	//每页加载记录数
	private String errorMsg = "";
	private boolean result = false;
	
	@Resource(name = "outletsReflectService")
	private OutletsReflectService outletsReflectService;
	
	@Action
    (
        value = "toUI",
        results =
        {
            @Result(name = "success", location = "/WEB-INF/jsp/outletsreflect.jsp"),
        }
    )
    public String toUI()
    {
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("映射进入网点IP映射管理界面");
			return "success";
		}
		
		logger.info("映射" + user.getUid() + "进入网点IP映射管理界面");
		return "success";
    }
	
	@Action
	(
		value = "loadReflectsByPage",
				results =
			{
				@Result(name = "success", type = "json", params = {"includeProperties", "result, tableVo.*"}),
				@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
			}
	)
	public String loadReflectsByPage()
	{
		try
		{
			@SuppressWarnings("unchecked")
			List<OutletsReflect> reflectList = (List<OutletsReflect>) outletsReflectService.getReflectByPage(page, rows);
			if ( reflectList == null )
			{
				result = false;
				errorMsg = "获取映射列表错误";
				return "error";
			}
			for(OutletsReflect ref : reflectList){
				logger.debug("id：" + ref.getId());
				logger.debug("city：" + ref.getCity());
				logger.debug("outlets：" + ref.getOutlets());
				logger.debug("outletsCode：" + ref.getCode());
				logger.debug("startIp：" + ref.getStart());
				logger.debug("endIp：" + ref.getEnd());
				logger.debug("the last num of ip(start)：" + ref.getStartnum());
				logger.debug("the last num of ip(end)：" + ref.getEndnum());
				logger.debug("submask：" + ref.getSubmask());
			}
			
			int totalCount = outletsReflectService.getReflectCount();
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "获取映射列表错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			logger.debug("totalPage：" + tableVo.getTotalpages());
			tableVo.setCurrpage(page);
			logger.debug("currentPage：" + tableVo.getCurrpage());
			tableVo.setTotalrecords(totalCount);
			logger.debug("totalRecord：" + tableVo.getTotalrecords());
			tableVo.setList(reflectList);
		}
		catch(Exception e)
		{
			logger.error("获取映射列表异常", e);
			result = false;
			errorMsg = "获取映射列表异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "delReflect",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String delReflect()
	{
		try
		{
			boolean ret = outletsReflectService.deleteReflect(Integer.parseInt(id));
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除网点IP映射记录失败";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("删除网点IP映射记录异常", e);
			result = false;
			errorMsg = "删除网点IP映射记录异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public List<OutletsReflectVo> getOutletsReflectVoList() 
	{
		return outletsReflectVoList;
	}
	
	public TableVo getTableVo()
	{
		return tableVo;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
}
