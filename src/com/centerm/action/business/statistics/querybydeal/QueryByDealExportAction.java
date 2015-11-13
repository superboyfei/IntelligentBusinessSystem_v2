package com.centerm.action.business.statistics.querybydeal;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.business.StatisticsExportService;

@Controller("queryByDealExportAction")
@Scope("prototype")
@Namespace("/business/statistics/querybydeal/export")
@ParentPackage("json-default")
public class QueryByDealExportAction implements ServletResponseAware, SessionAware
{
	private static final Logger logger = LogManager.getLogger(QueryByDealExportAction.class);
	
	private String bussType;	//业务类型
	private String outlet;	//网点
	private String startDate;	//起始日期
	private String endDate;	//结束日期
	private HttpServletResponse response;
	private Map<String, Object> session;
	
	private boolean result = false;
	private String errorMsg = "";
	
	@Resource(name = "statisticsExportService")
	private StatisticsExportService statisticsExportService;
	
	@Action
	(
		value = "loadExportData",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadExportData()
	{
		session.put("bussType", bussType);
		session.put("outlet", outlet);
		session.put("startDate", startDate);
		session.put("endDate", endDate);
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "exportExcel",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String exportExcel()
	{
		try
		{
			HSSFWorkbook wb = statisticsExportService.getExportDataByDeal();
			if ( wb == null )
			{
				result = false;
				errorMsg = "按业务类型导出报表错误";
				return "error";
			}
			
			OutputStream ouputStream = response.getOutputStream();
			response.reset();
			response.setContentType("text/html");
			response.setHeader("Content-disposition", "attachment;filename=" + DateFormatUtils.format(new Date(), "yyyy-MM-dd_HHmmss") + ".xls");
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}
		catch(Exception e)
		{
			logger.error("按业务类型导出报表异常", e);
			result = false;
			errorMsg = "按业务类型导出报表异常";
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
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public void setServletResponse(HttpServletResponse response)
	{
		this.response = response;
	}
	
	public boolean getResult()
	{
		return result;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
