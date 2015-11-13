package com.centerm.action.business.statistics.querybydeal;

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
import com.centerm.entity.po.QueryByDealPo;
import com.centerm.entity.po.QueryByDealSubPo;
import com.centerm.service.business.StatisticsQueryByDealService;
import com.centerm.vo.TableVo;

@Controller("queryByDealMainAction")
@Scope("prototype")
@Namespace("/business/statistics/querybydeal/main")
@ParentPackage("json-default")
public class QueryByDealMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(QueryByDealMainAction.class);
	
	private String bussType;	//业务类型
	private String outlet;	//网点
	private String startDate;	//起始日期
	private String endDate;	//结束日期
	private Integer page;	//当前页码
	private Integer rows;	//每页加载记录数
	private Map<String, Object> session;
	
	private boolean result = false;
	private TableVo tableVo = new TableVo();
	private List<QueryByDealPo> queryByDealPoList;
	private String errorMsg = "";
	
	@Resource(name = "statisticsQueryByDealService")
	private StatisticsQueryByDealService statisticsQueryByDealService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/bquery/deal-sort.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入按业务类型查询界面");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入按业务类型查询界面");
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
			List<QueryByDealPo> businessStatisticsList = statisticsQueryByDealService.getBusinessStatisticsListByPage(bussType, outlet, startDate, endDate, page, rows);
			if ( businessStatisticsList == null )
			{
				result = false;
				errorMsg = "按业务类型查询错误";
				return "error";
			}
			
			int totalCount = statisticsQueryByDealService.getBusinessStatisticsCount(bussType);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "按业务类型查询错误";
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
			logger.error("按业务类型查询异常", e);
			result = false;
			errorMsg = "按业务类型查询异常";
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
			List<QueryByDealSubPo> bussinessStatisticsList = statisticsQueryByDealService.getSubBusinessListByPage(bussType, outlet, startDate, endDate, page, rows);
			if ( bussinessStatisticsList == null )
			{
				result = false;
				errorMsg = "按业务类型查询错误";
				return "error";
			}
			
			int totalCount = statisticsQueryByDealService.getSubBusinessStatisticsCount(bussType, outlet, startDate, endDate);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "按业务类型查询错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			logger.debug("totalPage：" + tableVo.getTotalpages());
			tableVo.setCurrpage(page);
			logger.debug("currentPage：" + tableVo.getCurrpage());
			tableVo.setTotalrecords(totalCount);
			logger.debug("totalRecord：" + tableVo.getTotalrecords());
			tableVo.setList(bussinessStatisticsList);
		}
		catch(Exception e)
		{
			logger.error("按业务类型查询异常", e);
			result = false;
			errorMsg = "按业务类型查询异常";
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
			@Result(name = "success", type = "json", params = {"includeProperties", "result, queryByDealPoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadAllBusinessStatistics()
	{
		try
		{
			queryByDealPoList = statisticsQueryByDealService.getBusinessStatisticsList(bussType, outlet, startDate, endDate);
			if ( queryByDealPoList == null )
			{
				result = false;
				errorMsg = "按业务类型查询错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("按业务类型查询异常", e);
			result = false;
			errorMsg = "按业务类型查询异常";
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
	
	public List<QueryByDealPo> getQueryByDealPoList()
	{
		return queryByDealPoList;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
