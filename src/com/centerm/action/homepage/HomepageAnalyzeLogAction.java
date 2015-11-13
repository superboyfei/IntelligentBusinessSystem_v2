package com.centerm.action.homepage;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.manage.system.AnalyzeLogService;

@Controller("homepageAnalyzeLogAction")
@Scope("prototype")
@Namespace("/homepage/databaselog")
@ParentPackage("json-default")
public class HomepageAnalyzeLogAction implements ServletRequestAware, ServletResponseAware
{
	private static final Logger logger = LogManager.getLogger(HomepageLogAction.class);
	
	private String startDate;
	private String endDate;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private boolean result = false;
	private String errorMsg = "";
	
	@Resource(name = "analyzeLogService")
	private AnalyzeLogService analyzeLogService;
	
	
	@Action
	(
		value = "analyzeLogExport",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}		
	)
	public String analyzeLogExport()
	{
		String zipDir = request.getServletContext().getRealPath("") + File.separator + "logs" + File.separator + "database";
		try
		{
			this.startDate = "2015-09-01";
			this.endDate = "2015-09-09";
			HSSFWorkbook wb = analyzeLogService.getAnalyzeLogFile(zipDir, startDate, endDate);
			if ( wb == null )
			{
				result = false;
				errorMsg = "按交易时间导出报表错误";
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
			logger.error("按交易时间导出报表异常", e);
			result = false;
			errorMsg = "按交易时间导出报表异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
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
