package com.centerm.action.homepage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.centerm.service.manage.system.LogService;
import com.centerm.util.FileUtil;

@Controller("homepageLogAction")
@Scope("prototype")
@Namespace("/homepage/log")
@ParentPackage("json-default")
public class HomepageLogAction implements ServletRequestAware, ServletResponseAware, SessionAware
{
	private static final Logger logger = LogManager.getLogger(HomepageLogAction.class);
	
	private int printLevel;
	private String startDate;
	private String endDate;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String,Object> session;
	
	private boolean result = false;
	private String errorMsg = "";
	
	@Resource(name = "logService")
	private LogService logService;
	
	@Action
	(
		value = "loadLogConfig",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, printLevel"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadLogConfig()
	{
		try
		{
			String configFile = request.getServletContext().getRealPath("") + File.separator + "WEB-INF" + File.separator + "log4j2.xml";
			printLevel = logService.getLogConfig(configFile);
			if ( printLevel == -1 )
			{
				result = false;
				errorMsg = "获取日志配置失败";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("获取日志配置异常", e);
			result = false;
			errorMsg = "获取日志配置异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "setLogConfig",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String setLogConfig()
	{
		try
		{
			String configFile = request.getServletContext().getRealPath("") + File.separator + "WEB-INF" + File.separator + "log4j2.xml";
			boolean ret = logService.setLogConfig(configFile, printLevel);
			if ( ret == false )
			{
				result = false;
				errorMsg = "设置日志配置失败";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("设置日志配置异常", e);
			result = false;
			errorMsg = "设置日志配置异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "exportLogPrepare",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}		
	)
	public String exportLogPrepare()
	{
		try
		{
			String zipDir = request.getServletContext().getRealPath("") + File.separator + "logs";
//			Because Of some browser download file will check the link twice,so delete zip files before this time
			FileUtil.deleteCertainSuffix(zipDir,"zip");
			String zipName = "log_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".zip";
			session.put("zipDir", zipDir);
			session.put("zipName", zipName);
			
			boolean ret = logService.getZipLogFile(zipDir, zipName, startDate, endDate);
			if ( ret == false )
			{
				result = false;
				errorMsg = "没有符合条件的日志文件";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("导出日志异常", e);
			result = false;
			errorMsg = "导出日志异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	@Action
	(
		value="exportLog",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}		
	)
	public String exportLog()
	{
		String zipDir = (String)session.get("zipDir");
		String zipName = (String)session.get("zipName");
		File zipFile = new File(zipDir + File.separator + zipName);
		
		FileInputStream fis = null;
		OutputStream os = null;
		try
		{
			os = response.getOutputStream();
			response.reset();
			response.setContentType("text/html");
			response.setHeader("Content-disposition", "attachment;filename=" + zipName);
			
			fis = new FileInputStream(zipFile);
			if ( zipFile.exists() )
			{
				int readLen = 0;
				byte[] zipByte = new byte[(int)zipFile.length()];
				do
				{
					readLen = fis.read(zipByte, readLen, zipByte.length);
				}
				while ( readLen < zipByte.length );
				os.write(zipByte);
				os.flush();
			}
			else
			{
				logger.debug("日志zip文件不存在");
				result = false;
				errorMsg = "导出日志错误";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("导出日志异常", e);
			result = false;
			errorMsg = "导出日志异常";
			return "error";
		}
		finally
		{
			try
			{
				fis.close();
//				FileUtils.deleteQuietly(zipFile);	//下载完成后删除zip包
				os.close();
			}
			catch(IOException e)
			{
				logger.error("关闭流异常", e);
			}
		}
		
		result = true;
		return "success";
	}
	
	public void setPrintLevel(int printLevel)
	{
		this.printLevel = printLevel;
	}
	
	public int getPrintLevel()
	{
		return printLevel;
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
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
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
