package com.centerm.action.business.statistics.querybyoutlets;

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
import com.centerm.entity.po.QueryByOutletsPo;
import com.centerm.entity.po.QueryByOutletsSubPo;
import com.centerm.service.business.StatisticsQueryByOutletsService;
import com.centerm.vo.TableVo;

@Controller("queryByOutletsMainAction")
@Scope("prototype")
@Namespace("/business/statistics/querybyoutlets/main")
@ParentPackage("json-default")
public class QueryByOutletsMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(QueryByOutletsMainAction.class);
	
	private String bussType;	//业务类型
	private String outlet;	//网点
	private String startDate;	//起始日期
	private String endDate;	//结束日期
	private Integer page;	//当前页码
	private Integer rows;	//每页加载记录数
	private Map<String, Object> session;
	
	private boolean result = false;
	private TableVo tableVo = new TableVo();
	private List<QueryByOutletsPo> queryByOutletsPoList;
	private String errorMsg = "";
	
	@Resource(name = "statisticsQueryByOutletsService")
	private StatisticsQueryByOutletsService statisticsQueryByOutletsService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/bquery/outlets-sort.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入按网点查询界面");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入按网点查询界面");
		return "success";
	}
	
	@Action
	(
		value = "loadBusinessStatistics",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, tableVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadBusinessStatistics()
	{
		try
		{
			List<QueryByOutletsPo> businessStatisticsList = statisticsQueryByOutletsService.getBusinessStatisticsListByPage(bussType, outlet, startDate, endDate, page, rows);
			if ( businessStatisticsList == null )
			{
				result = false;
				errorMsg = "按网点查询错误";
				return "error";
			}
			
			int totalCount = statisticsQueryByOutletsService.getBusinessStatisticsCount(outlet);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "按网点查询错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			logger.debug("totalPage：" + tableVo.getTotalpages());
			tableVo.setCurrpage(page);
			logger.debug("currentPage：" + tableVo.getCurrpage());
			tableVo.setTotalrecords(totalCount);
			logger.debug("totalRecord：" + tableVo.getTotalrecords());
			tableVo.setList(businessStatisticsList);
		}
		catch(Exception e)
		{
			logger.error("按网点查询异常", e);
			result = false;
			errorMsg = "按网点查询异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadSubBusinessStatistics",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, tableVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadSubBusinessStatistics()
	{
		try
		{
			List<QueryByOutletsSubPo> businessStatisticsList = statisticsQueryByOutletsService.getSubBusinessListByPage(bussType, outlet, startDate, endDate, page, rows);
			if ( businessStatisticsList == null )
			{
				result = false;
				errorMsg = "按网点查询错误";
				return "error";
			}
			
			int totalCount = statisticsQueryByOutletsService.getSubBusinessStatisticsCount(bussType, outlet, startDate, endDate);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "按网点查询错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			logger.debug("totalPage：" + tableVo.getTotalpages());
			tableVo.setCurrpage(page);
			logger.debug("currentPage：" + tableVo.getCurrpage());
			tableVo.setTotalrecords(totalCount);
			logger.debug("totalRecord：" + tableVo.getTotalrecords());
			tableVo.setList(businessStatisticsList);
		}
		catch(Exception e)
		{
			logger.error("按网点查询异常", e);
			result = false;
			errorMsg = "按网点查询异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "loadAllBusinessStatistics",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, queryByOutletsPoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadAllBusinessStatistics()
	{
		try
		{
			queryByOutletsPoList = statisticsQueryByOutletsService.getBusinessStatisticsList(bussType, outlet, startDate, endDate);
			if ( queryByOutletsPoList == null )
			{
				result = false;
				errorMsg = "按网点查询错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("按网点查询异常", e);
			result = false;
			errorMsg = "按网点查询异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setBussType(String bussType)
	{
		this.bussType = bussType;
	}
	
	public void setOutlet(String outlet)
	{
		this.outlet = outlet;
	}
	
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	
	public void setPage(Integer page)
	{
		this.page = page;
	}
	
	public void setRows(Integer rows)
	{
		this.rows = rows;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public boolean getResult()
	{
		return result;
	}

	public TableVo getTableVo()
	{
		return tableVo;
	}
	
	public List<QueryByOutletsPo> getQueryByOutletsPoList()
	{
		return queryByOutletsPoList;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
