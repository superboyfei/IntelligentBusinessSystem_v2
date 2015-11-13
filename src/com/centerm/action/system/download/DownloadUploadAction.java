package com.centerm.action.system.download;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.DownloadFile;
import com.centerm.service.manage.download.DownloadService;
import com.centerm.vo.DownloadFileVo;

@Controller("downloadUploadAction")
@Scope("prototype")
@Namespace("/system/download/upload")
@ParentPackage("json-default")
public class DownloadUploadAction
{
	private static final Logger logger = LogManager.getLogger(DownloadUploadAction.class);
	
	private File downloadFile;
	private String downloadFileName;
	private String downloadFileDescription;
	
	private boolean result = false;
	private DownloadFileVo downloadFileVo = new DownloadFileVo();
	private String errorMsg = "";
	
	@Resource(name = "downloadService")
	private DownloadService downloadService;

	@Action
	(
		value = "uploadDownloadFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, downloadFileVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String uploadDownloadFile()
	{
		try
		{
			DownloadFile downloadFile = downloadService.parseDownloadFile(this.downloadFile, downloadFileName);
			if ( downloadFile == null )
			{
				result = false;
				errorMsg = "上传文件解析出错";
				return "error";
			}
			
			downloadFileVo.setName(downloadFile.getName());
			logger.debug("name：" + downloadFileVo.getName());
			downloadFileVo.setSize(downloadFile.getSize());
			logger.debug("size：" + downloadFileVo.getSize());
			downloadFileVo.setUploadtime(DateFormatUtils.format(downloadFile.getUploadtime(), "yyyy-MM-dd"));
			logger.debug("uploadtime：" + downloadFileVo.getUploadtime());
			downloadFileVo.setIconpath(downloadFile.getIconpath());
			logger.debug("iconpath：" + downloadFileVo.getIconpath());
		}
		catch (Exception e)
		{
			logger.error("上传文件解析异常", e);
			result = false;
			errorMsg = "上传文件解析异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "saveDownloadFileInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String saveDownloadFileInfo()
	{
		try
		{
			boolean ret = downloadService.saveDownloadFile(downloadFileDescription);
			if ( ret == false )
			{
				result = false;
				errorMsg = "保存文件失败";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("保存文件异常", e);
			result = false;
			errorMsg = "保存文件异常";
			return "error";
		}
		
		result = true;
		return "success";
	}

	@Action
	(
		value = "cancelSaveDownloadFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String cancelSaveDownloadFile()
	{
		try
		{
			boolean ret = downloadService.deleteTmpDownloadFile();
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除临时下载文件错误";
				return "error";
			}
		}
		catch (Exception e)
		{
			logger.error("删除临时下载文件异常", e);
			result = false;
			errorMsg = "删除临时下载文件异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setDownloadFile(File downloadFile)
	{
		this.downloadFile = downloadFile;
	}
	
	public void setDownloadFileName(String downloadFileName)
	{
		this.downloadFileName = downloadFileName;
	}

	public void setDownloadFileDescription(String downloadFileDescription)
	{
		this.downloadFileDescription = downloadFileDescription;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public DownloadFileVo getDownloadFileVo()
	{
		return downloadFileVo;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}
}
